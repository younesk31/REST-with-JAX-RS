package facades;

import dtos.PersonDTO;
import entities.Person;

import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade fe = PersonFacade.getPersonFacade(emf);
        fe.addPerson("First 1", "Last 1","123");
        fe.addPerson("First 2", "Last 2","456");
    }
    
    public static void main(String[] args) {
        populate();
    }
}
