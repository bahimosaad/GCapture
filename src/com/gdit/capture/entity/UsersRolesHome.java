package com.gdit.capture.entity;
 
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class UsersRoles.
 * @see com.gdit.capture.entity.UsersRoles
 * @author Hibernate Tools
 */
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;
public class UsersRolesHome {

   

/** 
* Dependency injecting constructor. 
*/ 
public UsersRolesHome() { 
 
} 
    
 public static Session getSession() {
      Session session = (Session) UsersRolesHome.session.get();
      if (session == null) {
         session = sessionFactory.openSession();
         UsersRolesHome.session.set(session);
      }
      return session;
   }
    public void persist(UsersRoles transientInstance) {
        log.log(Level.WARNING,"persisting UsersRoles instance");
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
    
    public List<UsersRoles> getAllUsersRoles(){
	    Query q = getSession().createQuery("from UsersRoles");
	    List<UsersRoles> list = (List<UsersRoles>) q.list();
	    return list;
	}
    public void attachDirty(UsersRoles instance) {
        log.log(Level.WARNING,"attaching dirty UsersRoles instance");
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
    
    public void attachClean(UsersRoles instance) {
        log.log(Level.WARNING,"attaching clean UsersRoles instance");
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
    
    public void delete(UsersRoles persistentInstance) {
        log.log(Level.WARNING,"deleting UsersRoles instance");
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
      UsersRolesHome.session.set(null);
   }
   
   public static void close() {
      getSession().close();
      UsersRolesHome.session.set(null);
   }
   
   private static final Logger log = Logger.getAnonymousLogger();

   private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
 
   private static final SessionFactory sessionFactory = new Configuration()
         .configure().buildSessionFactory();

    
    public UsersRoles merge(UsersRoles detachedInstance) {
        log.log(Level.WARNING,"merging UsersRoles instance");
        try {
            UsersRoles result = (UsersRoles) sessionFactory.getCurrentSession()
                    .merge(detachedInstance);
            log.log(Level.WARNING,"merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"merge failed", re);
            throw re;
        }
    }
    
    public UsersRoles findById( com.gdit.capture.entity.UsersRolesId id) {
        log.log(Level.WARNING,"getting UsersRoles instance with id: " + id);
        try {
	     begin();
            UsersRoles instance = (UsersRoles) getSession()
                    .get("com.gdit.capture.entity.UsersRoles", id);
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
    
    public List findByExample(UsersRoles instance) {
        log.log(Level.WARNING,"finding UsersRoles instance by example");
        try {
            List results = sessionFactory.getCurrentSession()
                    .createCriteria("com.gdit.capture.entity.UsersRoles")
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

