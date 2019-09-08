package life.zwp.community.service.serviceimpl;

import com.github.pagehelper.PageHelper;
import life.zwp.community.dto.PaginationDTO;
import life.zwp.community.dto.QuestionDTO;
import life.zwp.community.mapper.QuestionMapper;
import life.zwp.community.mapper.UserMapper;
import life.zwp.community.model.Question;
import life.zwp.community.model.User;
import life.zwp.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("publishServiceImpl")
@Transactional
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public void create(Question question) {
        questionMapper.insert(question);
    }

    @Override
    public List<Question> findAll(Integer page, Integer size) {
        //开始分页
        PageHelper.startPage(page, size);
        return questionMapper.findAll();
    }

    @Override
    public PaginationDTO findQuestion(Integer page, Integer size) {
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        List<Question> questions = findAll(page, size);
//        总数据
        Integer creator = 0;
        Integer totalCount = questionMapper.count(creator);
        QuestionDTO questionDTO ;
        User user;
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questions) {
            questionDTO = new QuestionDTO();
            questionDTO.setQuestion(question);
            //根据问题的发布人查询这个用户
            creator = question.getCreator();
            user = userMapper.findByCreator(creator);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setContent(questionDTOS);
        paginationDTO.setPaginationDTO(totalCount,page,size);
        return paginationDTO;
    }

    @Override
    public PaginationDTO findQuestionByUserId(Integer page, Integer size, Integer userId) {
        //        总数据
        Integer creator = userId;
        Integer totalCount = questionMapper.count(creator);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        Integer start = (page-1)*size;
        List<Question> questions = questionMapper.findQuestionByUserId(start, size, userId);
        User user = userMapper.findByCreator(userId);
        QuestionDTO questionDTO ;
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questions) {
            questionDTO = new QuestionDTO();
            questionDTO.setQuestion(question);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setContent(questionDTOS);
        paginationDTO.setPaginationDTO(totalCount,page,size);
        return paginationDTO;
    }

    @Override
    public Question findQuestionById(Integer id) {
        return questionMapper.findQuestionById(id);
    }

    @Override
    public void update(Question question) {
        questionMapper.update(question);
    }

}
