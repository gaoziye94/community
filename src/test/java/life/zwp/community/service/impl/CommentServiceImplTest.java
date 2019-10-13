package life.zwp.community.service.impl;

import life.zwp.community.mapper.CommentMapper;
import life.zwp.community.model.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceImplTest {
    @Autowired
    private CommentMapper commentMapper;
    @Test
    public void insert() {
        Comment comment = new Comment();
        comment.setCommentator(15L);
        comment.setContent("好问题111");
        comment.setParentId(534L);
        comment.setType(0);
        comment.setLikeCount(0L);
        comment.setGmtCreate(new Date().getTime());
        comment.setGmtModified(comment.getGmtCreate());
        try {
            int count = commentMapper.insert(comment);
            System.err.println("成功"+count);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("失败"+e.getMessage());
        }
    }

    @Test
    public void delete() {
        Comment comment = commentMapper.selectById(1L);
        if(comment !=null){
            int count = commentMapper.delete(comment);
            System.out.println("删除成功"+count);
        } else {
            System.out.println("错误");
        }
    }

    @Test
    public void update() {
        Comment comment = commentMapper.selectById(1L);
        if(comment !=null){
            comment.setContent("这是一个好问题");
            comment.setLikeCount(50L);
            comment.setGmtModified(new Date().getTime());
            commentMapper.update(comment);
            System.out.println(comment.toString());
        } else {
            System.out.println("错误");
        }
    }

    @Test
    public void selectById() {
        Comment comment = commentMapper.selectById(1L);
        if(comment !=null){
            System.out.println(comment.toString());
        } else {
            System.out.println("错误");
        }
    }
}
