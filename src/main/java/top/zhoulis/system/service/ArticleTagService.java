package top.zhoulis.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.zhoulis.system.entity.ArticleTag;

public interface ArticleTagService extends IService<ArticleTag> {
    /**
     * 根据文章ID删除
     *
     * @param articleId
     */
    void deleteByArticleId(Long articleId);

    /**
     * 新增关联关系
     *
     * @param articleTag
     */
    void add(ArticleTag articleTag);

    void deleteByTagsId(Long id);
}
