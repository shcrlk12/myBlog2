package com.zerobase.fastlms.admin.entity;


import com.zerobase.fastlms.course.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    String categoryName;
    String writer;

    int sortValue;
    boolean usingYn;

    @OneToMany
    @JoinColumn(name = "course_id")
    private List<Course> courseList = new ArrayList<>();
}
