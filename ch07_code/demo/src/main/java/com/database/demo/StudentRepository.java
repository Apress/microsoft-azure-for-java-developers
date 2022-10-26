package com.database.demo;

import org.springframework.data.repository.CrudRepository;
public interface StudentRepository extends CrudRepository<Student, Integer> {
}
