package com.example.demo.src.post;


import com.example.demo.config.BaseException;
import com.example.demo.src.post.model.GetPostsListRes;
import com.example.demo.src.post.model.GetPostsRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

//Provider : Read의 비즈니스 로직 처리
@Service
public class PostProvider {

    private final PostDao postDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public PostProvider(PostDao postDao, JwtService jwtService) {
        this.postDao = postDao;
        this.jwtService = jwtService;
    }

    /**
     * 1.1 게시판 리스트 조회 api
     * @author boyeong
     */
    public List<GetPostsListRes> retrievePostsList () throws BaseException{
        try {
            List<GetPostsListRes> getPosts = postDao.readPostsList();
            return getPosts;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 1.2 게시물 리스트 조회
     * @param postIdx
     * @return
     * @author hyeonwoo
     */
    public GetPostsRes getPosts (int postIdx) throws BaseException {
        try {
            GetPostsRes getPosts = postDao.getPosts(postIdx);
            return getPosts;
        }
        catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
