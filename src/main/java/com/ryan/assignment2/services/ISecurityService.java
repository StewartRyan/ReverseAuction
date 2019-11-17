package com.ryan.assignment2.services;

public interface ISecurityService
{
    String findLoggedInUsername();
    void autoLogin(String username, String password);
}
