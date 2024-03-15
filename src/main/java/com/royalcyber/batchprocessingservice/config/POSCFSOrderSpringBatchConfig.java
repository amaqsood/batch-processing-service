package com.royalcyber.batchprocessingservice.config;

import com.royalcyber.batchprocessingservice.dto.POSCFSOrderDTO;
import com.royalcyber.batchprocessingservice.entity.POSCFSOrder;
import com.royalcyber.batchprocessingservice.repository.POSCFSOrderDAORepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class POSCFSOrderSpringBatchConfig {

    private POSCFSOrderDAORepository poscfsOrderDAORepository;

    @Bean
    public ItemReader<POSCFSOrderDTO> itemReader() {

        FlatFileItemReader<POSCFSOrderDTO> itemReader = new FlatFileItemReader<>();
        itemReader.setStrict(false);
        itemReader.setResource(new PathResource("C:\\Users\\amerm\\Downloads\\pos_cfs_order.csv"));
        itemReader.setName("POSCFSOrderBatchConfig-itemReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<POSCFSOrderDTO> lineMapper() {
        DefaultLineMapper<POSCFSOrderDTO> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("posOrderId", "cfsOrderSummaryId", "cfsOrderNumber");
        BeanWrapperFieldSetMapper<POSCFSOrderDTO> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(POSCFSOrderDTO.class);
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
        return defaultLineMapper;
    }

    @Bean
    public ItemProcessor<POSCFSOrderDTO, POSCFSOrder> itemProcessor() {
        return new POSCFSOrderSpringBatchItemProcessor();
    }

    @Bean
    public ItemWriter<POSCFSOrder> itemWriter() {
        RepositoryItemWriter<POSCFSOrder> repositoryItemWriter = new RepositoryItemWriter<>();
        repositoryItemWriter.setRepository(poscfsOrderDAORepository);
        repositoryItemWriter.setMethodName("save");
        return repositoryItemWriter;
    }


    @Bean
    protected Step orderStepA(JobRepository jobRepository,
                              PlatformTransactionManager transactionManager,
                              ItemReader<POSCFSOrderDTO> itemReader,
                              ItemProcessor<POSCFSOrderDTO, POSCFSOrder> itemProcessor,
                              ItemWriter<POSCFSOrder> itemWriter) {
        return new StepBuilder("orderStepA", jobRepository)
                .<POSCFSOrderDTO, POSCFSOrder>chunk(10000, transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .taskExecutor(taskExecutor())
                .build();
    }


    @Bean(name = "orderBatchJob")
    public Job orderJob(JobRepository jobRepository, Step orderStepA) {
        return new JobBuilder("orderBatchJob", jobRepository)
                .preventRestart()
                .start(orderStepA)
                .listener(jobExecutionerListener())
                .build();
    }

    @Bean
    public JobExecutionListener jobExecutionerListener() {
        return new POSCFSOrderJobExecutionListener();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }


}
