package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.entity.Category;
import com.zerobase.fastlms.admin.mapper.CategoryMapper;
import com.zerobase.fastlms.admin.model.CategoryInput;
import com.zerobase.fastlms.admin.repository.CategoryRepository;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.entity.Course;
import com.zerobase.fastlms.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CourseRepository courseRepository;

    private Sort getSortBySortValueDesc() {
        return Sort.by(Sort.Direction.DESC, "sortValue");
    }
    
    @Override
    public List<CategoryDto> list() {
        List<Category> categories = categoryRepository.findAll(getSortBySortValueDesc());
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
    
    @Override
    public List<CategoryDto> frontList(CategoryDto parameter, String userName) {

        return categoryMapper.select(parameter, userName);
    }

    @Override
    public List<CourseDto> findAllCourse() {
        List<Course> courseList = courseRepository.findAll();

        if (courseList.isEmpty())
            return Collections.emptyList();

        return CourseDto.of(courseList);
    }
}
