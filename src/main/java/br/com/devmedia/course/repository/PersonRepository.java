package br.com.devmedia.course.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.devmedia.course.entity.Person;

public interface PersonRepository extends JpaRepository<Person , Long> {
    // Search by age equal the given parameter.
    List<Person> findByAge(Integer age);
    
    // Search by age different from the given parameter.
    List<Person> findByAgeNot(Integer age);
    
    // Search by first name equal the given parameter.
    List<Person> findByFirstNameLike(String firstName);
    
    // Search by first name different from the given parameter.
    List<Person> findByFirstNameNotLike(String firstName);
    
    // Search by first and last name.
    Person findByFirstNameAndLastName(String firstName, String lastName);
    
    // Search by age or first name.
    List<Person> findByAgeOrFirstName(Integer age, String firstName);
    
    // Search by age between a range of age.
    List<Person> findByAgeBetween(int min, int max);
    
    // Serach by last name and age between a range of age.
    List<Person> findByLastNameAndAgeBetween(String lastName, int min, int max);
}