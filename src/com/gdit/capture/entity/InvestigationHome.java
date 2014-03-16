package com.gdit.capture.entity;
// Generated 14/12/2010 07:03:58   by Hibernate Tools 3.2.0.beta7


import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class Investigation.
 * @see com.gdit.capture.entity.Investigation
 * @author Hibernate Tools
 */
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;
public class InvestigationHome {

   

/** 
* Dependency injecting constructor. 
*/ 
public InvestigationHome() { 
 
} 
    
 public static Session getSession() {
      Session session = (Session) InvestigationHome.session.get();
      if (session == null) {
         session = sessionFactory.openSession();
         InvestigationHome.session.set(session);
      }
      return session;
   }
    public void persist(Investigation transientInstance) {
        log.log(Level.WARNING,"persisting Investigation instance");
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
    
    public List<Investigation> getAllInvestigation(){
	    Query q = getSession().createQuery("from Investigation");
	    List<Investigation> list = (List<Investigation>) q.list();
	    return list;
	}
    public void attachDirty(Investigation instance) {
        log.log(Level.WARNING,"attaching dirty Investigation instance");
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
    
    public void attachClean(Investigation instance) {
        log.log(Level.WARNING,"attaching clean Investigation instance");
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
    
    public void delete(Investigation persistentInstance) {
        log.log(Level.WARNING,"deleting Investigation instance");
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
      InvestigationHome.session.set(null);
   }
   
   public static void close() {
      getSession().close();
      InvestigationHome.session.set(null);
   }
   
   private static final Logger log = Logger.getAnonymousLogger();

   private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
 
   private static final SessionFactory sessionFactory = new Configuration()
         .configure().buildSessionFactory();

    
    public Investigation merge(Investigation detachedInstance) {
        log.log(Level.WARNING,"merging Investigation instance");
        try {
            Investigation result = (Investigation) sessionFactory.getCurrentSession()
                    .merge(detachedInstance);
            log.log(Level.WARNING,"merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"merge failed", re);
            throw re;
        }
    }
    
    public Investigation findById( short id) {
        log.log(Level.WARNING,"getting Investigation instance with id: " + id);
        try {
	     begin();
            Investigation instance = (Investigation) getSession()
                    .get("com.gdit.capture.entity.Investigation", id);
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
    
    public List findByExample(Investigation instance) {
        log.log(Level.WARNING,"finding Investigation instance by example");
        try {
            List results = sessionFactory.getCurrentSession()
                    .createCriteria("com.gdit.capture.entity.Investigation")
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

