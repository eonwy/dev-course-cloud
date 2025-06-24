package com.grepp.spring.app.model.member;

import com.grepp.spring.app.model.auth.code.Role;
import com.grepp.spring.app.model.member.dto.MemberDto;
import com.grepp.spring.app.model.member.entity.Member;
import com.grepp.spring.app.model.member.entity.MemberInfo;
import com.grepp.spring.infra.error.exceptions.CommonException;
import com.grepp.spring.infra.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService{
    
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final ModelMapper mapper;
    
    @Transactional
    public void signup(MemberDto dto, Role role) {
        if(memberRepository.existsById(dto.getUserId()))
            throw new CommonException(ResponseCode.BAD_REQUEST);
        
        Member member = mapper.map(dto, Member.class);
        
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        member.setPassword(encodedPassword);
        member.setRole(role);
        
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setUserId(dto.getUserId());
        member.setInfo(memberInfo);
        memberRepository.save(member);
    }
    
    public Boolean isDuplicatedId(String id) {
        return memberRepository.existsById(id);
    }
    
    public MemberDto findById(String userId) {
        Member member = memberRepository.findById(userId)
                            .orElseThrow(() -> new CommonException(ResponseCode.BAD_REQUEST));
        return mapper.map(member, MemberDto.class);
    }
}
