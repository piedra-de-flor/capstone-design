package com.capstone.capstonedesign.web.controller.membership;

import com.capstone.capstonedesign.dto.membership.JwtToken;
import com.capstone.capstonedesign.dto.membership.MemberResponseDto;
import com.capstone.capstonedesign.dto.membership.MemberSignInDto;
import com.capstone.capstonedesign.dto.membership.MemberSignUpRequestDto;
import com.capstone.capstonedesign.service.membership.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@Tag(name = "회원 Controller", description = "Member API")
@RestController
public class MemberController {
    private final MemberService service;

    @Operation(summary = "회원가입", description = "새로운 회원을 생성합니다")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다")
    @ApiResponse(responseCode = "500", description = "내부 서버 오류 발생")
    @PostMapping("/sign-up")
    public ResponseEntity<MemberResponseDto> signUp(
            @Parameter(description = "회원 가입 요청 정보", required = true)
            @RequestBody @Validated final MemberSignUpRequestDto userJoinRequestDto) {
        MemberResponseDto responseDto = service.signUp(userJoinRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "로그인", description = "이메일와 비밀번호를 통해 로그인합니다")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다")
    @ApiResponse(responseCode = "500", description = "내부 서버 오류 발생")
    @PostMapping("/sign-in")
    public ResponseEntity<JwtToken> signIn(
            @Parameter(description = "로그인 요청 정보", required = true)
            @RequestBody @Validated MemberSignInDto signInDto) {
        JwtToken token = service.signIn(signInDto.email(), signInDto.password());
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "회원 정보 조회", description = "JWT토큰을 통해 회원 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다")
    @ApiResponse(responseCode = "500", description = "내부 서버 오류 발생")
    @GetMapping("/member")
    public ResponseEntity<MemberResponseDto> read() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberEmail = authentication.getName();

        MemberResponseDto response = service.read(memberEmail);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "회원 닉네임 수정", description = "회원의 nickName을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다")
    @ApiResponse(responseCode = "403", description = "JWT토큰이 잘못되었습니다")
    @ApiResponse(responseCode = "500", description = "내부 서버 오류 발생")
    @PatchMapping("/member/nick-name")
    public ResponseEntity<String> updateNickName(@RequestBody String nickName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberEmail = authentication.getName();

        String response = service.updateNickName(memberEmail, nickName);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "회원 비밀번호 수정", description = "회원의 password을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다")
    @ApiResponse(responseCode = "403", description = "JWT토큰이 잘못되었습니다")
    @ApiResponse(responseCode = "500", description = "내부 서버 오류 발생")
    @PatchMapping("/member/password")
    public ResponseEntity<Boolean> updatePassword(@RequestBody String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberEmail = authentication.getName();

        boolean response = service.updatePassword(memberEmail, password);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "회원 삭제", description = "JWT토큰과 이메일을 통해 회원정보를 삭제합니다")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다")
    @ApiResponse(responseCode = "403", description = "JWT토큰이 잘못되었습니다")
    @ApiResponse(responseCode = "500", description = "내부 서버 오류 발생")
    @DeleteMapping("/member")
    public ResponseEntity<Boolean> delete() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberEmail = authentication.getName();

        boolean response = service.delete(memberEmail);
        return ResponseEntity.ok(response);
    }
}
