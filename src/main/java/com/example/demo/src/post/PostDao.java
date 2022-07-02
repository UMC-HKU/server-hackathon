package com.example.demo.src.post;


import com.example.demo.src.post.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PostDao {

    private JdbcTemplate jdbcTemplate;
    private List<GetCommentsRes> getCommentsRes;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 1.1 게시판 리스트 조회 api
     * @author boyeong
     */
    public List<GetPostsListRes> readPostsList() {
        String readPostsListQuery = "SELECT p.postIdx as postIdx, p.title as title, p.content as content, p.createdAt as createdAt, " +
                "COUNT(c.content) as commentCount " +
                "FROM Post as p " +
                "left join Comment as c on c.postIdx = p.postIdx " +
                "GROUP BY p.postIdx ORDER BY p.postIdx DESC";

        return this.jdbcTemplate.query(readPostsListQuery,
                (rs,rowNum) -> new GetPostsListRes(
                        rs.getInt("postIdx"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("createdAt"),
                        rs.getInt("commentCount")));
    }

    /**
     * 1.2 게시물 리스트 조회
     * @param postIdx
     * @author hyeonwoo
     */
    public GetPostsRes getPosts(int postIdx){
        String selectPostsQuery = "SELECT * FROM Post WHERE postIdx = ?;";

        int selectPostsParam = postIdx;
        return this.jdbcTemplate.queryForObject(selectPostsQuery,
                (rs,rowNum) -> new GetPostsRes(
                        rs.getInt("postIdx"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("createdAt"),
                        getCommentsRes = this.jdbcTemplate.query("SELECT commentIdx, content, createdAt FROM Comment WHERE postIdx = ?;",(rk, count) ->new GetCommentsRes(
                                rk.getInt("commentIdx"),
                                rk.getString("content"),
                                rk.getString("createdAt")
                        ), selectPostsParam )
                ), selectPostsParam);
    }


    /**
     * 1.3 게시물 생성 api
     * @param title
     * @param content
     * @author taehyun
     */
    public int insertPosts(String title, String content) {
        String insertPostQuery = "INSERT INTO Post(title, content) VALUES (?,?)";
        Object[] insertPostParams = new Object[]{title, content};
        this.jdbcTemplate.update(insertPostQuery, insertPostParams);
        String lastInsertIdxQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdxQuery,int.class);
    }
}

