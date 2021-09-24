package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import errorhandling.MissingFieldsException;
import errorhandling.PersonNotFoundException;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("person")
public class PersonResource {

    private final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private final PersonFacade facade = PersonFacade.getPersonFacade(EMF);
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPerson(String person) throws MissingFieldsException {
        PersonDTO dto = gson.fromJson(person, PersonDTO.class);
        dto = facade.addPerson(dto.getfName(), dto.getlName(), dto.getPhone());
        return Response.ok(gson.toJson(dto), MediaType.APPLICATION_JSON).build();
    }

    @Path("{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editPerson(@PathParam("id") int id, String person) throws MissingFieldsException {
        PersonDTO dto = gson.fromJson(person, PersonDTO.class);
        dto.setId(id);
        dto = facade.editPerson(dto);
        return Response.ok(gson.toJson(dto), MediaType.APPLICATION_JSON).build();
    }

    @Path("{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deletePerson(@PathParam("id") int id) throws PersonNotFoundException {
        PersonDTO dto = facade.deletePerson(id);
        return Response.ok("{\"status\" : \"removed id:" + dto.getId() + "\"}", MediaType.APPLICATION_JSON).build();
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonById(@PathParam("id") int id) throws PersonNotFoundException {
        PersonDTO dto = facade.getPerson(id);
        return Response.ok(gson.toJson(dto), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllPersons() {
        return Response.ok(gson.toJson(facade.getAllPersons()), MediaType.APPLICATION_JSON).build();
    }
}