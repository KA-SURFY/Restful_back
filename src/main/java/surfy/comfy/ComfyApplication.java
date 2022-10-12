package surfy.comfy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync            // Async 활성화
public class ComfyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComfyApplication.class, args);
	}

}
