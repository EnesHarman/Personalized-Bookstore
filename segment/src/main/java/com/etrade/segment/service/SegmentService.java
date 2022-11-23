package com.etrade.segment.service;

import com.etrade.core.result.*;

import com.etrade.segment.dto.SegmentCreateRequest;
import com.etrade.segment.dto.SegmentListResponse;

import java.util.List;

public interface SegmentService {
    Result createSegment(SegmentCreateRequest segmentCreateRequest);

    void cacheSegments();

    void updateSegments();

    Result deleteSegment(String id);

    DataResult<List<SegmentListResponse>> listSegments();
}
