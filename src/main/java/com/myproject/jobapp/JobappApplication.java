package com.myproject.jobapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JobappApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobappApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//		return runner ->{
//			UserRepository userRepository = ctx.getBean(UserRepository.class);
//			UserTypeRepository userTypeRepository = ctx.getBean(UserTypeRepository.class);
//
//			UserType ut = userTypeRepository.findUserTypeById(1);
//			System.out.println(ut);
//
//			Users user = new Users();
//			user.setEmail("1223@Gmail.com");
//			user.setPassword("123");
//			user.setUserTypeId(ut);
//
//			userRepository.save(user);
//		};
//	}

}
