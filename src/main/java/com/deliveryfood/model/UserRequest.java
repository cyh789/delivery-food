package com.deliveryfood.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Value
public class UserRequest {
    private String email;
    private String password;
}
