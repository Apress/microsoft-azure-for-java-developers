package com.database.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	private final StudentRepository studentRepository;

	public DemoApplication(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public Student createStudents(@RequestBody Student student) {
		return studentRepository.save(student);
	}

	@GetMapping("/")
	public Iterable<Student> getStudents() {
		return studentRepository.findAll();
	}

}
