package com.zerobase.fastlms.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class LoginHistory {

    @Id
    String userId;

    String userEmail;
    LocalDateTime loginDt;

    String userIp;
    String userAgent;
}
