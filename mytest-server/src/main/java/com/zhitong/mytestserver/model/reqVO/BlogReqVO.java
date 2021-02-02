package com.zhitong.mytestserver.model.reqVO;

import lombok.Data;

/**
 * @author : subs
 * @Project: zt-parent
 * @Package com.zhitong.mytestserver.model.reqVO
 * @Description:
 * @date Date : 2021年02月01日 13:51
 */
@Data
public class BlogReqVO  {

    private String title;

    private String content;

    private String tagId;

    private Integer pageSize;

    private Integer currentPage;
}
