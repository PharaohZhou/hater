package top.zhoulis.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zhoulis.system.entity.ArticleCategory;
import top.zhoulis.system.mapper.ArticleCategoryMapper;
import top.zhoulis.system.service.ArticleCategoryService;

@Service
public class ArticleCategoryServiceImpl extends ServiceImpl<ArticleCategoryMapper,ArticleCategory> implements ArticleCategoryService {

    @Autowired
    private ArticleCategoryMapper articleCategoryMapper;

    @Override
    @Transactional
    public void deleteByArticleId(Long id) {
        LambdaQueryWrapper<ArticleCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleCategory::getArticleId, id);
        articleCategoryMapper.delete(queryWrapper);
    }

    @Override
    @Transactional
    public void add(ArticleCategory articleCategory) {
        if (!exists(articleCategory)) {
            articleCategoryMapper.insert(articleCategory);
        }
    }

    private boolean exists(ArticleCategory articleCategory) {
        LambdaQueryWrapper<ArticleCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleCategory::getArticleId, articleCategory.getArticleId());
        queryWrapper.eq(ArticleCategory::getCategoryId, articleCategory.getCategoryId());
        return articleCategoryMapper.selectList(queryWrapper).size() > 0 ? true : false;
    }
}
