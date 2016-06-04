package br.com.devmedia.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.devmedia.course.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}