/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.entity;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ehab
 */
public class FieldHome02 {

    public long persist(Field transientInstance) {
        long id = 0;
        Session session = ApplicationSession.getSession();
        Transaction transaction = session.beginTransaction();
        id = (Long) session.save(transientInstance);
        transaction.commit();
        return id;
    }

    public long update(Field transientInstance) {
        Session session = ApplicationSession.getSession();
        Transaction transaction = session.beginTransaction();
        session.update(transientInstance);
        transaction.commit();
        return transientInstance.getId();
    }

    public void delete(Field transientInstance) {
        Session session = ApplicationSession.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(transientInstance);
        transaction.commit();
    }

    public List<Field> listAllFields() {
        List<Field> instances = null;
        Session session = ApplicationSession.getSession();
        instances = session.createQuery("select f from Field f").list();
        return instances;
    }
    public List<Field> listCategoryFields(Category cat) {
        List<Field> instances = null;
        Session session = ApplicationSession.getSession();
        instances = session.createQuery("select f from Field f inner join f.associatedCategories c "
                + "where c.id = :id")
                .setParameter("id", cat.getId())
                .list();
        return instances;
    }


    public Field getFieldByID(Long id){
        Field instance = null;
        Session session = ApplicationSession.getSession();
        instance =(Field)session.createQuery("select f from Field f where f.id ="+id).list().get(0);
        return instance;
    }
}
