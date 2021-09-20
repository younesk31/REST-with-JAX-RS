package facades;

import dtos.PersonDTO;
import entities.Person;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import utils.EMF_Creator;

import java.util.Date;
import java.util.List;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade implements IPersonFacade{

    private static PersonFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private PersonFacade() {}

    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade fe = getPersonFacade(emf);
    }

    @Override
    public PersonDTO addPerson(String fName, String lName, String phone) {
        Person person = new Person(fName, lName, phone);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }

    @Override
    public PersonDTO editPerson(PersonDTO p) {
        EntityManager em = emf.createEntityManager();
        try {
            Person pp = em.find(Person.class, p.getId());
            pp.setFirstName(p.getfName());
            pp.setLastName(p.getlName());
            pp.setPhone(p.getPhone());
            pp.setLastEdited(new Date());
            em.getTransaction().begin();
            em.merge(pp);
            em.getTransaction().commit();
            return p;
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO deletePerson(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Person p = em.find(Person.class, id);
            em.getTransaction().begin();
            em.remove(p);
            em.getTransaction().commit();
            return new PersonDTO(p);
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO getPerson(int id) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p where p.id = :id", Person.class);
        query.setParameter("id",id);
        PersonDTO pdto = new PersonDTO((query.getSingleResult()));
        return pdto;
    }

    @Override
    public List<PersonDTO> getAllPersons() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
        List<Person> personList = query.getResultList();
        return PersonDTO.getDtos(personList);
    }

}
