package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;
import entities.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import errorhandling.MissingFieldsException;
import errorhandling.PersonNotFoundException;

import java.util.List;


public class PersonFacade implements IPersonFacade {
    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }

    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    @Override
    public PersonDTO addPerson(String fName, String lName, String phone) throws MissingFieldsException {
        if (fName == null || lName == null || phone == null) {
            throw new MissingFieldsException("Fields are missing!");
        }

        Person p = new Person(fName, lName, phone);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(p);
    }

    @Override
    public PersonDTO deletePerson(int id) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        Person person;
        try {
            em.getTransaction().begin();
            person = em.find(Person.class, id);
            if (person == null) {
                throw new PersonNotFoundException("Could not delete, Person id does not exist!");
            }
            em.remove(person);
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }

    }

    @Override
    public PersonDTO getPerson(int id) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        Person p = em.find(Person.class, id);
        if (p == null) {
            throw new PersonNotFoundException("Person id NOT found");
        } else {
            return new PersonDTO(p);
        }

    }

    @Override
    public PersonsDTO getAllPersons() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
        List<Person> persons = query.getResultList();
        return new PersonsDTO(persons);
    }

    @Override
    public PersonDTO editPerson(PersonDTO p) throws MissingFieldsException {
        if (p.getfName() == null || p.getlName() == null || p.getPhone() == null) {
            throw new MissingFieldsException("One or more fields are missing!");
        }
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Person tempPerson = em.find(Person.class, p.getId());
            tempPerson = tempPerson.updateFromDto(p);
            em.getTransaction().commit();
            return new PersonDTO(tempPerson);
        } finally {
            em.close();
        }
    }
}
