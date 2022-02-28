package com.zerobase.fastlms.admin.model;

import lombok.Data;

@Data
public class BannerInput {
    String bannerName;
    String bannerClickLink;
    String bannerTargetInfo;
    int  bannerDisplayNo;
    boolean  display;

    String bannerPath;
    String bannerResPath;

    String bannerAlterText;

    String idList;

    long userId;
}
