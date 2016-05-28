package br.com.devmedia.course.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "person",
    indexes = {
            @Index(columnList = "first_name, last_name", name = "idx_person_name", unique = true)
    }
)
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;
    
    @Column(name = "last_name", nullable = false, length = 60)
    private String lastName;
    
    @Column(name = "age", nullable = false)
    private Integer age;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_document")
    private Document document;
    
    @OneToMany(mappedBy = "person", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Phone> phones = new ArrayList<>();
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "person_address",
            joinColumns = @JoinColumn(name = "id_person"),
            inverseJoinColumns = @JoinColumn(name = "id_address")
    )
    private List<Address> addresses = new ArrayList<>();
    
    @Deprecated
    public Person() {
        // Default constructor for JPA
    }
    
    public Person(String firstName, String lastName, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
    
    public Person(String firstName, String lastName, Integer age, Document document) {
        this(firstName, lastName, age);
        this.document = document;
    }
    
    public void addPhone(Phone phone) {
        if(this.phones == null) {
            this.phones = new ArrayList<>();
        }
        phone.setPerson(this);
        this.phones.add(phone);
    }
    
    public void delPhone(Phone phone) {
        if(this.phones != null) {
            this.phones.remove(phone);
        }
    }
    
    public void addAddress(Address address) {
        if(this.addresses == null) {
            this.addresses = new ArrayList<>();
        }
        this.addresses.add(address);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    
    public Document getDocument() {
        return document;
    }
    
    public void setDocument(Document document) {
        this.document = document;
    }
    
    public List<Phone> getPhones() {
        return phones;
    }
    
    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }
    
    public List<Address> getAddresses() {
        return addresses;
    }
    
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age
                + ", document=" + document + "]";
    }
}