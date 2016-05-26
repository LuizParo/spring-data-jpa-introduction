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
import br.com.devmedia.course.entity.Phone;
import br.com.devmedia.course.entity.Phone.TypePhone;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-data.xml" })
public class PhoneRepositoryTest {
    private Phone phoneOne;
    private Phone phoneTwo;
    private Phone phoneThree;
    
    @Autowired
    private PhoneRepository phoneRepository;
    
    @Before
    public void setUp() {
        this.phoneOne = new Phone(TypePhone.RESIDENTIAL, "58 3632-8574", new Person("Luiz", "Paro", 24));
        this.phoneTwo = new Phone(TypePhone.COMMERCIAL, "58 4832-8574", new Person("João", "Silva", 30));
        this.phoneThree = new Phone(TypePhone.CELLPHONE, "58 99632-8574", new Person("Carlos", "Paro", 22));
        
        this.phoneRepository.save(this.phoneOne);
        this.phoneRepository.save(this.phoneTwo);
        this.phoneRepository.save(this.phoneThree);
    }
    
    @After
    public void tearDown() {
        this.phoneRepository.delete(this.phoneOne.getId());
        this.phoneRepository.delete(this.phoneTwo.getId());
        this.phoneRepository.delete(this.phoneThree.getId());
    }
    
    @Test
    public void shouldCountPhonesOnDatabase() {
        Assert.assertEquals(3L, this.phoneRepository.count());
    }

    @Test
    public void shouldFindAllDocuments() {
        List<Phone> allPhones = this.phoneRepository.findAll();
        Assert.assertFalse(allPhones.isEmpty());
        Assert.assertEquals(3, allPhones.size());
    }
}