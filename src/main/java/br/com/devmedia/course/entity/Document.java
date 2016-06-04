package br.com.devmedia.course.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "document")
@NamedStoredProcedureQuery(
        name = "docs.procedureReplaceCPF",
        procedureName = "procReplaceCPF",
        parameters = {
                @StoredProcedureParameter(
                        mode = ParameterMode.IN,
                        name = "ID_IN",
                        type = Long.class
                ),
                @StoredProcedureParameter(
                        mode = ParameterMode.OUT,
                        name = "CPF_OUT",
                        type = String.class
                )
        }
)
public class Document implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "cpf", nullable = false, unique = true, length = 14)
    private String cpf;
    
    @Column(name = "rg", unique = true, length = 20)
    private String rg;
    
    @Deprecated
    public Document() {
        // Default constructor for JPA
    }

    public Document(String cpf, String rg) {
        this.cpf = cpf;
        this.rg = rg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
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
        Document other = (Document) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Document [id=" + id + ", cpf=" + cpf + ", rg=" + rg + "]";
    }
}