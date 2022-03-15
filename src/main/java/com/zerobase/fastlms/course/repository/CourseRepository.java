package com.zerobase.fastlms.course.repository;

import com.zerobase.fastlms.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<List<Course>> findByCategoryId(long categoryId);
    List<Course> findByWriter(String writer);

    List<Course> findByCategoryIdAndWriter(long categoryId, String writer);


}
