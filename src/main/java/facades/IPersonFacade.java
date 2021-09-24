package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;


import java.util.List;

public interface IPersonFacade {
    public PersonDTO addPerson(String fName, String lName, String phone) throws Exception;
    public PersonDTO deletePerson(int id) throws Exception;
    public PersonDTO getPerson(int id) throws Exception;
    public PersonsDTO getAllPersons();
    public PersonDTO editPerson(PersonDTO p) throws Exception;
}

