package br.com.devmedia.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.devmedia.course.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
    
}