/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.entity;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author ehab
 */
public class DocumentDataHome {

    private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public DocumentDataHome() {
    }

    public static Session getSession() {
        Session session = (Session) DocumentDataHome.session.get();
        if (session == null) {
            session = sessionFactory.openSession();
            DocumentDataHome.session.set(session);
        }
        return session;
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
        } catch (HibernateException e) {
        }

        try {
            getSession().close();
        } catch (HibernateException e) {
        }
        DocumentDataHome.session.set(null);
    }

    public static void close() {
        getSession().close();
        DocumentDataHome.session.set(null);
    }

    public long persist(DocumentData transientInstance) {
        try {
            begin();
            getSession().save(transientInstance);
            commit();
            return transientInstance.getId();
        } catch (RuntimeException re) {
            re.printStackTrace();
            throw re;
        }
    }

    public long update(DocumentData transientInstance) {
        try {
            begin();
            getSession().saveOrUpdate(transientInstance);
            commit();
            return transientInstance.getId();
        } catch (RuntimeException re) {
            re.printStackTrace();
            throw re;
        }
    }

    public List<DocumentData> getAllDocuments() {
        List<DocumentData> documents = null;
        try {
                begin();
                documents = getSession().createQuery("select d from DocumentData d").list();
        } catch (Exception e) {
            e.printStackTrace();
            documents = null;
        }
        return documents;
    }

    public List<DocumentData> getDocumentDataByID(Long id) {
        List<DocumentData> documents = null;
        try {
                begin();
                documents = getSession().createQuery("select d from DocumentData d where d.docId ="+id).list();
        } catch (Exception e) {
            e.printStackTrace();
            documents = null;
        }
        return documents;
    }

    public DocumentData getDocumentDataByDocIDandFieldID(long docID, long fieldID) {
        DocumentData documentData = null;
        try {
                begin();
                documentData = (DocumentData)getSession().createQuery("select d from DocumentData d  " +
                        "where d.docId = :docID and d.fieldId= :fieldID")
                        .setParameter("docID", docID)
                        .setParameter("fieldID", fieldID)
                        .list().get(0);
        } catch (Exception e) {
//            e.printStackTrace();
            documentData = null;
        }
        return documentData;
    }
}
