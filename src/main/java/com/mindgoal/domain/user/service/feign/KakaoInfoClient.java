package com.mindgoal.domain.user.service.feign;


import com.mindgoal.domain.user.dto.KakaoUnlinkResponse;
import com.mindgoal.domain.user.dto.KakaoUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakao-info", url = "https://kapi.kakao.com")
public interface KakaoInfoClient {

    @GetMapping("/v2/user/me")
    KakaoUserInfo getUserInfo(@RequestHeader("Authorization") String authorization);

    @PostMapping(value = "/v1/user/unlink")
    KakaoUnlinkResponse unlink(@RequestHeader("Authorization") String authorization);
}
