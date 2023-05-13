package com.example.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomExceptionHandler
  implements ExceptionMapper<CustomException> {

  @Override
  public Response toResponse(CustomException exception) {
    ErrorMsg error = new ErrorMsg();
    error.setMessage(exception.getMessage());

    if (exception.getCode() == 404) {
      return Response.status(Status.NOT_FOUND).entity(error).build();
    } else if (exception.getCode() == 409) {
      return Response.status(Status.CONFLICT).entity(error).build();
    }

    return Response.status(Status.INTERNAL_SERVER_ERROR).entity(error).build();
  }
}
