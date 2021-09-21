package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import entities.Person;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("person")
public class PersonResource {

    private final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String welcome() {
        return "{\"msg\":\"Person Query's\"}";
    }

    @Path("add/{fname}/{lname}/{phone}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String addPerson(@PathParam("fname") String fname, @PathParam("lname") String lname, @PathParam("phone") String phone) {
        return GSON.toJson("Added: " + FACADE.addPerson(fname, lname, phone));
    }

    @Path("edit/{id}/{fname}/{lname}/{phone}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String editPerson(@PathParam("id") int id, @PathParam("fname") String fname, @PathParam("lname") String lname, @PathParam("phone") String phone) {
        PersonDTO p = new PersonDTO(new Person(fname, lname, phone));
        p.setId(id);
        return GSON.toJson("Edited: " + FACADE.editPerson(p));
    }

    @Path("delete/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    public String deletePerson(@PathParam("id") int id) {
        //String info = "{\"Deleted ID\":\""+id+"\"}";
        return GSON.toJson(FACADE.deletePerson(id));
    }

    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPerson(@PathParam("id") int id) {
        return GSON.toJson(FACADE.getPerson(id));
    }

    @Path("getall")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllPersons() {
        return GSON.toJson("ALL Persons: " + FACADE.getAllPersons());
    }
}
