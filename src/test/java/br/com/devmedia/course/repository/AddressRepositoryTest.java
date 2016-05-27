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
import br.com.devmedia.course.entity.Address;
import br.com.devmedia.course.entity.Address.TypeAddress;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CourseSpringDataApplication.class)
public class AddressRepositoryTest {
    private Address addressOne;
    private Address addressTwo;
    private Address addressThree;
    
    @Autowired
    private AddressRepository addressRepository;
    
    @Before
    public void setUp() {
        this.addressOne = new Address("São Paulo", "Test Street", TypeAddress.RESIDENTIAL);
        this.addressTwo = new Address("Salvador", "Test Street", TypeAddress.COMMERCIAL);
        this.addressThree = new Address("Rio de Janeiro", "Test Street", TypeAddress.RESIDENTIAL);
        
        this.addressRepository.save(this.addressOne);
        this.addressRepository.save(this.addressTwo);
        this.addressRepository.save(this.addressThree);
    }
    
    @Test
    public void shouldCountAddressesOnDatabase() {
        Assert.assertEquals(3L, this.addressRepository.count());
    }
    
    @Test
    public void shouldFindAllAddresses() {
        List<Address> allAddresses = this.addressRepository.findAll();
        Assert.assertFalse(allAddresses.isEmpty());
        Assert.assertEquals(3, allAddresses.size());
        for (Address address : allAddresses) {
            Assert.assertNotNull(address.getId());
        }
    }
}