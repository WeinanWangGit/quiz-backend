package com.system.quiz;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuizApplication {


	public static void main(String[] args) {
		nu.pattern.OpenCV.loadLocally();

		SpringApplication.run(QuizApplication.class, args);
	}

}
