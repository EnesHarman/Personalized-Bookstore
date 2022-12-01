package com.etrade.segment.repository.custom;

import com.etrade.segment.dto.SegmentCreateRequest;
import com.etrade.core.model.Segment;

import java.util.List;

public interface SegmentCustomRepository {
    Segment createSegment(SegmentCreateRequest segmentCreateRequest, long priority);
    void updateSegments(List<Segment> segments);
}
