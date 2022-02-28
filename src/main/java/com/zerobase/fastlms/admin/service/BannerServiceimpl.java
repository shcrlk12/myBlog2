package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BannerServiceimpl implements BannerService{

    private final BannerRepository bannerRepository;

    @Override
    public boolean addBanner(BannerInput bannerInput) {

        Banner banner = Banner.builder()
                .bannerPath(bannerInput.getBannerPath())
                .bannerAlterText(bannerInput.getBannerAlterText())
                .bannerName(bannerInput.getBannerName())
                .bannerClickLink(bannerInput.getBannerClickLink())
                .bannerTargetInfo(bannerInput.getBannerTargetInfo())
                .bannerDisplayNo(bannerInput.getBannerDisplayNo())
                .isDisplay(bannerInput.isDisplay())
                .regDt(LocalDateTime.now())
                .isDelete(false)
                .build();

        bannerRepository.save(banner);
        return true;
    }

    @Override
    public List<Banner> bannerList() {
        List<Banner> bannerList = bannerRepository.findByIsDelete(false);
        long totalSize = bannerList.size();

        System.out.println(totalSize);
        for(Banner x : bannerList) {
            x.setSeq(totalSize--);
        }

        return bannerList;
    }

    @Override
    public boolean delelteBanner(String idList) {
        if (idList != null && idList.length() > 0) {
            String[] ids = idList.split(",");
            for (String x : ids) {
                long id = 0L;
                try {
                    id = Long.parseLong(x);
                } catch (Exception e) {
                }

                if (id > 0) {
                    bannerRepository.setCustomerName(id);
                }
            }
        }
        return false;
    }

    @Override
    public Banner updateBanner(Long userId) {
        Optional<Banner> optionalbanner =  bannerRepository.findById(userId);
        if(!optionalbanner.isPresent()){
            return null;
        }
        Banner banner = optionalbanner.get();

        return banner;
    }

    @Override
    public void updateBannerPost(BannerInput bannerInput) {

        Optional<Banner> optionalBanner = bannerRepository.findById(bannerInput.getUserId());

        if(!optionalBanner.isPresent()) {
            return;
        }

        Banner banner = optionalBanner.get();

        banner.setBannerPath(bannerInput.getBannerPath());
        banner.setBannerAlterText(bannerInput.getBannerAlterText());
        banner.setBannerName(bannerInput.getBannerName());
        banner.setBannerClickLink(bannerInput.getBannerClickLink());
        banner.setBannerTargetInfo(bannerInput.getBannerTargetInfo());
        banner.setBannerDisplayNo(bannerInput.getBannerDisplayNo());
        banner.setDisplay(bannerInput.isDisplay());
        bannerRepository.save(banner);
    }

    @Override
    public List<Banner> mainPage() {

        return bannerRepository.selectBanner();
    }
}
