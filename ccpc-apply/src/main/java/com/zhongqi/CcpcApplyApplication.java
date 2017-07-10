package com.zhongqi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
@EnableEurekaClient
@EnableSwagger2
@EnableFeignClients
public class CcpcApplyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CcpcApplyApplication.class, args);
	}
}
