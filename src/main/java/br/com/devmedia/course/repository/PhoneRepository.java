package br.com.devmedia.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.devmedia.course.entity.Phone;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    
}