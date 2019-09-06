package life.zwp.community.service;

import life.zwp.community.dto.PaginationDTO;
import life.zwp.community.dto.QuestionDTO;
import life.zwp.community.model.Question;

import java.util.List;

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
     */
    PaginationDTO findQuestion(Integer page, Integer size);
}
