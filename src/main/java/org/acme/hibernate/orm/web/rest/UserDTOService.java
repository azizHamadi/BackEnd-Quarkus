package org.acme.hibernate.orm.web.rest;

import org.acme.hibernate.orm.domain.UserDTO;
import org.acme.hibernate.orm.repository.UserDTORepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("users")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class UserDTOService {
    private static final Logger LOGGER = Logger.getLogger(UserDTOService.class.getName());

    @Inject
    UserDTORepository userDTORepository;

    @GET
    public List<UserDTO> getAll() {
        return userDTORepository.findAll();
    }

    @POST
    public Response create(UserDTO userDTO) {
        userDTORepository.createUserDTO(userDTO);
        return Response.ok(userDTO).status(201).build();
    }

    @POST
    @Path("/me")
    public UserDTO findByUserName(UserDTO userDTO) {
        UserDTO currenUserDTO = userDTORepository.findByUserName(userDTO.getUsername());
        return currenUserDTO;
    }

}
