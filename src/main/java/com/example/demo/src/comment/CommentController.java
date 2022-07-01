package com.example.demo.src.comment;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.comment.model.PostCommentsReq;
import com.example.demo.src.comment.model.PostCommentsRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/comments")
public class CommentController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final CommentProvider commentProvider;
    @Autowired
    private final CommentService commentService;
    @Autowired
    private final JwtService jwtService;


    public CommentController(CommentProvider commentProvider, CommentService commentService, JwtService jwtService){
        this.commentProvider = commentProvider;
        this.commentService = commentService;
        this.jwtService = jwtService;
    }

    /**
     * 2.1 게시물 댓글 api : /post
     * @param postCommentsReq
     * @author minjun
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostCommentsRes> createComments(@RequestBody PostCommentsReq postCommentsReq) {
        try { //형식적 validation
            if (postCommentsReq.getContent().length() > 100) {
                return new BaseResponse<>(BaseResponseStatus.POST_POSTS_INVALID_CONTENTS);
            }
            PostCommentsRes postCommentsRes = commentService.createComments(postCommentsReq);
            return new BaseResponse<>(postCommentsRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }



}





