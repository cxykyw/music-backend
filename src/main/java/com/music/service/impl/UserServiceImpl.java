package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.music.dto.LoginDTO;
import com.music.dto.RegisterDTO;
import com.music.dto.UserUpdateDTO;
import com.music.entity.User;
import com.music.entity.UserFollow;
import com.music.mapper.UserMapper;
import com.music.mapper.UserFollowMapper;
import com.music.service.UserService;
import com.music.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserMapper userMapper;
    private final UserFollowMapper userFollowMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    /**
     * 用户登录
     * @param loginDTO 登录信息
     * @return JWT token
     * @throws RuntimeException 用户名或密码错误时抛出
     */
    @Override
    public String login(LoginDTO loginDTO) {
        User user = userMapper.selectOne(
            new QueryWrapper<User>().eq("username", loginDTO.getUsername())
        );
        
        if (user != null && passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return jwtUtil.generateToken(user.getUsername());
        }
        throw new RuntimeException("用户名或密码错误");
    }
    
    /**
     * 用户注册
     * @param registerDTO 注册信息
     * @return 注册成功的用户信息
     * @throws RuntimeException 用户名已存在时抛出
     */
    @Override
    public User register(RegisterDTO registerDTO) {
        // 检查用户名是否已存在
        if (userMapper.selectOne(new QueryWrapper<User>().eq("username", registerDTO.getUsername())) != null) {
            throw new RuntimeException("用户名已存在");
        }
        
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setNickname(registerDTO.getNickname());
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        
        userMapper.insert(user);
        return user;
    }
    
    @Override
    public User getCurrentUser(String username) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }
    
    @Override
    public void updateUserInfo(Long userId, UserUpdateDTO userUpdateDTO) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        user.setNickname(userUpdateDTO.getNickname());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
    }
    
    @Override
    public void updateAvatar(Long userId, String avatarUrl) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        user.setAvatar(avatarUrl);
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
    }
    
    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("原密码错误");
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
    }
    
    @Override
    public void follow(Long followerId, Long followedId) {
        if (followerId.equals(followedId)) {
            throw new RuntimeException("不能关注自己");
        }
        
        if (isFollowing(followerId, followedId)) {
            throw new RuntimeException("已经关注过了");
        }
        
        UserFollow userFollow = new UserFollow();
        userFollow.setFollowerId(followerId);
        userFollow.setFollowedId(followedId);
        userFollow.setCreateTime(LocalDateTime.now());
        userFollowMapper.insert(userFollow);
    }
    
    @Override
    public void unfollow(Long followerId, Long followedId) {
        userFollowMapper.delete(
            new QueryWrapper<UserFollow>()
                .eq("follower_id", followerId)
                .eq("followed_id", followedId)
        );
    }
    
    @Override
    public List<User> getFollowers(Long userId, int page, int size) {
        Page<UserFollow> followPage = userFollowMapper.selectPage(
            new Page<>(page, size),
            new QueryWrapper<UserFollow>().eq("followed_id", userId)
        );
        
        return followPage.getRecords().stream()
            .map(follow -> userMapper.selectById(follow.getFollowerId()))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<User> getFollowing(Long userId, int page, int size) {
        Page<UserFollow> followPage = userFollowMapper.selectPage(
            new Page<>(page, size),
            new QueryWrapper<UserFollow>().eq("follower_id", userId)
        );
        
        return followPage.getRecords().stream()
            .map(follow -> userMapper.selectById(follow.getFollowedId()))
            .collect(Collectors.toList());
    }
    
    @Override
    public boolean isFollowing(Long followerId, Long followedId) {
        return userFollowMapper.selectCount(
            new QueryWrapper<UserFollow>()
                .eq("follower_id", followerId)
                .eq("followed_id", followedId)
        ) > 0;
    }
} 