package org.ar.mvn.gui.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import org.ar.mvn.gui.entity.Project;
import org.ar.mvn.gui.listeners.IGenerateProjectExecutorListener;
import org.ar.mvn.gui.listeners.ITaskExecutorListener;
import org.ar.mvn.gui.state.ApplicationStateManager;

public final class CommandExecutorUtil {

  private static Runtime runtime = Runtime.getRuntime();

  public static void executeCommand(
      final Project project, final String command, final ITaskExecutorListener listener) {
    Thread executor =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
                UpdaterState updaterState = startConsoleUpdater(listener);
                try {
                  String maven = generateMavenLocation();
                  Process process = null;
                  if (OSUtil.isWindows()) {
                    process =
                        runtime.exec(
                            "cmd.exe /c "
                                + maven
                                + " "
                                + command
                                + " -f "
                                + project.getPath()
                                + "/pom.xml");
                  } else {
                    process = runtime.exec(maven + " " + command + " -f " + project.getPath());
                  }

                  BufferedReader reader =
                      new BufferedReader(new InputStreamReader(process.getInputStream()));
                  String buffer = reader.readLine();
                  while (buffer != null) {
                    project.getConsoleLog().append(buffer);
                    project.getConsoleLog().append("\n");
                    buffer = reader.readLine();
                  }
                  process.waitFor();
                  project.setStatus(Project.STATUS_AVAILABLE);
                } catch (Exception e) {
                  project.getConsoleLog().append(e.getMessage());
                  project.getConsoleLog().append("\n");
                  project.setStatus(Project.STATUS_ERROR);
                }
                listener.executed();
                if (updaterState.isWork()) {
                  updaterState.setWork(false);
                }
              }
            });
    executor.start();
  }

  public static void executeGenerateProjectCommand(
      final String destination,
      final String group,
      final String artifactory,
      final String archetype,
      final IGenerateProjectExecutorListener listener) {
    Thread executor =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
                try {
                  String maven = generateMavenLocation();
                  Process process = null;

                  if (OSUtil.isWindows()) {
                    process =
                        runtime.exec(
                            "cmd.exe /c "
                                + maven
                                + " archetype:generate -DgroupId="
                                + group
                                + " -DartifactId="
                                + artifactory
                                + " -DarchetypeArtifactId="
                                + archetype
                                + " -DinteractiveMode=false",
                            null,
                            new File(destination));
                  } else {
                    process =
                        runtime.exec(
                            maven
                                + " archetype:generate -DgroupId="
                                + group
                                + " -DartifactId="
                                + artifactory
                                + " -DarchetypeArtifactId="
                                + archetype
                                + " -DinteractiveMode=false",
                            null,
                            new File(destination));
                  }

                  BufferedReader reader =
                      new BufferedReader(new InputStreamReader(process.getInputStream()));
                  String buffer = reader.readLine();
                  while (buffer != null) {
                    listener.updateConsole(buffer);
                    listener.updateConsole("\n");
                    buffer = reader.readLine();
                  }
                  process.waitFor();
                } catch (Exception e) {
                  listener.updateConsole(e.getMessage());
                  listener.updateConsole("\n");
                }
                listener.executed();
              }
            });
    executor.start();
  }

  private static UpdaterState startConsoleUpdater(final ITaskExecutorListener listener) {
    final UpdaterState state = new UpdaterState();
    state.setWork(true);

    Thread updater =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
                while (state.isWork()) {
                  listener.updateConsole();
                  try {
                    synchronized (this) {
                      this.wait(500);
                    }
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                }
              }
            });
    updater.start();
    return state;
  }

  private static String generateMavenLocation() {
    return ApplicationStateManager.INSTANCE().getSetting().getMavenHome() + "/bin/mvn";
  }
}
