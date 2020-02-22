package top.zhoulis.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import top.zhoulis.common.utils.QueryPage;
import top.zhoulis.system.entity.SysComment;

import java.util.List;
import java.util.Map;

public interface CommentService extends IService<SysComment> {

    IPage<SysComment> list(SysComment sysComment, QueryPage queryPage);

    void add(SysComment sysComment);

    void update(SysComment sysComment);

    void delete(Long id);

    List<SysComment> findAll();

    /**
     * 分页查询并过滤留言数据
     *
     * @param articleId 当前访问的文章ID
     * @param sort      分类，规定：sort=0表示文章详情页的评论信息；sort=1表示友链页的评论信息；sort=2表示关于我页的评论信息
     * @return
     */
    Map<String, Object> findCommentsList(QueryPage queryPage, String articleId, int sort);

}
