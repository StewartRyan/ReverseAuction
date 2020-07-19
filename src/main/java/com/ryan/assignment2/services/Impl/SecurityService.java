package com.ryan.assignment2.services.Impl;

import com.ryan.assignment2.services.ISecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SecurityService implements ISecurityService
{
    @Autowired
    private AuthenticationManager _authenticationManager;

    @Autowired
    private UserDetailsService _userDetailsService;

    public SecurityService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        _authenticationManager = authenticationManager;
        _userDetailsService = userDetailsService;
    }

    private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    @Override
    public String findLoggedInUsername()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken))
        {
            return authentication.getName();
        }

        return "";
    }

    @Override
    public void autoLogin(String username, String password)
    {
        UserDetails userDetails = _userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        _authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated())
        {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            logger.debug(String.format("Auto login %s successfully!", username));
        }
    }
}
