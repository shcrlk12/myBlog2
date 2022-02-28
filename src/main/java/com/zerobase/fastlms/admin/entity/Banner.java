package com.zerobase.fastlms.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String bannerPath;
    String bannerAlterText;
    String bannerName;

    String bannerClickLink;
    String bannerTargetInfo;

    int bannerDisplayNo;
    boolean isDisplay;
    boolean isDelete;

    LocalDateTime regDt;

    @Transient
    Long seq;
}
