package com.grepp.spring.infra.config.security;

import com.grepp.spring.app.model.auth.domain.Principal;
import com.grepp.spring.app.model.member.MemberRepository;
import com.grepp.spring.app.model.member.entity.Member;
import com.grepp.spring.app.model.team.TeamMemberRepository;
import com.grepp.spring.app.model.team.entity.TeamMember;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final MemberRepository memberRepository;
    private final TeamMemberRepository teamMemberRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) {
        Member member = memberRepository.findById(username)
                            .orElseThrow(() -> new UsernameNotFoundException(username));
        List<SimpleGrantedAuthority> authorities = findUserAuthorities(username);
        return Principal.createPrincipal(member, authorities);
    }
    
    @Cacheable(cacheNames="authority")
    public List<SimpleGrantedAuthority> findUserAuthorities(String username){
        Member member = memberRepository.findById(username)
                            .orElseThrow(() -> new UsernameNotFoundException(username));
        
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getRole().name()));
        
        List<TeamMember> teamMembers = teamMemberRepository.findByUserIdAndActivated(username,
            true);
        
        List<SimpleGrantedAuthority> teamAuthorities =
            teamMembers.stream()
                .map(e -> new SimpleGrantedAuthority("TEAM_" + e.getTeamId() + ":" + e.getRole()))
                .toList();
        authorities.addAll(teamAuthorities);
        return authorities;
    }
}
