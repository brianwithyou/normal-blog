package com.brian.search.server.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.brian.search.server.service.SearchService;
import com.brian.web.api.dto.BlogDTO;
import com.xpand.starter.canal.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author Brian
 * @date 2023/5/31
 **/
@Slf4j
@Component
@CanalEventListener
public class EsCanalEventListener {

    @Resource
    private SearchService searchService;

    /**
     * InsertListerPoint 增加监听 只有增加后的数据
     *
     * @param eventType 当前操作的类型 增加数据类型
     * @param rowData   发生变更的一行数据
     *                  rowData.getAfterColumnsList：增加、修改
     *                  rowData.getBeforeColumnList:删除、修改
     */
    @InsertListenPoint
    public void onEventInsert(CanalEntry.EventType eventType, CanalEntry.RowData rowData) throws IOException {

        long blogId = 0;
        for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
            if (column.getName().equals("id")) {
                blogId = Long.parseLong(column.getValue());
                break;
            }
        }
        BlogDTO blog = searchService.insertById(blogId);
//        log.info("插入数据，Canal同步至es，id: {}, title: {}");
        log.info("插入数据，Canal同步至es，id: {}, title: {}", blog.getId(), blog.getTitle());
    }

    /**
     * 修改监听
     */
    @UpdateListenPoint
    public void onEventUpdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData) throws IOException {
        long blogId = 0;
        for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
            if (column.getName().equals("id")) {
                blogId = Long.parseLong(column.getValue());
                break;
            }
        }

        int status = 1;
        for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
            if (column.getName().equals("status")) {
                status = Integer.parseInt((column.getValue()));
                break;
            }
        }
        searchService.deleteById(blogId);
        if (status != 0) {
            BlogDTO blog = searchService.insertById(blogId);
            log.info("更新，Canal同步至es，id: {}, title: {}", blog.getId(), blog.getTitle());
            return;
        }
        log.info("删除数据，Canal同步至es，id: {}", blogId);
    }

    /**
     * 删除添加
     */
    @DeleteListenPoint
    public void onEventDelete(CanalEntry.EventType eventType, CanalEntry.RowData rowData) throws IOException {
        long blogId = 0;
        for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
            if (column.getName().equals("id")) {
                blogId = Long.parseLong(column.getValue());
                break;
            }
        }
        searchService.deleteById(blogId);
        log.info("删除，Canal同步至es，id: {}", blogId);
    }

    /***
     * 自定义数据修改监听
     * @param eventType eventType
     * @param rowData rowData
     */
    @ListenPoint(destination = "example", schema = "database_demo", table = {"tabl1", "t_blog"}, eventType = CanalEntry.EventType.UPDATE)
    public void onEventCustomUpdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        rowData.getAfterColumnsList().forEach((c) -> {
//            System.out.println("By--Annotation: " + c.getName() + " ::   " + c.getValue());
        });
    }
}
