package br.com.devmedia.course.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

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
}