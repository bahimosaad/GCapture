package com.gdit.capture.entity;
// Generated 27/10/2010 12:03:54   by Hibernate Tools 3.2.0.beta7


import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class ComputersGroups.
 * @see com.gdit.capture.entity.ComputersGroups
 * @author Hibernate Tools
 */
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;
public class ComputersGroupsHome {

   

/** 
* Dependency injecting constructor. 
*/ 
public ComputersGroupsHome() { 
 
} 
    
 public static Session getSession() {
      Session session = (Session) ComputersGroupsHome.session.get();
      if (session == null) {
         session = sessionFactory.openSession();
         ComputersGroupsHome.session.set(session);
      }
      return session;
   }
    public void persist(ComputersGroups transientInstance) {
        log.log(Level.WARNING,"persisting ComputersGroups instance");
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
    
    public List<ComputersGroups> getAllComputersGroups(){
	    Query q = getSession().createQuery("from ComputersGroups");
	    List<ComputersGroups> list = (List<ComputersGroups>) q.list();
	    return list;
	}
    public void attachDirty(ComputersGroups instance) {
        log.log(Level.WARNING,"attaching dirty ComputersGroups instance");
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
    
    public void attachClean(ComputersGroups instance) {
        log.log(Level.WARNING,"attaching clean ComputersGroups instance");
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
    
    public void delete(ComputersGroups persistentInstance) {
        log.log(Level.WARNING,"deleting ComputersGroups instance");
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
      ComputersGroupsHome.session.set(null);
   }
   
   public static void close() {
      getSession().close();
      ComputersGroupsHome.session.set(null);
   }
   
   private static final Logger log = Logger.getAnonymousLogger();

   private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
 
   private static final SessionFactory sessionFactory = new Configuration()
         .configure().buildSessionFactory();

    
    public ComputersGroups merge(ComputersGroups detachedInstance) {
        log.log(Level.WARNING,"merging ComputersGroups instance");
        try {
            ComputersGroups result = (ComputersGroups) sessionFactory.getCurrentSession()
                    .merge(detachedInstance);
            log.log(Level.WARNING,"merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"merge failed", re);
            throw re;
        }
    }
    
    public ComputersGroups findById( com.gdit.capture.entity.ComputersGroupsId id) {
        log.log(Level.WARNING,"getting ComputersGroups instance with id: " + id);
        try {
	     begin();
            ComputersGroups instance = (ComputersGroups) getSession()
                    .get("com.gdit.capture.entity.ComputersGroups", id);
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
    
    public List findByExample(ComputersGroups instance) {
        log.log(Level.WARNING,"finding ComputersGroups instance by example");
        try {
            List results = sessionFactory.getCurrentSession()
                    .createCriteria("com.gdit.capture.entity.ComputersGroups")
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

