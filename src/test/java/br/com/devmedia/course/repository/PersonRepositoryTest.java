package br.com.devmedia.course.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.devmedia.course.CourseSpringDataApplication;
import br.com.devmedia.course.entity.Document;
import br.com.devmedia.course.entity.Person;
import br.com.devmedia.course.entity.Phone;
import br.com.devmedia.course.entity.Phone.TypePhone;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CourseSpringDataApplication.class)
public class PersonRepositoryTest {
    private Person personOne;
    private Person personTwo;
    private Person personThree;
    
    @Autowired
    private PersonRepository personRepository;
    
    @Before
    public void setUp() {
        this.personOne = new Person("Luiz", "Paro", 24, new Document("123.654.789-98", "58.963.587-85"));
        this.personTwo = new Person("João", "Silva", 30, new Document("987.456.321-14", "85.742.658.74"));
        this.personThree = new Person("Carlos", "Paro", 22, new Document("147.852.369.65", "32.657.951-65"));
        
        this.personRepository.save(this.personOne);
        this.personRepository.save(this.personTwo);
        this.personRepository.save(this.personThree);
    }
    
    @Test
    public void shouldSavePeopleInABulkOperation() {
        List<Person> people = Arrays.asList(new Person("José", "Silva", 24), new Person("Pedro", "Marcos", 30), new Person("Maria", "Cabral", 22));
        this.personRepository.save(people);
        for (Person person : people) {
            Assert.assertNotNull(person.getId());
        }
    }
    
    @Test
    public void shouldDeletePeopleInABulkOperation() {
        List<Person> people = Arrays.asList(this.personOne, this.personTwo, this.personThree);
        this.personRepository.deleteInBatch(people);
        
        Assert.assertEquals(0, this.personRepository.count());
    }
    
    @Test
    public void shouldSearchPeopleInAscOrder() {
        Order order = new Sort.Order(Direction.ASC, "lastName");
        Sort sort = new Sort(order);
        
        List<Person> people = this.personRepository.findAll(sort);
        for (Person person : people) {
            Assert.assertNotNull(person.getId());
        }
    }
    
    @Test
    public void shouldSearchPeopleInDescOrder() {
        Order order = new Sort.Order(Direction.DESC, "firstName");
        Sort sort = new Sort(order);
        
        List<Person> people = this.personRepository.findAll(sort);
        for (Person person : people) {
            Assert.assertNotNull(person.getId());
        }
    }
    
    @Test
    public void shouldFindPeopleByTheirIds() {
        List<Long> ids = Arrays.asList(this.personOne.getId(), this.personTwo.getId(), this.personThree.getId());
        List<Person> people = this.personRepository.findAll(ids);
        for (Person person : people) {
            Assert.assertNotNull(person.getId());
        }
    }
    
    @Test
    public void shouldIdentifyThatPersonExists() {
        boolean exists = this.personRepository.exists(this.personOne.getId());
        Assert.assertTrue(exists);
    }
    
    @Test
    public void shouldIdentifyThatPersonDoesNotExists() {
        boolean exists = this.personRepository.exists(999L);
        Assert.assertFalse(exists);
    }
    
    @Test
    public void shouldPaginatePeople() {
        Page<Person> pageOne = this.personRepository.findAll(new PageRequest(0, 2));
        for (Person person : pageOne) {
            Assert.assertNotNull(person.getId());
        }
        
        Page<Person> pageTwo = this.personRepository.findAll(new PageRequest(1, 2));
        for (Person person : pageTwo) {
            Assert.assertNotNull(person.getId());
        }
    }
    
    @Test
    public void shouldCountPeopleOnDatabase() {
        Assert.assertEquals(3L, this.personRepository.count());
    }
    
    @Test
    public void shouldFindAllPeople() {
        List<Person> allPeople = this.personRepository.findAll();
        Assert.assertFalse(allPeople.isEmpty());
        Assert.assertEquals(3, allPeople.size());
        for (Person person : allPeople) {
            Assert.assertNotNull(person.getId());
        }
    }
    
    @Test
    public void shouldFindPeopleByAge() {
        List<Person> people = this.personRepository.findByAge(24);
        Assert.assertFalse(people.isEmpty());
        for (Person person : people) {
            Assert.assertEquals(new Integer(24), person.getAge());
        }
    }
    
    @Test
    public void shouldFindPeopleDifferentFromAge() {
        List<Person> people = this.personRepository.findByAgeNot(24);
        Assert.assertFalse(people.isEmpty());
        for (Person person : people) {
            Assert.assertFalse(person.getAge().equals(24));
        }
    }
    
    @Test
    public void shouldFindPeopleByFirstNameLike() {
        List<Person> people = this.personRepository.findByFirstNameLike("Luiz");
        Assert.assertFalse(people.isEmpty());
        for (Person person : people) {
            Assert.assertEquals("Luiz", person.getFirstName());
        }
    }
    
    @Test
    public void shouldFindPeopleDifferentFromFirstName() {
        List<Person> people = this.personRepository.findByFirstNameNotLike("Luiz");
        Assert.assertFalse(people.isEmpty());
        for (Person person : people) {
            Assert.assertFalse(person.getFirstName().equals("Luiz"));
        }
    }
    
    @Test
    public void shouldFindPersonByFirstNameAndLastName() {
        Person person = this.personRepository.findByFirstNameAndLastName("Luiz", "Paro");
        Assert.assertEquals("Luiz Paro", person.getFirstName() + " " + person.getLastName());
    }
    
    @Test
    public void shouldFindPeopleByAgeOrFirstName() {
        List<Person> people = this.personRepository.findByAgeOrFirstName(24, "Carlos");
        Assert.assertFalse(people.isEmpty());
        Assert.assertEquals(2, people.size());
    }
    
    @Test
    public void shouldFindPeopleByAgeBetween() {
        List<Person> people = this.personRepository.findByAgeBetween(22, 24);
        Assert.assertFalse(people.isEmpty());
        Assert.assertEquals(2, people.size());
        for (Person person : people) {
            Assert.assertTrue(person.getAge() >= 22 && person.getAge() <= 24);
        }
    }
    
    @Test
    public void shouldFindPeopleByLastNameAndAgeBetween() {
        List<Person> people = this.personRepository.findByLastNameAndAgeBetween("Paro", 22, 23);
        Assert.assertFalse(people.isEmpty());
        Assert.assertEquals(1, people.size());
        for (Person person : people) {
            Assert.assertEquals("Paro", person.getLastName());
            Assert.assertTrue(person.getAge() >= 22 && person.getAge() <= 23);
        }
    }
    
    @Test
    public void shouldFindPeopleByAgeGreaterThan() {
        List<Person> people = this.personRepository.findByAgeGreaterThan(22);
        Assert.assertFalse(people.isEmpty());
        Assert.assertEquals(2, people.size());
        for (Person person : people) {
            Assert.assertTrue(person.getAge() > 22);
        }
    }
    
    @Test
    public void shouldFindPeopleByAgeLessThan() {
        List<Person> people = this.personRepository.findByAgeLessThan(30);
        Assert.assertFalse(people.isEmpty());
        Assert.assertEquals(2, people.size());
        for (Person person : people) {
            Assert.assertTrue(person.getAge() < 30);
        }
    }
    
    @Test
    public void shouldFindPeopleByAgeGreaterThanEqual() {
        List<Person> people = this.personRepository.findByAgeGreaterThanEqual(24);
        Assert.assertFalse(people.isEmpty());
        Assert.assertEquals(2, people.size());
        for (Person person : people) {
            Assert.assertTrue(person.getAge() >= 24);
        }
    }
    
    @Test
    public void shouldFindPeopleByAgeLessThanEqual() {
        List<Person> people = this.personRepository.findByAgeLessThanEqual(24);
        Assert.assertFalse(people.isEmpty());
        Assert.assertEquals(2, people.size());
        for (Person person : people) {
            Assert.assertTrue(person.getAge() <= 24);
        }
    }
    
    @Test
    public void shouldFindPeopleByFirstNameGreaterThan() {
        List<Person> people = this.personRepository.findByFirstNameGreaterThan("João");
        Assert.assertFalse(people.isEmpty());
        Assert.assertEquals(1, people.size());
        for (Person person : people) {
            Assert.assertEquals("Luiz", person.getFirstName());
        }
    }
    
    @Test
    public void shouldFindPeopleByAgeIn() {
        List<Person> people = this.personRepository.findByAgeIn(22, 24);
        Assert.assertFalse(people.isEmpty());
        Assert.assertEquals(2, people.size());
        for (Person person : people) {
            Assert.assertTrue(person.getAge() == 22 || person.getAge() == 24);
        }
    }
    
    @Test
    public void shouldFindPeopleByAgeNotIn() {
        List<Person> people = this.personRepository.findByAgeNotIn(22, 24);
        Assert.assertFalse(people.isEmpty());
        Assert.assertEquals(1, people.size());
        for (Person person : people) {
            Assert.assertTrue(person.getAge() != 22 || person.getAge() != 24);
        }
    }
    
    @Test
    public void shouldFindPeopleByFirstNameIgnoringCase() {
        List<Person> people = this.personRepository.findByFirstNameIgnoreCase("luiz");
        Assert.assertFalse(people.isEmpty());
        Assert.assertEquals(1, people.size());
        for (Person person : people) {
            Assert.assertEquals("Luiz", person.getFirstName());
        }
    }
    
    @Test
    public void shouldFindPeopleByDocumentIsNull() {
        this.personOne.setDocument(null);
        this.personRepository.saveAndFlush(this.personOne);
        
        List<Person> people = this.personRepository.findByDocumentIsNull();
        Assert.assertFalse(people.isEmpty());
        Assert.assertEquals(1, people.size());
        for (Person person : people) {
            Assert.assertTrue(person.getDocument() == null);
        }
    }
    
    @Test
    public void shouldFindPeopleByDocumentIsNotNull() {
        List<Person> people = this.personRepository.findByDocumentIsNotNull();
        Assert.assertFalse(people.isEmpty());
        Assert.assertEquals(3, people.size());
        for (Person person : people) {
            Assert.assertTrue(person.getDocument() != null);
        }
    }
    
    @Test
    public void shouldFindPeopleByPhonesNumberStartingWith() {
        this.personOne.setPhones(new ArrayList<>(Arrays.asList(new Phone(TypePhone.RESIDENTIAL, "19 5487-9632", this.personOne))));
        this.personRepository.saveAndFlush(this.personOne);
        
        List<Person> people = this.personRepository.findByPhonesNumberStartingWith("19 5487");
        Assert.assertFalse(people.isEmpty());
        Assert.assertEquals(1, people.size());
        for (Person person : people) {
            Assert.assertTrue(person.getPhones().get(0).getNumber().startsWith("19 5487"));
        }
    }
    
    @Test
    public void shouldFindPeopleByAgeGreaterThanOrderByFirstNameAscLastNameAsc() {
        Person other = new Person("João", "Pedro", 30);
        this.personRepository.saveAndFlush(other);
        
        List<Person> people = this.personRepository.findByAgeGreaterThanOrderByFirstNameAscLastNameAsc(29);
        Assert.assertFalse(people.isEmpty());
        Assert.assertEquals(2, people.size());
        for (Person person : people) {
            Assert.assertEquals("João", person.getFirstName());
        }
    }
    
    @Test
    public void shouldFindPeopleByFirstName() {
        List<Person> people = this.personRepository.findByFirstName("Luiz");
        Assert.assertFalse(people.isEmpty());
        Assert.assertEquals(1, people.size());
        for (Person person : people) {
            Assert.assertEquals("Luiz", person.getFirstName());
        }
    }
    
    @Test
    public void shouldFindPeopleByFirstNameOrAge() {
        List<Person> people = this.personRepository.findByFirstNameOrAge("Luiz", 30);
        Assert.assertFalse(people.isEmpty());
        Assert.assertEquals(2, people.size());
        for (Person person : people) {
            Assert.assertTrue(person.getFirstName().equals("Luiz") || person.getAge() == 30);
        }
    }
    
    @Test
    public void shouldFindPeopleByFirstNameAndAge() {
        List<Person> people = this.personRepository.findByFirstNameAndAge(24, "Luiz");
        Assert.assertFalse(people.isEmpty());
        Assert.assertEquals(1, people.size());
        for (Person person : people) {
            Assert.assertEquals("Luiz", person.getFirstName());
            Assert.assertEquals(new Integer(24), person.getAge());
        }
    }
    
    @Test
    public void shouldFindPeopleByDocumentCPFEndsWith() {
        List<Person> people = this.personRepository.findByDocumentCPFEndsWith("789-98");
        Assert.assertFalse(people.isEmpty());
        Assert.assertEquals(1, people.size());
        for (Person person : people) {
            Assert.assertTrue(person.getDocument().getCpf().endsWith("789-98"));
        }
    }
    
    @Test
    public void shouldFindPeopleByAgeBetweenUsingQueryAnnotation() {
        List<Person> people = this.personRepository.findByAgeBetween(22, 24);
        Assert.assertFalse(people.isEmpty());
        Assert.assertEquals(2, people.size());
        for (Person person : people) {
            Assert.assertTrue(person.getAge() >= 22 && person.getAge() <= 24);
        }
    }
    
    @Test
    public void shouldFindPeopleByFirstNames() {
        List<Person> people = this.personRepository.findByFirstNames("Luiz", "Carlos");
        Assert.assertFalse(people.isEmpty());
        Assert.assertEquals(2, people.size());
        for (Person person : people) {
            Assert.assertTrue(person.getFirstName().equals("Luiz") || person.getFirstName().equals("Carlos"));
        }
    }
}