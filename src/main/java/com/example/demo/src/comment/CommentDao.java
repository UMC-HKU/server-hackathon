package com.example.demo.src.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class CommentDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /**
     * 2.1 게시물 댓글 api
     * @param postIdx
     * @param content
     * @author minjun
     */
    public int insertComments(int postIdx, String content) {
        String insertCommentQuery = "INSERT INTO Comment(postIdx, content) VALUES (?,?)";
        Object[] insertCommentParams = new Object[]{postIdx, content};
        this.jdbcTemplate.update(insertCommentQuery, insertCommentParams);
        String lastInsertIdxQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdxQuery,int.class);
    }




}
