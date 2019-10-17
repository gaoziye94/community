package life.zwp.community.service;

import life.zwp.community.dto.PaginationDTO;
import life.zwp.community.dto.QuestionDTO;
import life.zwp.community.model.Question;

import java.util.List;
import java.util.Map;

/**
 * 问题接口
 */
public interface QuestionService {
    /**
     * 发布问题
     * @param quertion
     */
    void create(Question quertion);

    /**
     * 查询所有
     * @return
     */
    List<Question> findAll(Integer page, Integer size);

    /**
     * 查询所有问题及用户
     * @return
     * @param page
     * @param size
     * @param search
     */
    PaginationDTO findQuestion(Integer page, Integer size, String search);


    /**
     * 查询某个人的所有问题
     * @param page
     * @param size
     * @param userId
     * @return
     */
    PaginationDTO findQuestionByUserId(Integer page, Integer size, Long userId);

    /**
     * 根据问题id查找问题
     * @param id
     * @return
     */
    Question findQuestionById(Long id);

    /**
     * 更新问题，根据id
     * @param question
     */
    void update(Question question);

    /**
     * 根据问题id,查询问题，并返回问题发布人
     * @param id
     * @return
     */
    QuestionDTO findQuestionDTOById(Long id);

    /**
     * 每次浏览次数+1,防止并发
     * @param record
     */
    void updateViewCount(Question record);

    /**
     * 获取评论列表根据问题id
     * @param id
     * @param type
     * @return
     */
    List<Map<String,Object>> commentList(Long id, Integer type);

    /**
     * 相关问题
     * @param id
     * @param tags
     * @return
     */
    List<Question> relatedQuestion(Long id, String tags);

    /**
     * 删除问题，根据id
     * @param question
     * @return
     */
    int deleteQuestion(Question question);

    /**
     * 查询我的问题数量
     * @param userId
     * @return
     */
    Integer count(Long userId);
}
