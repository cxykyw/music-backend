package com.music.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private String nickname;
    private String oldPassword;
    private String newPassword;
} 