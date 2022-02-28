package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.admin.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminBannerController {
    private final BannerService bannerService;

    @GetMapping("/admin/banner/list.do")
    public String bannerList(Model model) {

        List<Banner> bannerList = bannerService.bannerList();

        model.addAttribute("banner", bannerList);
        System.out.println("complete");
        return "admin/banner/list";
    }

    @GetMapping(value = {"/admin/banner/add.do","/admin/banner/edit.do"})
    public String bannerAdd(Model model, MemberParam parameter,
                            HttpServletRequest request) {

        Banner banner = new Banner();

        if(request.getRequestURI().contains("/edit.do")) {
            banner = bannerService.updateBanner(Long.parseLong(parameter.getUserId()));
        }

        model.addAttribute("banner", banner);
        return "admin/banner/add";
    }

    @PostMapping(value = {"/admin/banner/add.do", "/admin/banner/edit.do"})
    public String bannerAddPost(BannerInput bannerInput, MultipartFile file, HttpServletRequest request) {
        String localDirPath = "C:/dev/spring/(10-3)김정원 - fastlms3/fastlms3/src/main/resources/static";

        String resourecePath = "/img/blog/banner/";

        String localImgPath = localDirPath + resourecePath + file.getOriginalFilename();

        bannerInput.setBannerPath(localImgPath);
        bannerInput.setBannerAlterText(file.getOriginalFilename());
        bannerInput.setBannerResPath(resourecePath + file.getOriginalFilename());

        if(file != null) {
            File newFile = new File(localImgPath);
            try {
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        if(request.getRequestURI().contains("/edit.do")) {
            bannerService.updateBannerPost(bannerInput);
        }
        else {
            bannerService.addBanner(bannerInput);
        }

        return "redirect:/admin/banner/list.do";
    }

    @PostMapping("/admin/banner/delete.do")
    public String deleteBanner(BannerInput bannerInput){

        bannerService.delelteBanner(bannerInput.getIdList());

        return "redirect:/admin/banner/list.do";
    }

}
