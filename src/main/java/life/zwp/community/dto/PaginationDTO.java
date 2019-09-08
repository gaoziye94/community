package life.zwp.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页数据
 * @param <T>
 */
@Data
public class PaginationDTO<T> {
    /** 内容 */
    private List<T> content = new ArrayList<T>();
    /**是否展示上一页按钮**/
    private boolean showPrevPage;
    /**是否展示下一页按钮**/
    private boolean showNextPage;
    /**是否展示第一页按钮**/
    private boolean showFirstPage;
    /**是否展示最后一页按钮**/
    private boolean showEndPage;
    /**当前页**/
    private Integer page;
    /**总页数**/
    private Integer totalPage;
    /**总条数数**/
    private Integer totalCount;
    /**分页数据 存放分页数字**/
    private  List<Integer> pages = new ArrayList<Integer>();


    public void setPaginationDTO(Integer totalCount1, Integer cnpage, Integer size) {
        totalCount = totalCount1;
        page = cnpage;
//        总页数
        totalPage = totalCount % size ==0 ?( totalCount/size) : (totalCount/size +1);
//
        if(totalPage<=7){
            for (int i = 1; i <=totalPage ; i++) {
                pages.add(i);
            }
        } else {
            if(page <=3){
                for (int i = 1; i <=page+3 ; i++) {
                    pages.add(i);
                }
            } else {
                for (int i = page-3; i <=page+3; i++) {
                    if(i<=totalPage){
                        pages.add(i);
                    }
                }
            }

        }


//        是否展示上一页
        showPrevPage =( page == 1) ? false :true;
//        是否展示下一页
        showNextPage =( page == totalPage) ? false :true;
//        是否展示第一页
        showFirstPage = pages.contains(1) ? false :true;
//        是否展示最后一页
        showEndPage = pages.contains(totalPage) ? false :true;
    }
}
