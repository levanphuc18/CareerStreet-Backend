package com.careerstreet.file_service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import com.cloudinary.Cloudinary;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class FileServiceApplication {
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public Cloudinary getCloudinary(@Value("${spring.cloudinary.cloud_name}") String cloudName,
									@Value("${spring.cloudinary.api_key}") String apiKey,
									@Value("${spring.cloudinary.api_secret}") String apiSecret) {

		Map<String, Object> config = new HashMap<>();
		config.put("cloud_name", cloudName);
		config.put("api_key", apiKey);
		config.put("api_secret", apiSecret);
		config.put("secure", true);
		System.out.println(cloudName + "name cloud");
		return new Cloudinary(config);
	}

	public static void main(String[] args) {
		SpringApplication.run(FileServiceApplication.class, args);
	}
}
