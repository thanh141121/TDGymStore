package net.gymsrote.service.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import net.gymsrote.service.thirdparty.ghn.GHNService.ServiceIDRespones;

@Component
public class HttpRequest {
	private RestTemplate restTemplate = null; 
	
	public HttpRequest() { 
		restTemplate = new RestTemplateBuilder()
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
		restTemplate.setMessageConverters(Arrays.asList(new StringHttpMessageConverter(), new MappingJackson2HttpMessageConverter()));
	}
	
	public String postJson(String url, Object payload) {
		return restTemplate.postForEntity(url, payload, String.class).getBody();
	}
	
	public <T> ResponseEntity<T> postWithClassResponse(String url, HttpEntity<?> requestEntity, Class<T> src){
		return restTemplate.postForEntity(url, requestEntity, src);
		
	}
}
