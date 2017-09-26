package org.ar.mvn.gui.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.ar.mvn.gui.entity.Project;
import org.ar.mvn.gui.listeners.IGenerateProjectExecutorListener;
import org.ar.mvn.gui.listeners.ITaskExecutorListener;
import org.ar.mvn.gui.state.ApplicationStateManager;

public final class CommandExecutorUtil {

  private static Runtime r = Runtime.getRuntime();

  public static void executeCommand(final Project p, final String command,
      final ITaskExecutorListener listener) {
    // initialize thread - command executor
    Thread executor = new Thread(new Runnable() {
      @Override
      public void run() {
        UpdaterState updaterState = startConsoleUpdater(listener);
        try {
          String maven = generateMavenLocation();
          Process process = r.exec(maven + " " + command + " -f " + p.getPath());
          BufferedReader reader =
              new BufferedReader(new InputStreamReader(process.getInputStream()));
          String buffer = reader.readLine();
          while (buffer != null) {
            p.getConsoleLog().append(buffer);
            p.getConsoleLog().append("\n");
            buffer = reader.readLine();
          }
          process.waitFor();
          p.setStatus(Project.STATUS_AVAILABLE);
        } catch (Exception e) {
          // TODO add logger
          p.getConsoleLog().append(e.getMessage());
          p.getConsoleLog().append("\n");
          p.setStatus(Project.STATUS_ERROR);
        }
        listener.executed();
        if (updaterState.isWork()) {
          updaterState.setWork(false);
        }
      }
    });
    //
    executor.start();
  }

  public static void executeGenerateProjectCommand(final String destination, final String group,
      final String artifactory, final String archetype,
      final IGenerateProjectExecutorListener listener) {
    // initialize thread - command executor
    Thread executor = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          String maven = generateMavenLocation();
          Process process =
              r.exec(maven + " archetype:generate -DgroupId=" + group + " -DartifactId="
                  + artifactory + " -DarchetypeArtifactId=" + archetype
                  + " -DinteractiveMode=false", null, new File(destination));
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
          // TODO add logger
          listener.updateConsole(e.getMessage());
          listener.updateConsole("\n");
        }
        listener.executed();
      }
    });
    //
    executor.start();
  }


  private static UpdaterState startConsoleUpdater(final ITaskExecutorListener listener) {
    final UpdaterState state = new UpdaterState();
    state.setWork(true);

    Thread updater = new Thread(new Runnable() {
      @Override
      public void run() {
        while (state.isWork()) {
          listener.updateConsole();
          try {
            synchronized (this) {

              // update friquency
              this.wait(500);
            }
          } catch (Exception e) {
            // TODO logger
            e.printStackTrace();
          }
        }
      }
    });
    updater.start();
    return state;
  }

  private static String generateMavenLocation() {
    String mavenExecutable = getMavenExecutable();
    return ApplicationStateManager.INSTANCE().getSettings().getMavenHome() + "/bin/" + mavenExecutable;
  }

  static String getMavenExecutable() {
    String osName = System.getProperty("os.name").toLowerCase();
    if (osName.contains("win")) {
      return "mvn.cmd";
    }
    return "mvn";
  }
}
