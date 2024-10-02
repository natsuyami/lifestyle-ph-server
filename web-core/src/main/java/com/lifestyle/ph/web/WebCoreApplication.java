package com.lifestyle.ph.web;

import com.google.cloud.spring.data.firestore.repository.config.EnableReactiveFirestoreRepositories;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.lifestyle.ph.*"})
@EnableEncryptableProperties
@EnableReactiveFirestoreRepositories(basePackages = {"com.lifestyle.ph.*"})
public class WebCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebCoreApplication.class, args);
	}

}
