package org.example.dao;

import org.example.interfaces.EntityClass;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public abstract class EntityManager<T extends EntityClass> {

    private final Class<T> clazz;
    private final Session session;

    public EntityManager(Class<T> clazz, Session session) {
        this.clazz = clazz;
        this.session = session;
    }

    public T getById(final long id) {
        return session.get(clazz, id);
    }

    public List<T> getItems(int from, int count) {
        return session.createQuery("FROM " + clazz.getName(), clazz)
                .setMaxResults(count)
                .setFirstResult(from)
                .list();
    }

    public List<T> getAll() {
        return session.createQuery("FROM " + clazz.getName(), clazz)
                .list();
    }

    public long getCount() {
        return session.createQuery("SELECT COUNT(*) FROM " + clazz.getName(), Long.class)
                .uniqueResult();
    }

    public T save(final T entity) {
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        return entity;
    }

    public T update(final T entity) {
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        return entity;
    }

    public boolean delete(final T entity) {
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.delete(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteById(final long entityId) {
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            final T entity = getById(entityId);
            session.delete(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }
}