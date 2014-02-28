package com.gdit.capture.entity;
// Generated 27/10/2010 06:02:39   by Hibernate Tools 3.2.0.beta7


import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class ModulesRoles.
 * @see com.gdit.capture.entity.ModulesRoles
 * @author Hibernate Tools
 */
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;
public class ModulesRolesHome {

   

/** 
* Dependency injecting constructor. 
*/ 
public ModulesRolesHome() { 
 
} 
    
 public static Session getSession() {
      Session session = (Session) ModulesRolesHome.session.get();
      if (session == null) {
         session = sessionFactory.openSession();
         ModulesRolesHome.session.set(session);
      }
      return session;
   }
    public void persist(ModulesRoles transientInstance) {
        log.log(Level.WARNING,"persisting ModulesRoles instance");
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
    
    public List<ModulesRoles> getAllModulesRoles(){
	    Query q = getSession().createQuery("from ModulesRoles");
	    List<ModulesRoles> list = (List<ModulesRoles>) q.list();
	    return list;
	}
    public void attachDirty(ModulesRoles instance) {
        log.log(Level.WARNING,"attaching dirty ModulesRoles instance");
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
    
    public void attachClean(ModulesRoles instance) {
        log.log(Level.WARNING,"attaching clean ModulesRoles instance");
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
    
    public void delete(ModulesRoles persistentInstance) {
        log.log(Level.WARNING,"deleting ModulesRoles instance");
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
      ModulesRolesHome.session.set(null);
   }
   
   public static void close() {
      getSession().close();
      ModulesRolesHome.session.set(null);
   }
   
   private static final Logger log = Logger.getAnonymousLogger();

   private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
 
   private static final SessionFactory sessionFactory = new Configuration()
         .configure().buildSessionFactory();

    
    public ModulesRoles merge(ModulesRoles detachedInstance) {
        log.log(Level.WARNING,"merging ModulesRoles instance");
        try {
            ModulesRoles result = (ModulesRoles) sessionFactory.getCurrentSession()
                    .merge(detachedInstance);
            log.log(Level.WARNING,"merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"merge failed", re);
            throw re;
        }
    }
    
    public ModulesRoles findById( com.gdit.capture.entity.ModulesRolesId id) {
        log.log(Level.WARNING,"getting ModulesRoles instance with id: " + id);
        try {
	     begin();
            ModulesRoles instance = (ModulesRoles) getSession()
                    .get("com.gdit.capture.entity.ModulesRoles", id);
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
    
    public List findByExample(ModulesRoles instance) {
        log.log(Level.WARNING,"finding ModulesRoles instance by example");
        try {
            List results = sessionFactory.getCurrentSession()
                    .createCriteria("com.gdit.capture.entity.ModulesRoles")
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

