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
        this.addressOne = new Address("São Paulo", "Rua Ubatuba", TypeAddress.RESIDENTIAL);
        this.addressTwo = new Address("Salvador", "Rua Bertioga", TypeAddress.COMMERCIAL);
        this.addressThree = new Address("Rio de Janeiro", "Rua Santos", TypeAddress.RESIDENTIAL);
        
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
    
    @Test
    public void shouldFindAddressesByCityStartingWith() {
        List<Address> addresses = this.addressRepository.findByCityStartingWith("Sal");
        Assert.assertTrue(!addresses.isEmpty());
        for (Address address : addresses) {
            Assert.assertTrue(address.getCity().startsWith("Sal"));
        }
    }
    
    @Test
    public void shouldFindAddressesByStreetEndingWith() {
        List<Address> addresses = this.addressRepository.findByStreetEndingWith("Ubatuba");
        Assert.assertTrue(!addresses.isEmpty());
        for (Address address : addresses) {
            Assert.assertTrue(address.getStreet().endsWith("Ubatuba"));
        }
    }
    
    @Test
    public void shouldFindAddressesByStreetContaining() {
        List<Address> addresses = this.addressRepository.findByStreetContaining("San");
        Assert.assertTrue(!addresses.isEmpty());
        for (Address address : addresses) {
            Assert.assertTrue(address.getStreet().contains("San"));
        }
    }
    
    @Test
    public void shouldFindAddressesByCityStartingWithOrStreetEndingWith() {
        List<Address> addresses = this.addressRepository.findByCityStartingWithOrStreetEndingWith("Sal", "San");
        Assert.assertTrue(!addresses.isEmpty());
        for (Address address : addresses) {
            Assert.assertTrue(address.getCity().startsWith("Sal") || address.getStreet().endsWith("San"));
        }
    }
    
    @Test
    public void shouldFindAddressesByCityOrderByTypeDesc() {
        this.addressRepository.save(new Address("Rio de Janeiro", "Rua Santos", TypeAddress.COMMERCIAL));
        List<Address> addresses = this.addressRepository.findByCityOrderByTypeDesc("Rio de Janeiro");
        Assert.assertTrue(!addresses.isEmpty());
        Assert.assertEquals(2, addresses.size());
    }
    
    @Test
    public void shouldFindAddressesByCity() {
        List<Address> addresses = this.addressRepository.findByCity("Salvador");
        Assert.assertTrue(!addresses.isEmpty());
        for (Address address : addresses) {
            Assert.assertEquals("Salvador", address.getCity());
        }
    }
    
    @Test
    public void shouldFindByFullAddress() {
        Address address = this.addressRepository.findByFullAddress("São Paulo", "Rua Ubatuba");
        Assert.assertNotNull(address);
        Assert.assertEquals("São Paulo", address.getCity());
        Assert.assertEquals("Rua Ubatuba", address.getStreet());
    }
    
    @Test
    public void shouldFindAddressByCityAndStreet() {
        Address address = this.addressRepository.findByCityAndStreet("São Paulo", "Rua Ubatuba");
        Assert.assertNotNull(address);
        Assert.assertEquals("São Paulo", address.getCity());
        Assert.assertEquals("Rua Ubatuba", address.getStreet());
    }
    
    @Test
    public void shouldFindConcatAddress() {
        String addressConcat = this.addressRepository.findConcatAddress(this.addressOne.getId());
        Assert.assertNotNull(addressConcat);
        Assert.assertTrue(!addressConcat.isEmpty());
        Assert.assertEquals("São Paulo - Rua Ubatuba", addressConcat);
    }
    
    @Test
    public void shouldFindConcatAddressUsingNativeQuery() {
        String addressConcat = this.addressRepository.findConcatAddressUsingNativeQuery(this.addressOne.getId());
        Assert.assertNotNull(addressConcat);
        Assert.assertTrue(!addressConcat.isEmpty());
        Assert.assertEquals("São Paulo - Rua Ubatuba", addressConcat);
    }
}