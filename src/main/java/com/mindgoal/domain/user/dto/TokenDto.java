package com.mindgoal.domain.user.dto;


import com.mindgoal.config.jwt.JwTokenDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenDto {
    private JwTokenDto token;
}