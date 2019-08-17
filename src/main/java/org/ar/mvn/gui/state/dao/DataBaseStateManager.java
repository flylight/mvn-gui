package org.ar.mvn.gui.state.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.ar.mvn.gui.entity.Project;
import org.ar.mvn.gui.entity.Settings;

public class DataBaseStateManager implements IDataBaseStateManager {
  // defined queries
  private static final String CREATE_TABLE_IF_NOT_EXISTS = "create table if not exists ";
  // DB parameters
  private static final String PROJECT_TABLE = "project";
  private static final String SETTINGS_TABLE = "settings";

  private static final String SETTINGS_ID = "1";

  private Connection dbConnection;
  private Statement dbStatement;

  public DataBaseStateManager() {
    initDatabaseConnection();
  }

  private void checkDatabaseState() throws SQLException {
    // project table
    String sql =
        CREATE_TABLE_IF_NOT_EXISTS + PROJECT_TABLE
            + " (id INTEGER PRIMARY KEY, name TEXT, path TEXT);";
    dbStatement.execute(sql);
    // settings table
    sql =
        CREATE_TABLE_IF_NOT_EXISTS + SETTINGS_TABLE + " (id INTEGER PRIMARY KEY, mavenPath TEXT);";
    dbStatement.execute(sql);
  }

  private void initDatabaseConnection() {
    try {
      // load DB object
      Class.forName("org.sqlite.JDBC");
      dbConnection = DriverManager.getConnection("jdbc:sqlite:mvn-ui-config.db");
      dbConnection.setAutoCommit(true);
      // create DB statement
      dbStatement = dbConnection.createStatement();
      // chekc DB state
      checkDatabaseState();
    } catch (Exception e) {
      // TODO add logging
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    // TODO add logger
    System.out.println("Opened database successfully");
  }

  @Override
  public int saveNewProject(Project p) throws SQLException {
    int res =
        dbStatement.executeUpdate("insert into " + PROJECT_TABLE + " (name,path) values ('"
            + p.getName() + "','" + p.getPath() + "')");
    return res;
  }

  @Override
  public int deleteProject(int id) throws SQLException {
    int res = dbStatement.executeUpdate("delete from " + PROJECT_TABLE + " where id=" + id);
    return res;
  }

  @Override
  public List<Project> loadProjects() throws SQLException {
    List<Project> result = new ArrayList<Project>();

    ResultSet queryResult = dbStatement.executeQuery("select id, name, path from " + PROJECT_TABLE);

    while (queryResult.next()) {
      Project p = new Project();
      //
      p.setId(queryResult.getInt("id"));
      p.setName(queryResult.getString("name"));
      p.setPath(queryResult.getString("path"));
      //
      result.add(p);
    }

    return result;
  }

  @Override
  public int saveSettings(Settings s) throws SQLException {
    int result =
        dbStatement.executeUpdate("update " + SETTINGS_TABLE + " set mavenPath = '"
            + s.getMavenHome() +  "' , locale = '" + s.getLocale() +"' where id = " + SETTINGS_ID);
    if (result == 0) {
      result =
          dbStatement.executeUpdate("insert into " + SETTINGS_TABLE + " (id,mavenPath, locale) values ("
              + SETTINGS_ID + ",'" + s.getMavenHome() + "', ' " + s.getLocale()+"')");
    }
    return result;
  }

  @Override
  public Settings loadSettings() throws SQLException {
    Settings s = new Settings();

    ResultSet queryResult =
        dbStatement.executeQuery("select mavenPath, locale from " + SETTINGS_TABLE + " where id = "
            + SETTINGS_ID);

    while (queryResult.next()) {
      s.setMavenHome(queryResult.getString("mavenPath"));
      s.setLocale(queryResult.getString("locale"));
    }

    return s;
  }
}
