package com.springboottesttask;

import com.google.common.collect.ImmutableList;

import com.springboottesttask.service.CsvSuggestionWriter;
import com.springboottesttask.service.GoEuroApiClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.apache.http.impl.client.HttpClientBuilder;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;


@SpringBootApplication
public class SpringboottesttaskApplication implements CommandLineRunner {

	@Autowired
	private CsvSuggestionWriter csvSuggestionWriter;

	@Autowired
	private GoEuroApiClient goEuroApiClient;



	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate(new HttpComponentsAsyncClientHttpRequestFactory(HttpClientBuilder.create().build));
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringboottesttaskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String cityName = args[0].trim();
		String fileName = cityName + ".csv";

		csvSuggestionWriter.write(fileName, goEuroApiClient.findSuggestionsByCity(cityName).stream()
				.map(csvSuggestionConverter::toCsvSuggestionDto)
				.collect(collectingAndThen(toList(), ImmutableList::copyOf)));
	}
}
