package com.etrade.advice.controller;

import com.etrade.advice.core.result.DataResult;
import com.etrade.advice.core.result.Result;
import com.etrade.advice.dto.AdviceListCreateRequest;
import com.etrade.advice.dto.AdviceListResponse;
import com.etrade.advice.service.AdviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/advice")
public class AdviceController {
    private final AdviceService adviceService;

    public AdviceController(AdviceService adviceService) {
        this.adviceService = adviceService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAdviceList(@RequestBody AdviceListCreateRequest adviceListCreateRequest){
        Result result = adviceService.createAdviceList(adviceListCreateRequest);
        return  result.isSuccess() ? ResponseEntity.ok(result.getMessage()) : ResponseEntity.badRequest().body(result.getMessage());
    }

    @GetMapping("/list-admin")
    public ResponseEntity<List<AdviceListResponse>> listAdviceLists(){
        DataResult<List<AdviceListResponse>> result = adviceService.listAdviceLists();
        return  result.isSuccess() ? ResponseEntity.ok(result.getData()) : ResponseEntity.badRequest().body(result.getData());
    }

    @GetMapping("/list")
    public ResponseEntity<AdviceListResponse> listAdvices(HttpServletRequest request){
        DataResult<AdviceListResponse> result = adviceService.listAdvices(request);
        return  result.isSuccess() ? ResponseEntity.ok(result.getData()) : ResponseEntity.badRequest().body(result.getData());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAdviceList(@PathVariable String id){
        Result result = adviceService.deleteAdviceList(id);
        return  result.isSuccess() ? ResponseEntity.ok(result.getMessage()) : ResponseEntity.badRequest().body(result.getMessage());
    }
}
