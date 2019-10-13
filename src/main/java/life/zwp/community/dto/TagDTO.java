package life.zwp.community.dto;
import lombok.Data;
import java.util.List;

/**
 * 标签
 */
@Data
public class TagDTO {
    private String categoryName;
    private String category;
    private List<String> tags;
}
