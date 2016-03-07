package org.spacepro.rest.resource;

import org.spacepro.model.entity.Topic;
import org.spacepro.model.util.OrikaMapping;
import org.spacepro.rest.vo.TopicVO;
import org.spacepro.service.topic.TopicService;
import org.spaceproservice.exception.EntityException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Path("/topic")
@Consumes("application/json")
@Produces("application/json")
public class TopicResource {

    @Context
    HttpServletRequest request;
    @Context
    UriInfo info;
    @Inject
    TopicService topicService;
    @Inject
    OrikaMapping oMap;

    @Path("/")
    @GET
    public Response getTopicList() {
        List<Topic> topics = topicService.getAllTopics();
        List<TopicVO> topicsVO = new ArrayList<>();
        for (Topic t : topics) {
            TopicVO tVO = new TopicVO(t.getId(), t.getHeader(), t.getContent(), t.getCreationTime(), t.getCreator().getLoginName());
            topicsVO.add(tVO);
        }
        return Response.ok().entity(topicsVO).build();
    }

    @Path("/")
    @POST
    public Response saveNewTopic(TopicVO topicVo) throws EntityException {
        Topic topic = oMap.mapper().map(topicVo, Topic.class);
        topic = topicService.saveNewTopic(topic, getLoginName());
        TopicVO topicVO = oMap.mapper().map(topic, TopicVO.class);
        Response r = Response
                .created(info.getAbsolutePathBuilder().path(topic.getId().toString()).build())
                .entity(topicVO).build();
        return r;
    }

    @Path("{id}")
    @PUT
    public Response updateTopic(@NotNull @PathParam("id") Long id, TopicVO topicVO) throws EntityException {
        Topic topic = oMap.mapper().map(topicVO, Topic.class);
        topic.setId(id);
        topic = topicService.updateTopic(topic, getLoginName());
        topicVO = oMap.mapper().map(topic, TopicVO.class);
        return Response.ok().entity(topicVO).build();
    }

    @Path("{id}/comment")
    public CommentResource commentSubResource() {
        return new CommentResource(getLoginName(), topicService, oMap);
    }

    private String getLoginName() {
        return (String) request.getAttribute("loginName");
    }
}
