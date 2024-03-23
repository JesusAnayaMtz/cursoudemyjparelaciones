package com.java.jpa.springbootjparelaciones.repositories;

import com.java.jpa.springbootjparelaciones.entities.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {
}
