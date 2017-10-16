package com.springboottesttask;

import com.google.common.collect.ImmutableList;
import com.springboottesttask.service.CsvSuggestionWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;


@SpringBootApplication
public class SpringboottesttaskApplication implements CommandLineRunner {

	@Autowired
	private CsvSuggestionWriter csvSuggestionWriter;

	public static void main(String[] args) {
		SpringApplication.run(SpringboottesttaskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String cityName = args[0].trim();
		String fileName = cityName + ".csv";

		csvSuggestionWriter.write(fileName, goEuroApiClient.findSuggestionsByCity().stream
				.map(csvSuggestionConverter::toCsvSuggestionDto)
				.collect(collectingAndThen(toList(), ImmutableList::copyOf)));
	}
}
