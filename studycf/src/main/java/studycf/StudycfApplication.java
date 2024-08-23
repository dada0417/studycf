package studycf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class StudycfApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudycfApplication.class, args);
	}

}
