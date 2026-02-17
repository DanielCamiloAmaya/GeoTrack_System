package com.PPOOII.Proyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.PPOOII.Proyecto.Config.MailgunProperties;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(MailgunProperties.class)
public class ProyectoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoApplication.class, args);
	}

}
