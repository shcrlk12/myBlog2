package com.zerobase.fastlms.course.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    long categoryId;
    
    String imagePath;
    String keyword;
    String subject;
    
    @Column(length = 1000)
    String summary;
    
    @Lob
    String contents;

    String writer;

    LocalDateTime regDt;//등록일(추가날짜)
    LocalDateTime udtDt;//수정일(수정날짜)

    String filename;
    String urlFilename;

    @OneToMany
    @JoinColumn(name = "comment_id")
    private List<Comment> comments = new ArrayList<>();

}
