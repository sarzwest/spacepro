package org.spacepro.service.topic;

import org.spacepro.model.entity.Comment;
import org.spacepro.model.entity.Topic;
import org.spacepro.model.entity.User;
import org.spacepro.model.manager.TopicManager;
import org.spacepro.model.util.OrikaMapping;
import org.spacepro.service.user.UserService;
import org.spaceproservice.exception.EntityException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TopicService {

    @Inject
    UserService userService;
    @Inject
    TopicManager topicManager;
    @Inject
    OrikaMapping oMap;

    public Topic saveNewTopic(Topic topic, String loginName) throws EntityException {
        User u = userService.getUser(loginName);
        topic.setCreator(u);
        topicManager.persist(topic);
        return topic;
    }

    public Topic updateTopic(Topic t, String loginName) throws EntityException {
        Topic topicDB = topicManager.findOne(t.getId(), t.getClass());
        checkCreator(loginName, topicDB);
        oMap.mapper().map(t, topicDB);
        topicManager.updateTopic(topicDB);
        return topicDB;
    }

    public Comment addComment(String loginName, Long topicId, Comment comment) throws EntityException {
        User userDB = userService.getUser(loginName);
        Topic topicDB = getTopic(topicId);
        topicManager.addComment(userDB, topicDB, comment);
        return comment;
    }

    public Topic getTopic(Long topicId) throws EntityException {
        Topic topic = topicManager.findOne(topicId, Topic.class);
        if (topic == null) {
            throw new EntityException("Topic with id " + topicId + " does not exist");
        }
        return topic;
    }

    private void checkCreator(String loginName, Topic topicDB) throws EntityException {
        if(!topicDB.getCreator().getLoginName().equals(loginName)){
            throw new EntityException("Topic "+topicDB.getId()+" was not created by user " + loginName);
        }
    }

    public List<Topic> getAllTopics() {
        return topicManager.getAllTopics();
    }

    public List<Comment> getCommentsByTopic(Long topicId) throws EntityException {
        return topicManager.getCommentsByTopic(getTopic(topicId));
    }
}
