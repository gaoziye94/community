package life.zwp.community.service.serviceimpl;

import life.zwp.community.mapper.QuestionMapper;
import life.zwp.community.model.Question;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionServiceImplTest {

    @Autowired
    private QuestionMapper questionMapper;
    @Test
    public void create() {
        Question question ;
        for (int i = 0; i <100 ; i++) {
            question = new Question();
            question.setTitle("标题"+i);
            question.setDescription("内容"+i);
            question.setTags("测试，测试1，测试2");
            question.setCreator(9);
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }
        System.out.println("添加成功");
    }
}
