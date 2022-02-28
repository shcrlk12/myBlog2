package com.zerobase.fastlms.course.controller;

import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.course.dto.CourseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AllBlogController {

    private final CategoryService courseService;

    @GetMapping("/blog/all")
    public String readAllBlog(Model model){

        List<CourseDto> list = courseService.findAllCourse();
        model.addAttribute("list", list);

        return "/blog/all";
    }
}
