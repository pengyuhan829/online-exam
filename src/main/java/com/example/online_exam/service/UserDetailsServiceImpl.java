package com.example.online_exam.service;

import com.example.online_exam.entity.User;
import com.example.online_exam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 去数据库查人
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));

        // 2. 把我们自己的 User 对象，转换成 Spring Security 认识的 UserDetails 对象
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword()) // 这里的密码已经是加密过的了
                .roles(user.getRole())        // 比如 "TEACHER" -> 自动变成 "ROLE_TEACHER"
                .build();
    }
}