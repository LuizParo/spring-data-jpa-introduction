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
import br.com.devmedia.course.entity.Phone;
import br.com.devmedia.course.entity.Phone.TypePhone;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CourseSpringDataApplication.class)
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
    
    @Test
    public void shouldSetPhoneNumber() {
        int returnIdentifier = this.phoneRepository.setPhoneNumber("55 5555-5555", this.phoneOne.getId());
        Assert.assertEquals(1, returnIdentifier); // Success
    }
    
    @Test
    public void shouldSetPhoneType() {
        int returnIdentifier = this.phoneRepository.setPhoneType(TypePhone.COMMERCIAL, this.phoneOne.getId());
        Assert.assertEquals(1, returnIdentifier); // Success
    }
    
    @Test
    public void shouldRemovePhoneByNumber() {
        int returnIdentifier = this.phoneRepository.removePhoneByNumber(this.phoneOne.getNumber());
        Assert.assertEquals(1, returnIdentifier); // Success
    }
}