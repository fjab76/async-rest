package fjab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AsyncRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsyncRestApplication.class, args);
	}
}
