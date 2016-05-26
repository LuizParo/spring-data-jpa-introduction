package br.com.devmedia.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.devmedia.course.entity.Person;

public interface PersonRepository extends JpaRepository<Person , Long> {

}