package org.spacepro.rest.resource;

import org.spacepro.service.user.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;

@Path("/user")
@Consumes("application/json")
@Produces("application/json")
public class UserResource {

    @Inject
    UserService userService;

}
