package com.example.demo.src.comment;


import com.example.demo.config.BaseException;
import com.example.demo.src.comment.model.PostCommentsReq;
import com.example.demo.src.comment.model.PostCommentsRes;
import com.example.demo.src.post.PostDao;
import com.example.demo.src.post.PostProvider;
import com.example.demo.src.post.model.PostPostsReq;
import com.example.demo.src.post.model.PostPostsRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

// Service Create, Update, Delete 의 로직 처리
@Service
public class CommentService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CommentDao commentDao;
    private final CommentProvider commentProvider;
    private final JwtService jwtService;

    @Autowired
    public CommentService(CommentDao commentDao, CommentProvider commentProvider, JwtService jwtService) {
        this.commentDao = commentDao;
        this.commentProvider = commentProvider;
        this.jwtService = jwtService;
    }


    /**
     * 2.1 댓글 생성 api
     * @param postCommentsReq
     * @throws BaseException
     * @author minjun
     */

    public PostCommentsRes createComments (PostCommentsReq postCommentsReq) throws BaseException{
        try {
            int commentIdx = commentDao.insertComments(postCommentsReq.getPostIdx(),postCommentsReq.getContent());
            return new PostCommentsRes(commentIdx);
        }
        catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

}