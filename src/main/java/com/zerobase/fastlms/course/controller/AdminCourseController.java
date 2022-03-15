package com.zerobase.fastlms.course.controller;


import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminCourseController extends BaseController {
    
    private final CourseService courseService;
    private final CategoryService categoryService;
    
    @GetMapping(value = {"/course/add", "/course/edit"})
    public String add(Model model, HttpServletRequest request
            , CourseInput parameter, Principal principal) {
        
        //카테고리 정보를 내려줘야 함.
        model.addAttribute("category", categoryService.list(principal.getName()));
        
        boolean editMode = request.getRequestURI().contains("/edit");
        CourseDto detail = new CourseDto();
        
        if (editMode) {
            long id = parameter.getId();
            CourseDto existCourse = courseService.getById(id);
            if (existCourse == null) {
                // error 처리
                model.addAttribute("message", "강좌정보가 존재하지 않습니다.");
                return "common/error";
            }
            detail = existCourse;
        }
        
        model.addAttribute("editMode", editMode);
        model.addAttribute("detail", detail);
        
        return "course/add";
    }
    
    
    
    
    private String[] getNewSaveFile(String baseLocalPath, String baseUrlPath, String originalFilename) {
    
        LocalDate now = LocalDate.now();
    
        String[] dirs = {
                String.format("%s/%d/", baseLocalPath,now.getYear()),
                String.format("%s/%d/%02d/", baseLocalPath, now.getYear(),now.getMonthValue()),
                String.format("%s/%d/%02d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth())};
        
        String urlDir = String.format("%s/%d/%02d/%02d/", baseUrlPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        
        for(String dir : dirs) {
            File file = new File(dir);
            if (!file.isDirectory()) {
                file.mkdir();
            }
        }
        
        String fileExtension = "";
        if (originalFilename != null) {
            int dotPos = originalFilename.lastIndexOf(".");
            if (dotPos > -1) {
                fileExtension = originalFilename.substring(dotPos + 1);
            }
        }
        
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String newFilename = String.format("%s%s", dirs[2], uuid);
        String newUrlFilename = String.format("%s%s", urlDir, uuid);
        if (fileExtension.length() > 0) {
            newFilename += "." + fileExtension;
            newUrlFilename += "." + fileExtension;
        }
    
        return new String[]{newFilename, newUrlFilename};
    }
    
    @PostMapping(value = {"/course/add", "/course/edit"})
    public String addSubmit(Model model, HttpServletRequest request
                            , MultipartFile file, Principal principal
            , CourseInput parameter) {
    
        String saveFilename = "";
        String urlFilename = "";
        
        if (file != null) {
            String originalFilename = file.getOriginalFilename();

            String basePath = "C:/dev/spring/fastlms3_blog/fastlms3/src/main/resources/static";
            String baseLocalPath = "/img/blog/thumbnail";
            String baseUrlPath = "/img/blog/thumbnail";
            
            String[] arrFilename = getNewSaveFile(baseLocalPath, baseUrlPath, originalFilename);
    
            saveFilename = arrFilename[0];
            urlFilename = arrFilename[1];
            System.out.println("saveFilename");
            System.out.println(saveFilename);
            System.out.println(urlFilename);

            try {
                File newFile = new File(basePath + saveFilename);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
            } catch (IOException e) {
                log.info("############################ - 1");
                log.info(e.getMessage());
            }
        }
        
        parameter.setFilename(saveFilename);
        parameter.setUrlFilename(urlFilename);
        
        boolean editMode = request.getRequestURI().contains("/edit");
        
        if (editMode) {
            long id = parameter.getId();
            CourseDto existCourse = courseService.getById(id);
            if (existCourse == null) {
                // error 처리
                model.addAttribute("message", "강좌정보가 존재하지 않습니다.");
                return "common/error";
            }
            
            boolean result = courseService.set(parameter, principal.getName());
            
        } else {
            boolean result = courseService.add(parameter, principal.getName());
        }
        
        return "redirect:/edit-blog-list";
    }
    
    @PostMapping("/course/delete")
    public String del(Model model, HttpServletRequest request
            , CourseInput parameter) {
        
        boolean result = courseService.del(parameter.getIdList());
        
        return "redirect:/edit-blog-list";
    }
    
    
}
