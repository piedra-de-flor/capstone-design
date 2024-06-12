package com.capstone.capstonedesign.dto.membership;

import com.capstone.capstonedesign.domain.entity.membership.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record MemberSignUpRequestDto(
        @NotBlank(message = "Name can not be null")
        String nickName,
        @NotBlank(message = "Email can not be null")
        @Email
        String email,
        @NotBlank(message = "Password can not be null")
        @Size(min = 4, max = 13, message = "password size must be between 4 and 13")
        String password,
        String role) {
        public Member toEntity(String password, List<String> roles) {
                return Member.builder()
                        .name(nickName)
                        .email(email)
                        .password(password)
                        .roles(roles)
                        .build();
        }
}
