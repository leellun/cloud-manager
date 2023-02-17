package com.newland.mybatis.page;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 页面
 * Author: leell
 * Date: 2023/1/10 21:57:46
 */
@Data
public class PageEntity {
    /**
     * 页码
     */
    private Integer pageNo = 1;
    /**
     * 页大小
     */
    private Integer pageSize = 10;
    /**
     * 排序
     */
    private String order;
    /**
     * 排序
     */
    private boolean desc;

    public static PageEntity page(Integer pageNo, Integer pageSize) {
        PageEntity pageEntity = new PageEntity();
        pageEntity.setPageNo(pageNo);
        pageEntity.setPageSize(pageSize);
        return pageEntity;
    }

    public String getOrder() {
        if (StringUtils.isEmpty(this.order)) {
            return null;
        }
        String order = com.baomidou.mybatisplus.core.toolkit.StringUtils.toStringTrim(this.order);
        order = StrUtil.toUnderlineCase(order);
        return order;
    }
}
