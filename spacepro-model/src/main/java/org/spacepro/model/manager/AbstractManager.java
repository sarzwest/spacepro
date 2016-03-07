package org.spacepro.model.manager;

import org.spacepro.model.entity.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class AbstractManager {

    @PersistenceContext
    protected EntityManager em;

    public Long persist(AbstractEntity e){
        em.persist(e);
        return e.getId();
    }

    public <T> T findOne(Long id, Class<T> clazz){
        return em.find(clazz, id);
    }

    protected <T> List<T> getAll(Class<T> clazz){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> root = cq.from(clazz);
        cq.select(root);
        TypedQuery<T> q = em.createQuery(cq);
        return q.getResultList();
    }
}
