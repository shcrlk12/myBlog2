package com.zerobase.fastlms.admin.controller;


import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.model.CategoryInput;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.admin.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminCategoryController {
    
    private final CategoryService categoryService;
    
    @GetMapping("/category/list")
    public String list(Model model, MemberParam parameter) {
        
        List<CategoryDto> list = categoryService.list();
        model.addAttribute("list", list);
      
        return "/category/list";
    }
    
    
    @PostMapping("/category/add")
    public String add(Model model, CategoryInput parameter, Principal principal) {
    
        boolean result = categoryService.add(parameter.getCategoryName(), principal.getName());
    
        return "redirect:/category/list";
    }
    
    @PostMapping("/category/delete")
    public String del(Model model, CategoryInput parameter) {
        
        boolean result = categoryService.del(parameter.getId());
        
        return "redirect:/category/list";
    }
    
    @PostMapping("/category/update")
    public String update(Model model, CategoryInput parameter, Principal principal) {
        
        boolean result = categoryService.update(parameter, principal.getName());
        
        return "redirect:/category/list";
    }
    
}
