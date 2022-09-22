package com.deliveryfood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    public static final String STATUS_NONE = "NONE";
    public static final String STATUS_REGISTER = "REGISTER";
    public static final String STATUS_WITHDRAW = "WITHDRAW";

    private String userId;
    private String name;
    private String phone;
    private String email;
    private String password;
    private String address;
    private String status;
    private LocalDateTime regDt;
    private LocalDateTime udtDt;
}
