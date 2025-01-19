package com.mindgoal.domain.expert.controller;

import com.mindgoal.common.BaseResponse;
import com.mindgoal.domain.expert.dto.ExpertCreateRequest;
import com.mindgoal.domain.expert.dto.ExpertListResponse;
import com.mindgoal.domain.expert.dto.ExpertResponse;
import com.mindgoal.domain.expert.dto.ExpertSearchCondition;
import com.mindgoal.domain.expert.service.ExpertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/experts")
public class ExpertController {
    private final ExpertService expertService;

    @PostMapping
    public ResponseEntity<BaseResponse<ExpertResponse>> register(
            @RequestBody @Valid ExpertCreateRequest request) {
        ExpertResponse response = expertService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(response, "전문가 등록 성공"));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<Page<ExpertListResponse>>> getAllExperts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) Integer minCareerYears,
            @RequestParam(required = false) Integer maxCareerYears,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "rating") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        ExpertSearchCondition condition = ExpertSearchCondition.ofDetail(
                category, position, region,
                minCareerYears, maxCareerYears,
                minRating, minPrice, maxPrice);

        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<ExpertListResponse> response = expertService.searchExperts(condition, pageable);
        return ResponseEntity.ok(BaseResponse.success(response, "전문가 목록 조회 성공"));
    }
}