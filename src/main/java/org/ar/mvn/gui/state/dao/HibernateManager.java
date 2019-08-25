package org.ar.mvn.gui.state.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.ar.mvn.gui.entity.Project;
import org.ar.mvn.gui.entity.Setting;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateManager implements IDataBaseStateManager {

  private SessionFactory sessionFactory = null;
  private Session session = null;

  private void configureSessionFactory() throws HibernateException {
    try {
      Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
      sessionFactory = configuration.buildSessionFactory();
    } catch (Exception e) {
      e.printStackTrace();
    }
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
  public Setting loadSetting() {
    Setting setting = new Setting();
    try {
      openTransaction();
      setting = (Setting) session.createQuery("FROM Setting").uniqueResult();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      closeSession();
    }
    return setting;
  }

  @Override
  public List<Project> loadProjects(){
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
  public int saveSetting(Setting setting) {
    int result = 0;
    try {
      openTransaction();
      session.merge(setting);
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
  public int saveProject(Project project) {
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
  public int deleteProject(int id) {
    int result = 0;
    try {
      openTransaction();
      Project project = session.find(Project.class, id);
      session.remove(project);
      commit();
      result = 1;
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
    }
    return result;
  }
}
