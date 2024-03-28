package com.kuroko.heathyapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kuroko.heathyapi.feature.account.service.AccountService;
import com.kuroko.heathyapi.feature.user.service.UserService;

@SpringBootApplication
public class HeathyapiApplication {
	@Autowired
	AccountService accountService;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(HeathyapiApplication.class, args);
	}

	// public int randomAge() {
	// int n = (int) (Math.random() * 100) + 15;
	// return n;
	// }

	// public int randomHeight() {
	// int n = (int) (Math.random() * 70) + 140;
	// return n;
	// }

	// public int randomWeight() {
	// int n = (int) (Math.random() * 60) + 40;
	// return n;
	// }

	// public double randomCoff() {
	// int n = (int) (Math.random() * 5);
	// double[] coff = { 1.2, 1.375, 1.55, 1.725, 1.9 };
	// return coff[n];
	// }
}
