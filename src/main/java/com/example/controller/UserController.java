package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

  private final UserService userService;

  @Inject
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GET
  public Response getAllUsers() {
    return Response.ok(userService.getAllUsers()).build();
  }

  @GET
  @Path("/{id}")
  public Response getUserById(@PathParam("id") long id) {
    return Response.ok(userService.getUserById(id)).build();
  }

  @POST
  @Path("/add")
  public Response createUser(User user) {
    return Response
      .status(Response.Status.CREATED)
      .entity(userService.createUser(user))
      .build();
  }

  @PUT
  @Path("/update/{id}")
  public Response updateUser(@PathParam("id") long userId, User user) {
    return Response
      .status(Response.Status.OK)
      .entity(userService.updateUser(userId, user))
      .build();
  }

  @DELETE
  @Path("/{id}")
  public Response deleteUser(@PathParam("id") long userId) {
    userService.deleteUserByUserId(userId);
    return Response.status(Response.Status.NO_CONTENT).build();
  }
}
