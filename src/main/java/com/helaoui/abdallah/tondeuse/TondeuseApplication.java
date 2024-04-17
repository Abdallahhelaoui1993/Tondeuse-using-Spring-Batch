package com.helaoui.abdallah.tondeuse;

import com.helaoui.abdallah.tondeuse.model.Tondeuse;
import com.helaoui.abdallah.tondeuse.util.InputFileReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.List;

@SpringBootApplication
@EnableAutoConfiguration
@EnableBatchProcessing

public class TondeuseApplication {

	public static void main(String[] args) {
		SpringApplication.run(TondeuseApplication.class, args);
	}

	@Configuration
	public static class BatchConfig {

		@Autowired
		private JobLauncher jobLauncher;

		@Autowired
		private Job tondeuseJob;
		@Bean
		public CommandLineRunner run(JobLauncher jobLauncher, Job tondeuseJob) {
			return args -> {
				if (args.length < 1) {
					throw new IllegalArgumentException("Merci de fournir le chemain pur le fichier d'entrée");
				}
				String inputFile = args[0];
				String outputFile = "output.txt";

				InputFileReader.readInputFile(inputFile);


				JobParameters jobParameters = new JobParametersBuilder()
						.addString("inputFile", inputFile)
						.addString("outputFile", outputFile)
						.addLong("time", System.currentTimeMillis())
						.toJobParameters();

				jobLauncher.run(tondeuseJob, jobParameters);
			};
		}


	}
}
