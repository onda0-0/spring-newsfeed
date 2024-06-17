package com.sparta.springnewsfeed.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawRequestDto {
    @NotBlank
    private String password;
}
