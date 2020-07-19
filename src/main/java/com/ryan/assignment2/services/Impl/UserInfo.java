package com.ryan.assignment2.services.Impl;

import com.ryan.assignment2.domain.entities.Member;
import com.ryan.assignment2.domain.entities.Role;
import com.ryan.assignment2.repositories.IMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Primary
public class UserInfo implements UserDetailsService
{
    @Autowired
    private IMemberRepository _memberRepository;

    public UserInfo(IMemberRepository userRepository) {
        _memberRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)
    {

        Member member = _memberRepository.findByEmail(username);
        if (member == null)
            throw new UsernameNotFoundException(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : member.getRoles())
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));

        return new org
                .springframework
                .security
                .core
                .userdetails
                .User(
                        member.getEmail(),
                        member.getPassHash(),
                        grantedAuthorities
        );
    }
}