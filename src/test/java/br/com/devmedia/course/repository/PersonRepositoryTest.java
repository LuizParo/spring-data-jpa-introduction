package br.com.devmedia.course.repository;

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
    public void shouldFindPeopleByFirstName() {
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
}