package com.newland.mybatis.page;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.StringUtils;

/**
 * Author: leell
 * Date: 2023/1/10 21:57:16
 */
public class PageWrapper {
    public static <T> Page<T> wrapper(PageEntity pageEntity) {
        Page<T> page = new Page<>(pageEntity.getPageNo(), pageEntity.getPageSize());
        if (!StringUtils.isEmpty(pageEntity.getOrder())) {
            if (pageEntity.isDesc()) {
                page.addOrder(OrderItem.desc(pageEntity.getOrder()));
            } else {
                page.addOrder(OrderItem.asc(pageEntity.getOrder()));
            }
        }
        return page;
    }

}
