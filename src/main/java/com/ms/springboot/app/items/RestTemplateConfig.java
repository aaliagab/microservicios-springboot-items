package com.ms.springboot.app.items;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	@Bean("clienteRest")
	public RestTemplate registrarRestTemplateEnContenedor() {
		return new RestTemplate();
	}
}
