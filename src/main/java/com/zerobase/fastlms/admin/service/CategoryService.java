package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.entity.Category;
import com.zerobase.fastlms.admin.model.CategoryInput;
import com.zerobase.fastlms.course.dto.CourseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryService {
    
    List<CategoryDto> list();
    
    /**
     * 카테고리 신규 추가
     */
    boolean add(String categoryName, String userName);
    
    /**
     * 카테고리 수정
     */
    boolean update(CategoryInput parameter, String userName);
    
    /**
     * 카테고리 삭제
     */
    boolean del(long id);
    
    
    /**
     * 프론트 카테고리 정보
     */
    List<CategoryDto> frontList(CategoryDto parameter, String userName);
    

    List<CourseDto> findAllCourse();

}
