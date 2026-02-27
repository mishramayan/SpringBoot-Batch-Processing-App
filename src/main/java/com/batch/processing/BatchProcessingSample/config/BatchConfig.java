package com.batch.processing.BatchProcessingSample.config;

import com.batch.processing.BatchProcessingSample.model.Product;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.FlatFileItemWriter;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfig {

    @Bean
    public Job jobBean(JobRepository jobRepository, JobCompletionNotificationImpl listener, Step steps){
        return new JobBuilder("job",jobRepository)
                .listener(listener)
                .start(steps)
                .build();
    }

    @Bean
    public Step steps(
            JobRepository jobRepository,
            DataSourceTransactionManager manager,
            ItemReader<Product> reader,
            ItemProcessor<Product, Product> processor,
            ItemWriter<Product> writer
    ){

        return new StepBuilder("jobStep",jobRepository)
                .<Product, Product>chunk(500)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();

    }

    // reader
    @Bean
    public FlatFileItemReader<Product> reader(){
        return new FlatFileItemReaderBuilder<Product>()
                .name("itemReader")
                .resource(new ClassPathResource("data.csv"))
                .linesToSkip(1)
                .delimited()
                .names("productId", "title", "description", "price", "discount")
                .targetType(Product.class)
                .build();
    }

    // processor
    @Bean
    public ItemProcessor<Product, Product> processor(){
        return new CustomItemProcessor();
    }

    // writer
    @Bean
    public ItemWriter<Product> writer(DataSource dataSource){
        return new JdbcBatchItemWriterBuilder<Product>()
                .sql("insert into products(product_id, title, description, price, discount, discounted_price) " +
                        "values(:product_id, :title, :description, :price, :discount, :discounted_price)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }
}
