package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.entity.Category;
import com.zerobase.fastlms.admin.mapper.CategoryMapper;
import com.zerobase.fastlms.admin.model.CategoryInput;
import com.zerobase.fastlms.admin.repository.CategoryRepository;
import com.zerobase.fastlms.course.controller.CourseController;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.entity.Course;
import com.zerobase.fastlms.course.repository.CourseRepository;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private  static final org.apache.log4j.Logger log = Logger.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CourseRepository courseRepository;
    private final MemberRepository memberRepository;

    private Sort getSortBySortValueDesc() {
        return Sort.by(Sort.Direction.DESC, "sortValue");
    }
    
    @Override
    public List<CategoryDto> list(String name) {
        Optional<Member> memberOptional = memberRepository.findById(name);
        if(memberOptional.isEmpty()){
            return Collections.emptyList();
        }
        Member member = memberOptional.get();
        List<Category> categories = member.getCategoryList();

        return CategoryDto.of(categories);
    }
    
    @Override
    public boolean add(String categoryName, String userName) {
        
        //카테고리명이 중복인거 체크
        
        Category category = Category.builder()
                .writer(userName)
                .categoryName(categoryName)
                .usingYn(true)
                .sortValue(0)
                .build();
        categoryRepository.save(category);

        Optional<Member> memberOptional = memberRepository.findById(userName);

        if(memberOptional.isEmpty())
            return false;

        Member member = memberOptional.get();

        List<Category> categories = member.getCategoryList();

        categories.add(category);
        member.setCategoryList(categories);

        memberRepository.save(member);
        return true;
    }
    
    @Override
    public boolean update(CategoryInput parameter, String userName) {
        
        Optional<Category> optionalCategory = categoryRepository.findById(parameter.getId());
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setWriter(userName);
            category.setCategoryName(parameter.getCategoryName());
            category.setSortValue(parameter.getSortValue());
            category.setUsingYn(parameter.isUsingYn());
            categoryRepository.save(category);
        }
        
        return true;
    }
    
    @Override
    public boolean del(long id) {
        
        categoryRepository.deleteById(id);
        
        return true;
    }

    public List<Category> getCategory(String userName){
        Optional<Member> memberOptional = memberRepository.findById(userName);

        if(memberOptional.isEmpty())
            return Collections.emptyList();

        return memberOptional.get().getCategoryList();
    }

    @Override
    public List<CategoryDto> frontList(String userName) {

        List<Category> categories = getCategory(userName);

        List<CategoryDto> categoryDtoList = CategoryDto.of(categories);

        int index =0;
        for(Category category : categories){
            categoryDtoList.get(index).setCourseCount(category.getCourseList().size());
            index++;
        }
        return categoryDtoList;
    }

    @Override
    public List<CourseDto> findAllCourse() {
        List<Course> courseList = courseRepository.findAll();
        List<CourseDto> courseDtoList = new ArrayList<>();

        if (courseList.isEmpty())
            return Collections.emptyList();

        for(Course c : courseList){
            CourseDto courseDto = CourseDto.of(c);

            Optional<Category> categoryOptional = categoryRepository.findById(c.getCategoryId());
            Category category = categoryOptional.get();

            courseDto.setCategoryName(category.getCategoryName());
            courseDtoList.add(courseDto);
        }
        return courseDtoList;
    }
}
