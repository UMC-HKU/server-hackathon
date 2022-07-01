package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
//import com.example.demo.src.post.model.*;
import com.example.demo.src.post.model.GetPostsListRes;
import com.example.demo.src.post.model.GetPostsRes;
import com.example.demo.src.post.model.PostPostsReq;
import com.example.demo.src.post.model.PostPostsRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/posts")
public class PostController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired 
    private final PostProvider postProvider;
    @Autowired
    private final PostService postService;
    @Autowired
    private final JwtService jwtService;


    public PostController(PostProvider postProvider, PostService postService, JwtService jwtService){
        this.postProvider = postProvider;
        this.postService = postService;
        this.jwtService = jwtService;
    }

    /**
     * 1.1 게시판 리스트 조회 api
     * @author boyeong
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetPostsListRes>> getPostsList() {
        try {
            List<GetPostsListRes> getPostsListRes = postProvider.retrievePostsList();
            return new BaseResponse<>(getPostsListRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 1.3 게시물 생성 api : /post
     * @param postPostsReq
     * @author taehyun
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostPostsRes> createPosts(@RequestBody PostPostsReq postPostsReq) {
        try { //형식적 validation
            if (postPostsReq.getContent().length() > 500) {
                return new BaseResponse<>(BaseResponseStatus.POST_POSTS_INVALID_CONTENTS);
            }
            PostPostsRes postPostsRes = postService.createPosts(postPostsReq);
            return new BaseResponse<>(postPostsRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/{postIdx}") // (GET) 127.0.0.1:9000/posts
    public BaseResponse<GetPostsRes> getPosts(@PathVariable ("postIdx") int postIdx) { //<GetUserRes> model: 응답값.
        //Model에서는 필요한 요청값/응답값 형식을 정리해놓는다. 어떠한 형태/어떠한 데이터를 출력할건지/클라에게 전달할건지 정의를 해주는곳
        try {
            GetPostsRes getPostsRes = postProvider.getPosts(postIdx);
            return new BaseResponse<>(getPostsRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}





