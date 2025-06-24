package com.grepp.spring.app.controller.web.member.payload;

import com.grepp.spring.app.model.member.dto.MemberDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {
    
    @NotBlank
    private String userId;
    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Zㄱ-힣])(?!.*[ㄱ-힣]).{8,}$"
    ,message = "비밀번호는 8자리 이상의 영문자, 숫자, 특수문자 조합으로 이루어져야 합니다.")
    private String password;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 8, max = 14)
    private String tel;
    
    public MemberDto toDto(){
        MemberDto memberDto = new MemberDto();
        memberDto.setUserId(userId);
        memberDto.setPassword(password);
        memberDto.setEmail(email);
        memberDto.setTel(tel);
        return memberDto;
    }
}
