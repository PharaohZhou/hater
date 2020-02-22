package top.zhoulis.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zhoulis.system.entity.ArticleTag;
import top.zhoulis.system.mapper.ArticleTagMapper;
import top.zhoulis.system.service.ArticleTagService;

@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    @Transactional
    public void deleteByArticleId(Long articleId) {
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, articleId);
        articleTagMapper.delete(queryWrapper);
    }

    @Override
    public void add(ArticleTag articleTag) {
        if (!exists(articleTag)) {
            articleTagMapper.insert(articleTag);
        }
    }

    @Override
    @Transactional
    public void deleteByTagsId(Long id) {
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getTagId, id);
        articleTagMapper.delete(queryWrapper);
    }

    private boolean exists(ArticleTag articleTag) {
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, articleTag.getArticleId());
        queryWrapper.eq(ArticleTag::getTagId, articleTag.getTagId());
        return articleTagMapper.selectList(queryWrapper).size() > 0 ? true : false;
    }
}
