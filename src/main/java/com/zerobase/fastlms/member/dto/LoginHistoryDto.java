package com.zerobase.fastlms.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LoginHistoryDto {
    String userId;
    LocalDateTime loginDt;
    String userEmail;
    String userAgent;
    String userIp;

    long seq;
}