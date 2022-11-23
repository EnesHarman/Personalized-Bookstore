package com.etrade.segment.controller;

import com.etrade.core.result.*;
import com.etrade.segment.dto.SegmentCreateRequest;
import com.etrade.segment.dto.SegmentListResponse;
import com.etrade.segment.service.SegmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/segment")
public class SegmentController {
    private final SegmentService segmentService;

    public SegmentController(SegmentService segmentService) {
        this.segmentService = segmentService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createSegment(@RequestBody SegmentCreateRequest segmentCreateRequest){
        Result result = segmentService.createSegment(segmentCreateRequest);
        return  result.isSuccess() ? ResponseEntity.ok(result.getMessage()) : ResponseEntity.badRequest().body(result.getMessage());
    }

    @GetMapping("/list")
    public ResponseEntity<List<SegmentListResponse>> listSegments(){
        DataResult<List<SegmentListResponse>> result = segmentService.listSegments();
        return  result.isSuccess() ? ResponseEntity.ok(result.getData()) : ResponseEntity.badRequest().body(result.getData());
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> deleteSegment(@PathVariable String id){
        Result result = segmentService.deleteSegment(id);
        return  result.isSuccess() ? ResponseEntity.ok(result.getMessage()) : ResponseEntity.badRequest().body(result.getMessage());
    }
}

