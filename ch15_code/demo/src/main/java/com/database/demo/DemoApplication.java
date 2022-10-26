package com.database.demo;

import com.microsoft.applicationinsights.TelemetryClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	static final TelemetryClient telemetryClient = new TelemetryClient();

	private final StudentRepository studentRepository;

	public DemoApplication(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@GetMapping("/")
	@ResponseBody
	@PreAuthorize("hasAuthority('APPROLE_superuser')")

	public String getStudents() {
		Iterable<Student> students = studentRepository.findAll();
		String html = "<html><body>" ;
		try{
			for (Student student: students) {
				html = html+"<p>";
				html = html+"<b>Roll : </b>" + student.getRoll()+"<br/>";
				html = html+"<b>Name : </b>" + student.getName()+"<br/>";
				html = html+"<b>Age : </b>" + student.getAge()+"<br/>";
				html = html+"<b>Marks : </b>" + student.getMarks()+"<br/>";

				telemetryClient.trackTrace("Processing Success with Roll : "+student.getRoll());
			}
			html = html+"</body></html>";
		}
		catch(Exception ex){
			telemetryClient.trackException(ex);
		}

		return html;
	}

}
