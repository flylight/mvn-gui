package org.ar.mvn.gui.state.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.ar.mvn.gui.entity.Project;
import org.ar.mvn.gui.entity.Settings;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateManager implements IDataBaseStateManager {
  private EntityManager entityManager;
  private SessionFactory sessionFactory = null;
  private Session session = null;

  private SessionFactory configureSessionFactory() throws HibernateException {
    try{
      Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
      sessionFactory = configuration.buildSessionFactory();
    }catch (Exception e){
      e.printStackTrace();
    }
    return sessionFactory;
  }

  public void openTransaction() {
    configureSessionFactory();
    session = sessionFactory.openSession();
    session.beginTransaction();
  }

  public void commit() {
    session.getTransaction().commit();
  }

  public void rollback() {
    session.getTransaction().rollback();
  }

  public void closeSession() {
    session.close();
  }

  @Override
  public Settings loadSettings() throws SQLException {
    Settings settings = new Settings();
    try {
      openTransaction();
      settings = (Settings) session.createQuery("FROM Settings").uniqueResult();
    } catch (Exception e) {
      e.printStackTrace();
    }finally{
      closeSession();
    }
    return settings;
  }

  @Override
  public List<Project> loadProjects() throws SQLException {
    List<Project> projects = new ArrayList();
    try {
      openTransaction();
      projects = session.createQuery("FROM Project").list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      closeSession();
    }
    return projects;
  }

  @Override
  public int saveSettings(Settings settings) throws SQLException {
    int result = 0;
    try {
      openTransaction();
      session.merge(settings);
      commit();
      result = 1;
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
    } finally {
      closeSession();
    }
    return result;
  }

  @Override
  public int saveNewProject(Project project) throws SQLException {
    int result = 0;
    try {
      openTransaction();
      session.save(project);
      commit();
      result = 1;
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
    } finally {
      closeSession();
    }
    return result;
  }

  @Override
  public int deleteProject(int id) throws SQLException {
    int result = 0;
    try{
      openTransaction();
      Project project = session.find(Project.class, id);
      session.remove(project);
      commit();
      result = 1;
    }catch (Exception e){
      e.printStackTrace();
      rollback();
    }
    return result;
  }
}
