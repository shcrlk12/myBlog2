package com.zerobase.fastlms.course.repository;

import com.zerobase.fastlms.course.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
