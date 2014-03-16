package com.gdit.capture.entity;
// Generated 27/10/2010 06:02:39   by Hibernate Tools 3.2.0.beta7


import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class Modules.
 * @see com.gdit.capture.entity.Modules
 * @author Hibernate Tools
 */
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;
public class ModulesHome {

   

/** 
* Dependency injecting constructor. 
*/ 
public ModulesHome() { 
 
} 
    
 public static Session getSession() {
      Session session = (Session) ModulesHome.session.get();
      if (session == null) {
         session = sessionFactory.openSession();
         ModulesHome.session.set(session);
      }
      return session;
   }
    public void persist(Modules transientInstance) {
        log.log(Level.WARNING,"persisting Modules instance");
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
    
    public List<Modules> getAllModules(){
	    Query q = getSession().createQuery(" from Modules");
	    List<Modules> list = (List<Modules>) q.list();
	    return list;
	}
    public void attachDirty(Modules instance) {
        log.log(Level.WARNING,"attaching dirty Modules instance");
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
    
    public void attachClean(Modules instance) {
        log.log(Level.WARNING,"attaching clean Modules instance");
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
    
    public void delete(Modules persistentInstance) {
        log.log(Level.WARNING,"deleting Modules instance");
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
      ModulesHome.session.set(null);
   }
   
   public static void close() {
      getSession().close();
      ModulesHome.session.set(null);
   }
   
   private static final Logger log = Logger.getAnonymousLogger();

   private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
 
   private static final SessionFactory sessionFactory = new Configuration()
         .configure().buildSessionFactory();

    
    public Modules merge(Modules detachedInstance) {
        log.log(Level.WARNING,"merging Modules instance");
        try {
            Modules result = (Modules) sessionFactory.getCurrentSession()
                    .merge(detachedInstance);
            log.log(Level.WARNING,"merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"merge failed", re);
            throw re;
        }
    }
    
    public Modules findById( java.math.BigDecimal id) {
        log.log(Level.WARNING,"getting Modules instance with id: " + id);
        try {
	     begin();
            Modules instance = (Modules) getSession()
                    .get("com.gdit.capture.entity.Modules", id);
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
    
    public List findByExample(Modules instance) {
        log.log(Level.WARNING,"finding Modules instance by example");
        try {
            List results = sessionFactory.getCurrentSession()
                    .createCriteria("com.gdit.capture.entity.Modules")
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

