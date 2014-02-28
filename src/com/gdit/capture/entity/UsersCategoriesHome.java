package com.gdit.capture.entity;
// Generated 05/01/2011 03:33:03  by Hibernate Tools 3.2.0.beta7


import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class UsersCategories.
 * @see com.gdit.capture.entity.UsersCategories
 * @author Hibernate Tools
 */
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;
public class UsersCategoriesHome {

   

/** 
* Dependency injecting constructor. 
*/ 
public UsersCategoriesHome() { 
 
} 
    
 public static Session getSession() {
      Session session = (Session) UsersCategoriesHome.session.get();
      if (session == null) {
         session = sessionFactory.openSession();
         UsersCategoriesHome.session.set(session);
      }
      return session;
   }
    public void persist(UsersCategories transientInstance) {
        log.log(Level.WARNING,"persisting UsersCategories instance");
        try {
	     begin();
            getSession().persist(transientInstance);
            log.log(Level.WARNING,"persist successful");
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"persist failed", re);
            throw re;
        }
    }
    
    public List<UsersCategories> getAllUsersCategories(){
	    Query q = getSession().createQuery("from UsersCategories");
	    List<UsersCategories> list = (List<UsersCategories>) q.list();
	    return list;
	}
    public void attachDirty(UsersCategories instance) {
        log.log(Level.WARNING,"attaching dirty UsersCategories instance");
        try {
	     begin();
            getSession().saveOrUpdate(instance);
            log.log(Level.WARNING,"attach successful");
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(UsersCategories instance) {
        log.log(Level.WARNING,"attaching clean UsersCategories instance");
        try {
             begin();
            getSession().lock(instance, LockMode.NONE);
            log.log(Level.WARNING,"attach successful");
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"attach failed", re);
            throw re;
        }
    }
    
    public void delete(UsersCategories persistentInstance) {
        log.log(Level.WARNING,"deleting UsersCategories instance");
        try {
	     begin();
            getSession().delete(persistentInstance);
            log.log(Level.WARNING,"delete successful");
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"delete failed", re);
            throw re;
        }
    }
	
public void begin() {
      getSession().beginTransaction();
   }
   
   public void commit() {
      getSession().getTransaction().commit();
   }

public void rollback() {
      try {
      getSession().getTransaction().rollback();
      } catch( HibernateException e ) {
         log.log(Level.WARNING,"Cannot rollback",e);
      }
      
      try {
         getSession().close();
      } catch( HibernateException e ) {
         log.log(Level.WARNING,"Cannot close",e);         
      }
      UsersCategoriesHome.session.set(null);
   }
   
   public static void close() {
      getSession().close();
      UsersCategoriesHome.session.set(null);
   }
   
   private static final Logger log = Logger.getAnonymousLogger();

   private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
 
   private static final SessionFactory sessionFactory = new Configuration()
         .configure().buildSessionFactory();

    
    public UsersCategories merge(UsersCategories detachedInstance) {
        log.log(Level.WARNING,"merging UsersCategories instance");
        try {
            UsersCategories result = (UsersCategories) sessionFactory.getCurrentSession()
                    .merge(detachedInstance);
            log.log(Level.WARNING,"merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"merge failed", re);
            throw re;
        }
    }
    
    public UsersCategories findById( com.gdit.capture.entity.UsersCategoriesId id) {
        log.log(Level.WARNING,"getting UsersCategories instance with id: " + id);
        try {
	     begin();
            UsersCategories instance = (UsersCategories) getSession()
                    .get("com.gdit.capture.entity.UsersCategories", id);
            if (instance==null) {
                log.log(Level.WARNING,"get successful, no instance found");
            }
            else {
                log.log(Level.WARNING,"get successful, instance found");
            }
            return instance;
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"get failed", re);
            throw re;
        }
    }
    
    public List findByExample(UsersCategories instance) {
        log.log(Level.WARNING,"finding UsersCategories instance by example");
        try {
            List results = sessionFactory.getCurrentSession()
                    .createCriteria("com.gdit.capture.entity.UsersCategories")
                    .add(Example.create(instance))
            .list();
            log.log(Level.WARNING,"find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"find by example failed", re);
            throw re;
        }
    } 
}

