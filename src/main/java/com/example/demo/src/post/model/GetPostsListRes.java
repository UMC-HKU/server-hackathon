package com.example.demo.src.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetPostsListRes {
    private int postIdx;
    private String title;
    private String content;
    private String createdAt;
    private int commentCount;
}
