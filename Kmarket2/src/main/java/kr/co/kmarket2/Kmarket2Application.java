package kr.co.kmarket2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class Kmarket2Application {

	public static void main(String[] args) {
		SpringApplication.run(Kmarket2Application.class, args);
	}

}
