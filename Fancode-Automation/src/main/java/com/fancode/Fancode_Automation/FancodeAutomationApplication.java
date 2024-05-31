package com.fancode.Fancode_Automation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FancodeAutomationApplication {

	public static void main(String[] args) {
		SpringApplication.run(FancodeAutomationApplication.class, args);
	}
}


