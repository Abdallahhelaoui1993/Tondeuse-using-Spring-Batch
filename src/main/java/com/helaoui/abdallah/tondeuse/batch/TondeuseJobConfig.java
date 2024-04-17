package com.helaoui.abdallah.tondeuse.batch;


import com.helaoui.abdallah.tondeuse.model.Tondeuse;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class TondeuseJobConfig  {

    private JobRepository jobRepository;
    private PlatformTransactionManager transactionManager;

    @Bean
    public Job tondeuseJob(JobBuilderFactory jobBuilderFactory,
                           Step processMowersStep) {
        return jobBuilderFactory.get("tondeuseJob")
                .incrementer(new RunIdIncrementer())
                .start(processMowersStep)
                .build();
    }

    @Bean
    public Step processTondeusesStep(StepBuilderFactory stepBuilderFactory,
                                     ItemReader<Tondeuse> tondeuseItemReader,
                                     ItemProcessor<Tondeuse, Tondeuse> tondeuseItemProcessor,
                                     ItemWriter<Tondeuse> tondeuseItemWriter) {
        return stepBuilderFactory.get("processTondeusesStep")
                .<Tondeuse, Tondeuse>chunk(1)
                .reader(tondeuseItemReader)
                .processor(tondeuseItemProcessor)
                .writer(tondeuseItemWriter)
                .build();
    }


    @Bean
    @StepScope
    public FlatFileItemReader<Tondeuse> tondeuseItemReader(@Value("#{jobParameters['inputFile']}") String inputFile) {

        if (inputFile == null) {
            throw new IllegalArgumentException("Input file path must not be null");
        }
        System.out.println("Input file path: " + inputFile);

        return new FlatFileItemReaderBuilder<Tondeuse>()
                .name("tondeuseItemReader")
                .resource(new FileSystemResource(inputFile))
                .linesToSkip(1)
                .lineMapper(lineMapper())
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Tondeuse>() {{
                    setTargetType(Tondeuse.class);
                }})
                .build();
    }

    @Bean
    public ItemProcessor<Tondeuse, Tondeuse> tondeuseItemProcessor() {
        return new TondeuseItemProcessor();
    }


    @Bean
    public FlatFileItemWriter<Tondeuse> mowerItemWriter() {
        return new FlatFileItemWriterBuilder<Tondeuse>()
                .name("tondeuseItemWriter")
                .resource(new FileSystemResource("output.txt"))
                .lineAggregator(new DelimitedLineAggregator<Tondeuse>() {{
                    setDelimiter(" ");
                    setFieldExtractor(tondeuse -> new String[]{String.valueOf(tondeuse.getX()),
                            String.valueOf(tondeuse.getY()), String.valueOf(tondeuse.getOrientation())});
                }})
                .build();
    }

    private LineMapper<Tondeuse> lineMapper() {
        DefaultLineMapper<Tondeuse> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(" ");
        tokenizer.setNames(new String[] {"x", "y", "orientation", "instructions"});

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSet -> {
            Tondeuse tondeuse = new Tondeuse();
            tondeuse.setX(fieldSet.readInt("x"));
            tondeuse.setY(fieldSet.readInt("y"));
            tondeuse.setOrientation(fieldSet.readString("orientation").charAt(0));
            tondeuse.setInstructions(fieldSet.readString("instructions"));
            return tondeuse;
        });

        return lineMapper;
    }

}
