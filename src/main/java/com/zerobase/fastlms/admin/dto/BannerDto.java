package com.zerobase.fastlms.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BannerDto {

    Long id;

    String bannerPath;
    String bannerAlterText;
    String bannerName;

    String bannerClickLink;
    String bannerTargetInfo;

    int bannerDisplayNo;
    boolean isDisplay;

    LocalDateTime regDt;

    Long seq;
}
