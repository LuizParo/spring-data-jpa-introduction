package br.com.devmedia.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ImportResource(value = "spring-data.xml")
public class CourseSpringDataApplication {
	public static void main(String[] args) {
		SpringApplication.run(CourseSpringDataApplication.class, args);
	}
}