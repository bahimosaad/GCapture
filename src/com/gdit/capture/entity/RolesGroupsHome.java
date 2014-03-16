package com.gdit.capture.entity;
// Generated 23/10/2010 01:01:59  by Hibernate Tools 3.2.0.beta7


import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class RolesGroups.
 * @see com.gdit.capture.entity.RolesGroups
 * @author Hibernate Tools
 */
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;
public class RolesGroupsHome {

   

/** 
* Dependency injecting constructor. 
*/ 
public RolesGroupsHome() { 
 
} 
    
 public static Session getSession() {
      Session session = (Session) RolesGroupsHome.session.get();
      if (session == null) {
         session = sessionFactory.openSession();
         RolesGroupsHome.session.set(session);
      }
      return session;
   }
    public void persist(RolesGroups transientInstance) {
        log.log(Level.WARNING,"persisting RolesGroups instance");
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
    
    public List<RolesGroups> getAllRolesGroups(){
	    Query q = getSession().createQuery("from RolesGroups");
	    List<RolesGroups> list = (List<RolesGroups>) q.list();
	    return list;
	}
    public void attachDirty(RolesGroups instance) {
        log.log(Level.WARNING,"attaching dirty RolesGroups instance");
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
    
    public void attachClean(RolesGroups instance) {
        log.log(Level.WARNING,"attaching clean RolesGroups instance");
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
    
    public void delete(RolesGroups persistentInstance) {
        log.log(Level.WARNING,"deleting RolesGroups instance");
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
      RolesGroupsHome.session.set(null);
   }
   
   public static void close() {
      getSession().close();
      RolesGroupsHome.session.set(null);
   }
   
   private static final Logger log = Logger.getAnonymousLogger();

   private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
 
   private static final SessionFactory sessionFactory = new Configuration()
         .configure().buildSessionFactory();

    
    public RolesGroups merge(RolesGroups detachedInstance) {
        log.log(Level.WARNING,"merging RolesGroups instance");
        try {
            RolesGroups result = (RolesGroups) sessionFactory.getCurrentSession()
                    .merge(detachedInstance);
            log.log(Level.WARNING,"merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"merge failed", re);
            throw re;
        }
    }
    
    public RolesGroups findById( com.gdit.capture.entity.RolesGroupsId id) {
        log.log(Level.WARNING,"getting RolesGroups instance with id: " + id);
        try {
	     begin();
            RolesGroups instance = (RolesGroups) getSession()
                    .get("com.gdit.capture.entity.RolesGroups", id);
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
    
    public List findByExample(RolesGroups instance) {
        log.log(Level.WARNING,"finding RolesGroups instance by example");
        try {
            List results = sessionFactory.getCurrentSession()
                    .createCriteria("com.gdit.capture.entity.RolesGroups")
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

