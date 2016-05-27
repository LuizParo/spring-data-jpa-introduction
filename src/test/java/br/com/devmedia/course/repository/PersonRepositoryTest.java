package br.com.devmedia.course.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.devmedia.course.CourseSpringDataApplication;
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
        this.personOne = new Person("Luiz", "Paro", 24);
        this.personTwo = new Person("João", "Silva", 30);
        this.personThree = new Person("Carlos", "Paro", 22);
        
        this.personRepository.save(this.personOne);
        this.personRepository.save(this.personTwo);
        this.personRepository.save(this.personThree);
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