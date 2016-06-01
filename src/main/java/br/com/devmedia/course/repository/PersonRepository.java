package br.com.devmedia.course.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    
    // Search by last name and age between a range of age.
    List<Person> findByLastNameAndAgeBetween(String lastName, int min, int max);
    
    // Search by a age greater than the given parameter.
    List<Person> findByAgeGreaterThan(Integer age);
    
    // Search by a age lesser than the given parameter.
    List<Person> findByAgeLessThan(Integer age);
    
    // Search by a age greater or equal than the given parameter.
    List<Person> findByAgeGreaterThanEqual(Integer age);
    
    // Search by a age lesser or equal than the given parameter.
    List<Person> findByAgeLessThanEqual(Integer age);
    
    // Search by first name greater than the given parameter.
    List<Person> findByFirstNameGreaterThan(String firstName);
    
    // Search by age in the given array or collection of ages.
    List<Person> findByAgeIn(Integer... ages);
    
    // Search by age not in the given array or collection of ages.
    List<Person> findByAgeNotIn(Integer... ages);
    
    // Search by first name ignoring upper and lower case.
    List<Person> findByFirstNameIgnoreCase(String firstName);
    
    // Search by document being null.
    List<Person> findByDocumentIsNull();
    
    // Search by document not being null.
    List<Person> findByDocumentIsNotNull();
    
    // Search by phone number mapped in person.
    List<Person> findByPhonesNumberStartingWith(String number);
    
    // Search by age ordering by first and last name asc.
    List<Person> findByAgeGreaterThanOrderByFirstNameAscLastNameAsc(Integer age);
    
    // Search by first name using the annotation @Query.
    @Query("select p from Person p where p.firstName like ?1")
    List<Person> findByFirstName(String firstName);
    
    // Search by first name or age using the annotation @Query.
    @Query("select p from Person p where p.firstName like ?1 or p.age = ?2")
    List<Person> findByFirstNameOrAge(String firstName, Integer age);
    
    // Search by first name and age using the annotation @Query.
    @Query("select p from Person p where p.firstName like ?2 and p.age = ?1")
    List<Person> findByFirstNameAndAge(Integer age, String firstName);
    
    // Search by CPF ending with the given parameter using the annotation @Query.
    @Query("select p from Person p where p.document.cpf like %?1")
    List<Person> findByDocumentCPFEndsWith(String value);
    
    // Search by age between the given parameters using the annotation @Query.
    @Query("select p from Person p where p.age >= :min and p.age <= :max")
    List<Person> findByAgeBetween(@Param("min") Integer start, @Param("max") Integer end);
    
    // Search by first names between the given parameters using the annotation @Query.
    @Query("select p from Person p where p.firstName in :names order by p.age asc")
    List<Person> findByFirstNames(@Param("names") String... firstNames);
}