package org.spacepro.rest.resource;

import org.spacepro.model.entity.Comment;
import org.spacepro.model.util.OrikaMapping;
import org.spacepro.rest.vo.CommentVO;
import org.spacepro.service.topic.TopicService;
import org.spaceproservice.exception.EntityException;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Consumes("application/json")
@Produces("application/json")
public class CommentResource {

    private String loginName;
    TopicService topicService;
    OrikaMapping oMap;

    public CommentResource(String loginName, TopicService topicService, OrikaMapping oMap) {
        this.loginName = loginName;
        this.topicService = topicService;
        this.oMap = oMap;
    }

    @Path("/")
    @GET
    public Response getCommentsByTopic(@NotNull @PathParam("id") Long topicId) throws EntityException {
        List<Comment> commentList = topicService.getCommentsByTopic(topicId);
        List<CommentVO> commentVOList = new ArrayList<>();
        for(Comment c:commentList){
            commentVOList.add(new CommentVO(c.getContent(), c.getCreationTime(), c.getCreator().getLoginName()));
        }
        return Response.ok().entity(commentVOList).build();
    }

    @Path("/")
    @POST
    public Response saveNewComment(@Context UriInfo info, @NotNull @PathParam("id") Long topicId, CommentVO commentVO) throws EntityException {
        Comment comment = oMap.mapper().map(commentVO, Comment.class);
        comment = topicService.addComment(loginName, topicId, comment);
        oMap.mapper().map(comment, commentVO);
        return Response
                .created(info.getAbsolutePathBuilder().path(comment.getId().toString()).build())
                .entity(commentVO).build();
    }
}
