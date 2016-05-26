package br.com.devmedia.course.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "phone")
public class Phone implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum TypePhone {
        RESIDENTIAL, CELLPHONE, COMMERCIAL;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type_phone", nullable = false)
    private TypePhone type;
    
    @Column(name = "number", nullable = false)
    private String number;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_person")
    private Person person;
    
    @Deprecated
    public Phone() {
        // Default constructor for JPA
    }

    public Phone(TypePhone type, String number, Person person) {
        this.type = type;
        this.number = number;
        this.person = person;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypePhone getType() {
        return type;
    }

    public void setType(TypePhone type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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
        Phone other = (Phone) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Phone [id=" + id + ", type=" + type + ", number=" + number + ", person=" + person + "]";
    }
}