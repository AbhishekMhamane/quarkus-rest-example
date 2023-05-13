package com.example.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

  @Override
  public Response toResponse(Exception exception) {
    ErrorMsg error = new ErrorMsg();
    error.setMessage(exception.getMessage());
    error.setCode(500);
    return Response.status(Status.INTERNAL_SERVER_ERROR).entity(error).build();
  }
}
