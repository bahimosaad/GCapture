package com.gdit.capture.entity;
// Generated 23/10/2010 01:01:59 � by Hibernate Tools 3.2.0.beta7


import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class Roles.
 * @see com.gdit.capture.entity.Roles
 * @author Hibernate Tools
 */
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;
public class RolesHome{



/**
* Dependency injecting constructor.
*/
public RolesHome() {

}

 public static Session getSession() {
      Session session = (Session) RolesHome.session.get();
      if (session == null) {
         session = sessionFactory.openSession();
         RolesHome.session.set(session);
      }
      return session;
   }
    public void persist(Roles transientInstance) {
        log.log(Level.WARNING,"persisting Roles instance");
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

    public List<Roles> getAllRoles(){
	    Query q = getSession().createQuery("from Roles");
	    List<Roles> list = (List<Roles>) q.list();
	    return list;
	}

    public List<Roles> getUsersRoles(Users user){
        try{
            Query q = getSession().createQuery("Select r from Roles r ,Users u, UsersGroups ug , RolesGroups rg where ug.groups = rg.groups " +
                    " AND rg.roles = r AND ug.users = u AND u = :user ").setParameter("user", user);
            List<Roles> roles = q.list();
            return roles;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public List<Modules> getUsersModules(Users user){
        try{
            Query q = getSession().createQuery("Select m from Modules m, ModulesRoles mr, Roles r ,Users u, UsersGroups ug , RolesGroups rg where ug.groups = rg.groups " +
                    " AND rg.roles = r AND ug.users = u AND mr.roles = r AND mr.modules = m  AND u = :user ").setParameter("user", user);
            List<Modules> modules = q.list();
            return modules;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public List<Roles> getComputersRoles(Computers computer){
        try{
            Query q = getSession().createQuery("Select r from Roles r ,Computers u, ComputersGroups ug , RolesGroups rg where ug.groups = rg.groups " +
                    " AND rg.roles = r AND ug.computers = u AND u = :user ").setParameter("user", computer);
            List<Roles> roles = q.list();
            return roles;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public void attachDirty(Roles instance) {
        log.log(Level.WARNING,"attaching dirty Roles instance");
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

    public void attachClean(Roles instance) {
        log.log(Level.WARNING,"attaching clean Roles instance");
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

    public void delete(Roles persistentInstance) {
        log.log(Level.WARNING,"deleting Roles instance");
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
      RolesHome.session.set(null);
   }

   public static void close() {
      getSession().close();
      RolesHome.session.set(null);
   }

   private static final Logger log = Logger.getAnonymousLogger();

   private static final ThreadLocal<Session> session = new ThreadLocal<Session>();

   private static final SessionFactory sessionFactory = new Configuration()
         .configure().buildSessionFactory();


    public Roles merge(Roles detachedInstance) {
        log.log(Level.WARNING,"merging Roles instance");
        try {
            begin();
            Roles result = (Roles) getSession().merge(detachedInstance);
//            commit();
            log.log(Level.WARNING,"merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"merge failed", re);
            throw re;
        }
    }

    public Roles findById( Integer id) {
        log.log(Level.WARNING,"getting Roles instance with id: " + id);
        try {
	     begin();
            Roles instance = (Roles) getSession()
                    .get("com.gdit.capture.entity.Roles", id);
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


    public Roles findByName( String name) {
        log.log(Level.WARNING,"getting Roles instance with id: " + name);
        try {
	     begin();
            List<Roles> instances =  getSession().createQuery("select r from Roles r where r.roleName = :name")
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


    public List findByExample(Roles instance) {
        log.log(Level.WARNING,"finding Roles instance by example");
        try {
            List results = sessionFactory.getCurrentSession()
                    .createCriteria("com.gdit.capture.entity.Roles")
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

