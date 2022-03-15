package com.zerobase.fastlms.course.dto;

import com.zerobase.fastlms.course.entity.Comment;
import com.zerobase.fastlms.course.entity.Course;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Setter
@Getter
public class CommentDto {
    Long id;
    String text;
    String userName;
    LocalDateTime regDt;

    public static CommentDto of(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .userName(comment.getUserName())
                .regDt(comment.getRegDt())
                .build();
    }

    public static List<CommentDto> of(List<Comment> comments) {

        if (comments == null) {
            return null;
        }

        List<CommentDto> commentSet = new ArrayList<>();
        for(Comment x : comments) {
            commentSet.add(CommentDto.of(x));
        }
        return commentSet;

    }
}
