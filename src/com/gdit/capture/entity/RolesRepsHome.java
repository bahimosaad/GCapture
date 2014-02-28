package com.gdit.capture.entity;
// Generated 05/01/2011 04:28:01  by Hibernate Tools 3.2.0.beta7


import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class RolesReps.
 * @see com.gdit.capture.entity.RolesReps
 * @author Hibernate Tools
 */
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;
public class RolesRepsHome {

   

/** 
* Dependency injecting constructor. 
*/ 
public RolesRepsHome() { 
 
} 
    
 public static Session getSession() {
      Session session = (Session) RolesRepsHome.session.get();
      if (session == null) {
         session = sessionFactory.openSession();
         RolesRepsHome.session.set(session);
      }
      return session;
   }
    public void persist(RolesReps transientInstance) {
        log.log(Level.WARNING,"persisting RolesReps instance");
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
    
    public List<RolesReps> getAllRolesReps(){
	    Query q = getSession().createQuery("from RolesReps");
	    List<RolesReps> list = (List<RolesReps>) q.list();
	    return list;
	}
    public void attachDirty(RolesReps instance) {
        log.log(Level.WARNING,"attaching dirty RolesReps instance");
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
    
    public void attachClean(RolesReps instance) {
        log.log(Level.WARNING,"attaching clean RolesReps instance");
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
    
    public void delete(RolesReps persistentInstance) {
        log.log(Level.WARNING,"deleting RolesReps instance");
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
      RolesRepsHome.session.set(null);
   }
   
   public static void close() {
      getSession().close();
      RolesRepsHome.session.set(null);
   }
   
   private static final Logger log = Logger.getAnonymousLogger();

   private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
 
   private static final SessionFactory sessionFactory = new Configuration()
         .configure().buildSessionFactory();

    
    public RolesReps merge(RolesReps detachedInstance) {
        log.log(Level.WARNING,"merging RolesReps instance");
        try {
            RolesReps result = (RolesReps) sessionFactory.getCurrentSession()
                    .merge(detachedInstance);
            log.log(Level.WARNING,"merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"merge failed", re);
            throw re;
        }
    }
    
    public RolesReps findById( com.gdit.capture.entity.RolesRepsId id) {
        log.log(Level.WARNING,"getting RolesReps instance with id: " + id);
        try {
	     begin();
            RolesReps instance = (RolesReps) getSession()
                    .get("com.gdit.capture.entity.RolesReps", id);
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
    
    public List findByExample(RolesReps instance) {
        log.log(Level.WARNING,"finding RolesReps instance by example");
        try {
            List results = sessionFactory.getCurrentSession()
                    .createCriteria("com.gdit.capture.entity.RolesReps")
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

