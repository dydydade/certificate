package email.certificate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class CertificateApplication {

	public static void main(String[] args) {
		SpringApplication.run(CertificateApplication.class, args);
	}

}
