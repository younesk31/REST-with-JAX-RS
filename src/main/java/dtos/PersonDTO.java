package dtos;

import entities.Address;
import entities.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PersonDTO {
    private Integer id;
    private String fName;
    private String lName;
    private String phone;
    private Address address;

    public PersonDTO() {
    }

    public PersonDTO(Person p) {
        this.fName = p.getFirstName();
        this.lName = p.getLastName();
        this.phone = p.getPhone();
        this.id = p.getId();
        this.address = p.getAddress();
    }

    public PersonDTO(String fn, String ln, String phone, Address address) {
        this.fName = fn;
        this.lName = ln;
        this.phone = phone;
        this.address = address;
    }

    public static List<PersonDTO> getDtos(List<Person> personList){
        List<PersonDTO> personDTOS = new ArrayList();
        personList.forEach(rm->personDTOS.add(new PersonDTO(rm)));
        return personDTOS;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return id == personDTO.id && fName.equals(personDTO.fName) && lName.equals(personDTO.lName) && phone.equals(personDTO.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fName, lName, phone);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("fName='").append(fName).append('\'');
        sb.append(", lName='").append(lName).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", address=").append(address).append('\'');
        sb.append("id=").append(id);
        sb.append('}');
        return sb.toString();
    }

    public static class PersonsDTO {

        List<PersonDTO> all = new ArrayList();

        public PersonsDTO(List<Person> personEntities) {
            personEntities.forEach((p) -> { all.add(new PersonDTO(p));
            });
        }

    }
}

