package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.course.dto.CommentDto;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParam;

import java.util.List;
import java.util.Set;

public interface CourseService {
    
    
    /**
     * 강좌 등록
     */
    boolean add(CourseInput parameter, String userName);
    
    /**
     * 강좌 정보수정
     */
    boolean set(CourseInput parameter, String userName);
    
    /**
     * 강좌 목록
     */
    List<CourseDto> list(CourseParam parameter, String userName);
    
    /**
     * 강좌 상세정보
     */
    CourseDto getById(long id);
    
    /**
     * 강좌 내용 삭제
     */
    boolean del(String idList);
    
    /**
     * 프론트 강좌 목록
     */
    List<CourseDto> frontList(CourseParam parameter, String userName);
    
    /**
     * 프론트 강좌 상세 정보
     */
    CourseDto frontDetail(long id);

    /**
     * 전체 강좌 목록
     */
    List<CourseDto> listAll();

    void postComment(Long blogId, String text, String name);

    List<CommentDto> getComments(long id);
}
