package com.zerobase.fastlms.main.controller;


import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.service.BannerService;
import com.zerobase.fastlms.components.MailComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final MailComponents mailComponents;
    private final BannerService bannerService;

    @RequestMapping("/")
    public String index(Model model) {

        List<Banner> banner = bannerService.mainPage();
        model.addAttribute("banner", banner);

        /*
        String email = "satcop@naver.com";
        String subject = " 안녕하세요. 제로베이스 입니다. ";
        String text = "<p>안녕하세요.</p><p>반갑습니다.</p>";
        
        mailComponents.sendMail(email, subject, text);
        */
        
        return "index";
    }
    
    
    
    @RequestMapping("/error/denied")
    public String errorDenied() {
        
        return "error/denied";
    }
    
    
    
}
