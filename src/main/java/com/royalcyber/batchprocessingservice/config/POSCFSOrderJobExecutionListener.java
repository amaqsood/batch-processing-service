package com.royalcyber.batchprocessingservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class POSCFSOrderJobExecutionListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        JobExecutionListener.super.beforeJob(jobExecution);
        log.info("JOB Started....! - POSCFSOrderJobExecutionListener");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        JobExecutionListener.super.afterJob(jobExecution);
        if (jobExecution.getStatus().equals(BatchStatus.COMPLETED)) {
            log.info("JOB COMPLETED....! - POSCFSOrderJobExecutionListener");
        } else {
            if (jobExecution.isRunning()) {
                log.info("JOB is running....! - POSCFSOrderJobExecutionListener");
            }
        }
    }
}
