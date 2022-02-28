package com.zerobase.fastlms.admin.controller;


import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.admin.model.MemberInput;
import com.zerobase.fastlms.admin.repository.BannerRepository;
import com.zerobase.fastlms.admin.service.BannerService;
import com.zerobase.fastlms.course.controller.BaseController;
import com.zerobase.fastlms.member.service.MemberService;
import com.zerobase.fastlms.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServletServerHttpRequest;
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

@RequiredArgsConstructor
@Controller
public class AdminMemberController extends BaseController {
    
    private final MemberService memberService;
    private final BannerService bannerService;

    @GetMapping("/admin/member/list.do")
    public String list(Model model, MemberParam parameter) {
        
        parameter.init();
        List<MemberDto> members = memberService.list(parameter);
        
        long totalCount = 0;
        if (members != null && members.size() > 0) {
            totalCount = members.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        String pagerHtml = getPaperHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);
        
        model.addAttribute("list", members);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);
        
        return "admin/member/list";
    }
    
    @GetMapping("/admin/member/detail.do")
    public String detail(Model model, MemberParam parameter) {
        
        parameter.init();
        
        MemberDto member = memberService.detail(parameter.getUserId());
        model.addAttribute("member", member);
       
        return "admin/member/detail";
    }

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
    
    @PostMapping("/admin/member/status.do")
    public String status(Model model, MemberInput parameter) {
    
        
        boolean result = memberService.updateStatus(parameter.getUserId(), parameter.getUserStatus());
        
        return "redirect:/admin/member/detail.do?userId=" + parameter.getUserId();
    }
    
    
    @PostMapping("/admin/member/password.do")
    public String password(Model model, MemberInput parameter) {
        
        
        boolean result = memberService.updatePassword(parameter.getUserId(), parameter.getPassword());
        
        return "redirect:/admin/member/detail.do?userId=" + parameter.getUserId();
    }

    @PostMapping(value = {"/admin/banner/add.do", "/admin/banner/edit.do"})
    public String bannerAddPost(BannerInput bannerInput, MultipartFile file, HttpServletRequest request) {
        String localDirPath = "C:\\dev\\spring\\(10-3)김정원 - fastlms3\\fastlms3\\files\\banner\\";
        String localImgPath = localDirPath + file.getOriginalFilename();

        bannerInput.setBannerPath(localImgPath);
        bannerInput.setBannerAlterText(file.getOriginalFilename());

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
