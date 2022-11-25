package com.etrade.segment.service;

import com.etrade.segment.core.config.hazelcast.HazelcastClient;
import com.etrade.core.result.*;
import com.etrade.segment.dto.SegmentCache;
import com.etrade.segment.dto.SegmentCreateRequest;
import com.etrade.segment.dto.SegmentListResponse;
import com.etrade.core.model.Segment;
import com.etrade.segment.repository.SegmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SegmentServiceImpl implements SegmentService{
    private final SegmentRepository segmentRepository;
    private final HazelcastClient hazelcastClient;

    public SegmentServiceImpl(SegmentRepository segmentRepository, HazelcastClient hazelcastClient) {
        this.segmentRepository = segmentRepository;
        this.hazelcastClient = hazelcastClient;
    }

    @Override
    public Result createSegment(SegmentCreateRequest segmentCreateRequest) {
        Segment segment = segmentRepository.createSegment(segmentCreateRequest);

        Segment createdSegment = segmentRepository.save(segment);

        SegmentCache segmentCache = SegmentCache.builder()
                .segmentId(createdSegment.getId())
                .userIds(createdSegment.getUserIds())
                .build();
        hazelcastClient.put(segmentCache.getSegmentId(), segmentCache);
        log.info("SegmentServiceImpl :: createSegment :: Segment Cached and created");
        return new SuccessResult("Segment created.");
    }

    @Override
    public void cacheSegments() {
        List<Segment> segmentList = segmentRepository.findAll();
        segmentList.stream().forEach(segment -> {
            SegmentCache segmentCache = SegmentCache
                    .builder()
                    .segmentId(segment.getId())
                    .userIds(segment.getUserIds())
                    .build();
            hazelcastClient.put(segment.getId(), segmentCache);
            log.info("SegmentServiceImpl :: cacheSegments :: Segments Cached");
        });
    }

    @Override
    public void updateSegments() {
        List<Segment> segments = segmentRepository.findAll();
        segmentRepository.updateSegments(segments);
        cacheSegments();
        log.info("SegmentServiceImpl :: updateSegments :: Segment has updated");
    }

    @Override
    public Result deleteSegment(String id) {
        Optional<Segment> segment = segmentRepository.findById(id);
        if(!segment.isPresent()){
            return new ErrorResult("There is no such a segment with that id. Please try again later");
        }
        hazelcastClient.delete(id);
        segmentRepository.delete(segment.get());
        log.info("SegmentServiceImpl :: deleteSegment :: Segment has deleted");

        return new SuccessResult("Segment has deleted.");
    }

    @Override
    public DataResult<List<SegmentListResponse>> listSegments() {
        List<Segment> segments = segmentRepository.findAll();
        return new SuccessDataResult<>(castToListDto(segments));
    }

    private List<SegmentListResponse> castToListDto(List<Segment> segments){
        return segments.stream().map(segment -> {
            return SegmentListResponse.builder()
                    .id(segment.getId())
                    .name(segment.getName())
                    .userCount(segment.getUserIds().size())
                    .createDate(segment.getCreateDate())
                    .build();
        }).collect(Collectors.toList());
    }
}
