package com.java.jpa.springbootjparelaciones.repositories;

import com.java.jpa.springbootjparelaciones.entities.Course;
import com.java.jpa.springbootjparelaciones.entities.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CourseRepository extends CrudRepository<Course, Long> {

    @Query("select c from Course c left join fetch c.students where c.Id=?1")
    Optional<Course> findOneWithStudents(Long id);
}
