package com.springboottesttask;

import com.google.common.collect.ImmutableList;

import com.springboottesttask.service.CsvSuggestionConverter;
import com.springboottesttask.service.CsvSuggestionWriter;
import com.springboottesttask.service.GoEuroApiClient;

import com.sun.javaws.exceptions.ExitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.Arrays;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;


@SpringBootApplication
public class SpringboottesttaskApplication implements CommandLineRunner {

	@Autowired
	private CsvSuggestionWriter csvSuggestionWriter;

	@Autowired
	private GoEuroApiClient goEuroApiClient;

	@Autowired
	CsvSuggestionConverter csvSuggestionConverter;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate(new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build()));
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(SpringboottesttaskApplication.class)
				.bannerMode(Banner.Mode.OFF)
				.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (args.length != 1) {
			showHelpScreen();
			throw new Exception(String.format("Program arguments were not correct: %s", Arrays.asList(args)));
		}

		try {
			final String cityName = args[0].trim();
			final String filename = cityName + ".csv";

			csvSuggestionWriter.write(
					filename,
					goEuroApiClient.findSuggestionsByCity(cityName).stream()
							.map(csvSuggestionConverter::toCsvSuggestionDto)
							.collect(collectingAndThen(toList(), ImmutableList::copyOf)));

			System.out.println(String.format("Suggestions were sucessefully saved into %s", filename));

		} catch (RuntimeException e) {
			System.err.println("Error occured while performing your request. Check log for details");
		}
	}

	private void showHelpScreen() {
		System.out.println("Command line syntax is wrong! Please provide correct parameters!");
		System.out.println("Syntax: java -jar GoEuroTest.jar \"CITY_NAME\"");
		System.out.println("Example: java -jar GoEuroTest.jar berlin");
	}
}
