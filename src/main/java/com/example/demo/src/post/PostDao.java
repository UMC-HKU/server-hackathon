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

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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

