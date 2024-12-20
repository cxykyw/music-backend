package com.music.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.music.entity.User;
import com.music.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtil {
    
    private final UserMapper userMapper;
    
    public User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return userMapper.selectOne(
            new QueryWrapper<User>().eq("username", userDetails.getUsername())
        );
    }
    
    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }
} 