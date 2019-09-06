package life.zwp.community.dto;

import life.zwp.community.model.Question;
import life.zwp.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Question question;
    private User user;
}
