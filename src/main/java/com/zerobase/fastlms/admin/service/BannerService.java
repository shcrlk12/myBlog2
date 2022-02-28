package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.model.BannerInput;

import java.util.List;

public interface BannerService {

    boolean addBanner(BannerInput bannerInput);

    List<Banner> bannerList();

    boolean delelteBanner(String idList);

    Banner updateBanner(Long userId);

    void updateBannerPost(BannerInput bannerInput);

    List<Banner> mainPage();
}
