package br.com.devmedia.course.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.devmedia.course.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
    // Search for addresses starting with the letters in given parameter.
    List<Address> findByCityStartingWith(String city);
    
    // Search for addresses ending with the letters in given parameter.
    List<Address> findByStreetEndingWith(String street);
    
    // Search for addresses containing the letters in the given parameter.
    List<Address> findByStreetContaining(String street);
    
    // Search for addresses starting with the letters of the city or ending with the letters of street.
    List<Address> findByCityStartingWithOrStreetEndingWith(String city, String street);
    
    // Search addresses by city in desc order.
    List<Address> findByCityOrderByTypeDesc(String city);
    
    // Using @NamedQuery from entity.
    List<Address> findByCity(String city);
    
    // Using @NamedNativeQuery from entity.
    Address findByFullAddress(String city, String street);
    
    // Search by city and street using the annotation @Query on the meyhod interface.
    @Query(value = "select * from address where city = ?1 and street = ?2", nativeQuery = true)
    Address findByCityAndStreet(String city, String street);
    
    // Calling a function using @NamedNativeQuery from entity.
    String findConcatAddress(Long id);
    
    @Query(value = "select funcConcatAddress(?1)", nativeQuery = true)
    String findConcatAddressUsingNativeQuery(Long id);
}