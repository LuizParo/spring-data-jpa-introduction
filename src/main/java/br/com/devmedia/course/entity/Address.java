package br.com.devmedia.course.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "address")
@NamedQuery(
        name = "Address.findByCity",
        query = "select a from Address a where a.city = ?1"
)
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Address.findByFullAddress",
                query = "select * from address where city = ?1 and street = ?2",
                resultClass = Address.class
        ),
        @NamedNativeQuery(
                name = "Address.findConcatAddress",
                query = "select funcConcatAddress(?1)"
        )
})
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum TypeAddress {
        RESIDENTIAL, COMMERCIAL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "city", nullable = false)
    private String city;
    
    @Column(name = "street", nullable = false)
    private String street;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type_address", nullable = false)
    private TypeAddress type;
    
    @ManyToMany
    @JoinTable(
            name = "person_address",
            joinColumns = @JoinColumn(name = "id_address"),
            inverseJoinColumns = @JoinColumn(name = "id_person")
    )
    private List<Person> people;
    
    @Deprecated
    public Address() {
        // Default constructor for JPA
    }
    
    public Address(String city, String street, TypeAddress type) {
        this.city = city;
        this.street = street;
        this.type = type;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public TypeAddress getType() {
        return type;
    }
    public void setType(TypeAddress type) {
        this.type = type;
    }
    public List<Person> getPeople() {
        return people;
    }
    public void setPeople(List<Person> people) {
        this.people = people;
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
        Address other = (Address) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "Address [id=" + id + ", city=" + city + ", street=" + street + ", type=" + type + ", people=" + people + "]";
    }
}