package life.zwp.community.cache;

import life.zwp.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 标签写死，应该从数据库获取
 */
public class TagCache {
    /**
     * 模拟标签
     * @return
     */
    public static List<TagDTO> getTags(){
        List<TagDTO> tagCategorys = new ArrayList<>();
        TagDTO tagCategory = new TagDTO();
        tagCategory.setCategoryName("开发语言");
        tagCategory.setCategory("language");
        tagCategory.setTags(Arrays.asList("Java","Js","Php"));
        tagCategorys.add(tagCategory);
        TagDTO tagCategory2 = new TagDTO();
        tagCategory2.setCategoryName("平台框架");
        tagCategory2.setCategory("frame");
        tagCategory2.setTags(Arrays.asList("Spring","Bootstrap","Vue","Mybatis"));
        tagCategorys.add(tagCategory2);
        return tagCategorys;
    }

    /**
     * 判断输入的标签，在不在标签库中
     * @param tags
     * @return
     */
    public static String filterInvalid(String tags){
        //传递的标签
        String[] split = StringUtils.split(tags, "，");
        //标签库中的标签
        List<TagDTO> tagDTOS = getTags();
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String inValid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining("，"));
        return inValid;
    }

}
