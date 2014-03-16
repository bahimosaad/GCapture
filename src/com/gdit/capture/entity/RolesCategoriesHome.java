package com.gdit.capture.entity;
// Generated 05/01/2011 04:28:01   by Hibernate Tools 3.2.0.beta7


import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class RolesCategories.
 * @see com.gdit.capture.entity.RolesCategories
 * @author Hibernate Tools
 */
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;
public class RolesCategoriesHome {

   

/** 
* Dependency injecting constructor. 
*/ 
public RolesCategoriesHome() { 
 
} 
    
 public static Session getSession() {
      Session session = (Session) RolesCategoriesHome.session.get();
      if (session == null) {
         session = sessionFactory.openSession();
         RolesCategoriesHome.session.set(session);
      }
      return session;
   }
    public void persist(RolesCategories transientInstance) {
        log.log(Level.WARNING,"persisting RolesCategories instance");
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
    
    public List<RolesCategories> getAllRolesCategories(){
	    Query q = getSession().createQuery("from RolesCategories");
	    List<RolesCategories> list = (List<RolesCategories>) q.list();
	    return list;
	}
    public void attachDirty(RolesCategories instance) {
        log.log(Level.WARNING,"attaching dirty RolesCategories instance");
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
    
    public void attachClean(RolesCategories instance) {
        log.log(Level.WARNING,"attaching clean RolesCategories instance");
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
    
    public void delete(RolesCategories persistentInstance) {
        log.log(Level.WARNING,"deleting RolesCategories instance");
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
      RolesCategoriesHome.session.set(null);
   }
   
   public static void close() {
      getSession().close();
      RolesCategoriesHome.session.set(null);
   }
   
   private static final Logger log = Logger.getAnonymousLogger();

   private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
 
   private static final SessionFactory sessionFactory = new Configuration()
         .configure().buildSessionFactory();

    
    public RolesCategories merge(RolesCategories detachedInstance) {
        log.log(Level.WARNING,"merging RolesCategories instance");
        try {
            RolesCategories result = (RolesCategories) sessionFactory.getCurrentSession()
                    .merge(detachedInstance);
            log.log(Level.WARNING,"merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"merge failed", re);
            throw re;
        }
    }
    
    public RolesCategories findById( com.gdit.capture.entity.RolesCategoriesId id) {
        log.log(Level.WARNING,"getting RolesCategories instance with id: " + id);
        try {
	     begin();
            RolesCategories instance = (RolesCategories) getSession()
                    .get("com.gdit.capture.entity.RolesCategories", id);
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
    
    public List findByExample(RolesCategories instance) {
        log.log(Level.WARNING,"finding RolesCategories instance by example");
        try {
            List results = sessionFactory.getCurrentSession()
                    .createCriteria("com.gdit.capture.entity.RolesCategories")
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

