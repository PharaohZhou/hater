package top.zhoulis.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.zhoulis.system.entity.ArticleCategory;

public interface ArticleCategoryService extends IService<ArticleCategory> {
    /**
     * 根据文章id删除分类
     * @param id
     */
    void deleteByArticleId(Long id);

    void add(ArticleCategory articleCategory);
}
