package com.gdit.capture.entity;
// Generated 23/10/2010 01:01:59 � by Hibernate Tools 3.2.0.beta7


import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class Users.
 * @see com.gdit.capture.entity.Users
 * @author Hibernate Tools
 */
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;
public class UsersHome {



/**
* Dependency injecting constructor.
*/
public UsersHome() {

}

 public static Session getSession() {
      Session session = (Session) UsersHome.session.get();

      if (session == null) {
         session = sessionFactory.openSession();
         UsersHome.session.set(session);
      }
      return session;
   }
    public void persist(Users transientInstance) {
        log.log(Level.WARNING,"persisting Users instance");
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

    public List<Users> getAllUsers(){
	    Query q = getSession().createQuery("from Users");
	    List<Users> list = (List<Users>) q.list();
	    return list;
	}

    
    public void attachDirty(Users instance) {
        log.log(Level.WARNING,"attaching dirty Users instance");
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

    public Users authnticateUser(String userName,String password){
        try{
            begin();
            Query q = getSession().createQuery("select u from Users u where u.userName = :userName AND u.password = :password")
                                   .setParameter("userName", userName)
                                   .setParameter("password", password);
            List<Users> users = q.list();
            if(users!=null && users.size() > 0){
                return users.get(0);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public void attachClean(Users instance) {
        log.log(Level.WARNING,"attaching clean Users instance");
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

    public void delete(Users persistentInstance) {
        log.log(Level.WARNING,"deleting Users instance");
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
      UsersHome.session.set(null);
   }

   public static void close() {
      getSession().close();
      UsersHome.session.set(null);
   }

   private static final Logger log = Logger.getAnonymousLogger();

   private static final ThreadLocal<Session> session = new ThreadLocal<Session>();

   private static final SessionFactory sessionFactory = new Configuration()
         .configure().buildSessionFactory();


    public void merge(Users detachedInstance) {
        log.log(Level.WARNING,"merging Users instance");
        try {
            begin();
              getSession().merge(detachedInstance);
            commit();
            log.log(Level.WARNING,"merge successful");
            
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"merge failed", re);
            throw re;
        }
    }

    public Users findById( int id) {
        log.log(Level.WARNING,"getting Users instance with id: " + id);
        try {
	     begin();
            Users instance = (Users) getSession()
                    .get("com.gdit.capture.entity.Users", id);
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

    public List findByExample(Users instance) {
        log.log(Level.WARNING,"finding Users instance by example");
        try {
            List results = sessionFactory.getCurrentSession()
                    .createCriteria("com.gdit.capture.entity.Users")
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

