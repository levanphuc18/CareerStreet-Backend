package com.careerstreet.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRes {
    private String username;
    private String email;
    private boolean active;
    private Long roleId;
}
