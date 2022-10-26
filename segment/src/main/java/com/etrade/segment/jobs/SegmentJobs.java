package com.etrade.segment.jobs;

import com.etrade.segment.service.SegmentService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SegmentJobs {
    private final SegmentService segmentService;

    public SegmentJobs(SegmentService segmentService) {
        this.segmentService = segmentService;
    }

    @Scheduled(cron = "0 0 2 * * *") //Every Day at 2:00 am
    public void updateSegments(){
        this.segmentService.updateSegments();
    }
}
