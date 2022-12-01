package com.etrade.advice.service;

import com.etrade.core.result.*;
import com.etrade.advice.dto.AdviceListCreateRequest;
import com.etrade.advice.dto.AdviceListResponse;
import com.etrade.core.model.Advice;
import com.etrade.advice.repository.AdviceRepository;
import org.springframework.dao.DuplicateKeyException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdviceServiceImpl implements AdviceService{
    private final AdviceRepository adviceRepository;

    public AdviceServiceImpl(AdviceRepository adviceRepository) {
        this.adviceRepository = adviceRepository;
    }

    @Override
    public Result createAdviceList(AdviceListCreateRequest adviceListCreateRequest) {
        Advice advice = Advice.builder()
                .name(adviceListCreateRequest.getName())
                .segmentId(adviceListCreateRequest.getSegmentId())
                .createDate(LocalDateTime.now())
                .productIds(adviceListCreateRequest.getProductIds())
                .build();
        try {
            adviceRepository.save(advice);
        }catch (DuplicateKeyException duplicateKeyException){
            return new ErrorDataResult<>("There is already a list with that name. Please change the name.");
        }
        log.info("AdviceServiceImpl :: createAdviceList :: Advice list has created.");
        return new SuccessResult("Advice list has created.");
    }

    @Override
    public DataResult<List<AdviceListResponse>> listAdviceLists() {
        List<Advice> advice = adviceRepository.findAll();
        List<AdviceListResponse> result = advice.stream().map(adv -> adviceRepository.getAdvicesInfo(adv)).collect(Collectors.toList());
        return new SuccessDataResult<>(result);
    }

    @Override
    public DataResult<AdviceListResponse> listAdvices(HttpServletRequest request) {
        Advice advice;
        Optional<String> userEmail = getUserEmailFromRequest(request);
        if(!userEmail.isPresent()) {
            advice = adviceRepository.getDefaultAdvice();
            return new SuccessDataResult<>(adviceRepository.getAdvicesInfo(advice));
        }
        advice = adviceRepository.getUserAdviceList(userEmail.get());
        return new SuccessDataResult<>(adviceRepository.getAdvicesInfo(advice)); //TODO NICE SHIT CODE
    }

    @Override
    public Result deleteAdviceList(String id) {
        adviceRepository.deleteById(id);
        return new SuccessResult("The advice has deleted.");
    }

    private Optional<String> getUserEmailFromRequest(HttpServletRequest request){
        Base64.Decoder decoder = Base64.getUrlDecoder();
        if(request.getHeader("Authorization") == null){
            return Optional.empty();
        }
        String token = request.getHeader("Authorization").split("Bearer ")[1];
        String payload = new String(decoder.decode(token.split("\\.")[1]));
        JSONObject obj = new JSONObject(payload);
        return Optional.of(obj.getString("email"));
    }
}
