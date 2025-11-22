package com.woowaprecourse.minjun_baseball_game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MinjunBaseballGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinjunBaseballGameApplication.class, args);
	}

}
