package org.spacepro.model.manager;

import org.spacepro.model.entity.Comment;
import org.spacepro.model.entity.Topic;
import org.spacepro.model.entity.User;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class TopicManager extends AbstractManager{

    public void updateTopic(Topic topic) {
        em.merge(topic);
    }

    public void addComment(User creator, Topic topic, Comment c){
        c.setCreator(creator);
        c.setTopic(topic);
        em.persist(c);
    }

    public List<Topic> getAllTopics(){
        return getAll(Topic.class);
    }

    public List<Comment> getCommentsByTopic(Topic topic) {
        Query q = em.createQuery("select c from Comment c where c.topic = :topic");
        q.setParameter("topic", topic);
        return q.getResultList();
    }
}
