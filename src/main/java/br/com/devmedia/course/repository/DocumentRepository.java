package br.com.devmedia.course.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.devmedia.course.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("select d from Document d where d.cpf like :start%")
    public List<Document> findByCPFStartWith(@Param("start") String start);
}