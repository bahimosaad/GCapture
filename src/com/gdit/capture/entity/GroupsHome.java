package com.gdit.capture.entity;
// Generated 23/10/2010 01:01:59  by Hibernate Tools 3.2.0.beta7


import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class Groups.
 * @see com.gdit.capture.entity.Groups
 * @author Hibernate Tools
 */
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;
public class GroupsHome {

   

/** 
* Dependency injecting constructor. 
*/ 
public GroupsHome() { 
 
} 
    
 public static Session getSession() {
      Session session = (Session) GroupsHome.session.get();
      if (session == null) {
         session = sessionFactory.openSession();
         GroupsHome.session.set(session);
      }
      return session;
   }
    public void persist(Groups transientInstance) {
        log.log(Level.WARNING,"persisting Groups instance");
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
    
    public List<Groups> getAllGroups(){
	    Query q = getSession().createQuery("from Groups");
	    List<Groups> list = (List<Groups>) q.list();
	    return list;
	}
    public void attachDirty(Groups instance) {
        log.log(Level.WARNING,"attaching dirty Groups instance");
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

    public void update(Groups instance) {
        log.log(Level.WARNING,"attaching dirty Groups instance");
        try {
	     begin();
            getSession().update(instance);
            log.log(Level.WARNING,"attach successful");
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Groups instance) {
        log.log(Level.WARNING,"attaching clean Groups instance");
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
    
    public void delete(Groups persistentInstance) {
        log.log(Level.WARNING,"deleting Groups instance");
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
      GroupsHome.session.set(null);
   }
   
   public static void close() {
      getSession().close();
      GroupsHome.session.set(null);
   }
   
   private static final Logger log = Logger.getAnonymousLogger();

   private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
 
   private static final SessionFactory sessionFactory = new Configuration()
         .configure().buildSessionFactory();

    
    public Groups merge(Groups detachedInstance) {
        log.log(Level.WARNING,"merging Groups instance");
        try {
            begin();
            Groups result = (Groups) getSession().merge(detachedInstance);
            commit();
            log.log(Level.WARNING,"merge successful");
          
            return result;
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"merge failed", re);
            throw re;
        }
    }
    
    public Groups findById( java.math.BigDecimal id) {
        log.log(Level.WARNING,"getting Groups instance with id: " + id);
        try {
	     begin();
            Groups instance = (Groups) getSession()
                    .get("com.gdit.capture.entity.Groups", id);
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

    public Groups findByName( String name) {

        try {
	     begin();
            List<Groups> instances =  getSession().createQuery("select g from Groups g where g.groupName = :name")
                                .setParameter("name", name)
                                .list();
            if (instances==null) {
                log.log(Level.WARNING,"get successful, no instance found");
            }
            else {
                log.log(Level.WARNING,"get successful, instance found");
            }
            return instances.get(0);
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"get failed", re);
            throw re;
        }
    }
    
    public List findByExample(Groups instance) {
        log.log(Level.WARNING,"finding Groups instance by example");
        try {
            List results = sessionFactory.getCurrentSession()
                    .createCriteria("com.gdit.capture.entity.Groups")
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

