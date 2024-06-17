package com.sparta.springnewsfeed.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Value
@Setter
@Getter
public class LoginRequestDto {
    private String userId;
    private String password;
}