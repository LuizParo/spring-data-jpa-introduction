package br.com.devmedia.course.repository;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.devmedia.course.entity.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-data.xml" })
public class PersonRepositoryTest {
    private Person personOne;
    private Person personTwo;
    private Person personThree;
    
    @Autowired
    private PersonRepository personRepository;
    
    @Before
    public void setUp() {
        this.personOne = new Person("Luiz", "Paro", 24);
        this.personTwo = new Person("João", "Silva", 30);
        this.personThree = new Person("Carlos", "Paro", 22);
        
        this.personRepository.save(this.personOne);
        this.personRepository.save(this.personTwo);
        this.personRepository.save(this.personThree);
    }
    
    @After
    public void tearDown() {
        this.personRepository.delete(this.personOne.getId());
        this.personRepository.delete(this.personTwo.getId());
        this.personRepository.delete(this.personThree.getId());
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
}