package com.yash.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.yash.model.User;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
	
	@Bean
	public Job job(JobBuilderFactory jobFactory, StepBuilderFactory stepFactory, ItemReader<User> itemReader, 
			ItemProcessor<User, User> processor, ItemWriter<User> writer ) {
		
		Step step = stepFactory.get("ETL_File_Load").
				<User, User>chunk(100).reader(itemReader).processor(processor).writer(writer).build();	
		
		Job job = jobFactory.get("ETL_Load").incrementer(new RunIdIncrementer()).start(step).build();
		return job;		
	}
	
	@Bean
	public FlatFileItemReader<User> itemReader(@Value("${input}") Resource resource){
		FlatFileItemReader<User> itemReader = new FlatFileItemReader<>();
		itemReader.setResource(resource);
		itemReader.setName("CSV-reader");
		itemReader.setLinesToSkip(1);
		itemReader.setLineMapper(lineMapper());
		return itemReader;
	}
	
	@Bean
	public LineMapper<User> lineMapper(){
		DefaultLineMapper<User> lineMapper = new DefaultLineMapper<>();
		
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(new String[] {"name", "rollNo", "department", "result"} );
		
		BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(User.class);	
		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		
		return lineMapper;
	}
	

}
