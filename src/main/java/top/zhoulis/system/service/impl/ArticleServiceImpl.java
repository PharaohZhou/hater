package top.zhoulis.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zhoulis.common.constants.CommonConstant;
import top.zhoulis.common.constants.SiteConstant;
import top.zhoulis.common.exception.GlobalException;
import top.zhoulis.common.utils.QueryPage;
import top.zhoulis.system.entity.*;
import top.zhoulis.system.entity.dto.ArchivesWithArticle;
import top.zhoulis.system.mapper.ArticleMapper;
import top.zhoulis.system.service.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, SysArticle> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private TagService tagService;

    @Override
    public IPage<SysArticle> list(SysArticle sysArticle, QueryPage queryPage) {
        IPage<SysArticle> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<SysArticle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(SysArticle::getId);
        queryWrapper.like(StringUtils.isNotBlank(sysArticle.getTitle()), SysArticle::getTitle, sysArticle.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(sysArticle.getAuthor()), SysArticle::getAuthor, sysArticle.getAuthor());
        IPage<SysArticle> selectPage = articleMapper.selectPage(page, queryWrapper);
        findInit(selectPage.getRecords());
        return selectPage;
    }

    /**
     * 封装文章分类、标签数据
     *
     * @param list
     */
    private void findInit(List<SysArticle> list) {
        if (!list.isEmpty()) {
            list.forEach(sysArticle -> {
                List<SysCategory> sysCategoryList = categoryService.findByArticleId(sysArticle.getId());
                if (sysCategoryList.size() >0) {
                    sysArticle.setCategory(sysCategoryList.get(0).getName());
                }
                List<SysTag> tagList = tagService.findByArticleId(sysArticle.getId());
                List<String> stringList = new ArrayList<>();
                tagList.forEach(sysTag -> {
                    stringList.add(sysTag.getName());
                });
                sysArticle.setTags(tagList);
            });
        }
    }

    @Override
    @Transactional
    public void add(SysArticle sysArticle) {
        if (sysArticle.getState() == null) {
            sysArticle.setState(CommonConstant.DEFAULT_DRAFT_STATUS);
        }
        if (sysArticle.getPublishTime() == null && sysArticle.getState() == CommonConstant.DEFAULT_RELEASE_STATUS) {
            sysArticle.setPublishTime(new Date());
        }
        sysArticle.setAuthor(((SysUser)(SecurityUtils.getSubject().getPrincipal())).getUsername());
        sysArticle.setEditTime(new Date());
        sysArticle.setCreateTime(new Date());
        articleMapper.insert(sysArticle);
        sysArticle.setId(sysArticle.getId());
        this.updateArticleCategoryTags(sysArticle);
    }
    /**
     * 更新文章-分类-标签，三表间的关联
     *
     * @param sysArticle
     */
    private void updateArticleCategoryTags(SysArticle sysArticle) {
        if (sysArticle.getId() != null) {
            if (sysArticle.getCategory() != null) {
                articleCategoryService.deleteByArticleId(sysArticle.getId());
                SysCategory category = categoryService.getById(sysArticle.getCategory());
                if (category != null) {
                    articleCategoryService.add(new ArticleCategory(sysArticle.getId(), category.getId()));
                }
            }
            if (sysArticle.getTags() != null && sysArticle.getTags().size() > 0) {
                articleTagService.deleteByArticleId(sysArticle.getId());
                sysArticle.getTags().forEach(sysTag -> {
                    articleTagService.add(new ArticleTag(sysArticle.getId(), sysTag.getId()));
                });
            }
        }
    }

    @Override
    @Transactional
    public void update(SysArticle sysArticle) {
        if (sysArticle.getPublishTime() == null && sysArticle.getState().equals("1")) {
            sysArticle.setPublishTime(new Date());
        }
        articleMapper.updateById(sysArticle);
        updateArticleCategoryTags(sysArticle);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (id != null && id != 0) {
            articleMapper.deleteById(id);
            //删除文章-分类表的关联
            articleCategoryService.deleteByArticleId(id);
            //删除文章-标签表的关联
            articleTagService.deleteByArticleId(id);
        }
    }

    @Override
    public List<SysArticle> findAll() {
        LambdaQueryWrapper<SysArticle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(SysArticle::getId);
        queryWrapper.eq(SysArticle::getState, CommonConstant.DEFAULT_RELEASE_STATUS);
        IPage<SysArticle> page = new Page<>(0, 8);
        return articleMapper.selectPage(page, queryWrapper).getRecords();
    }

    @Override
    public SysArticle findById(Long id) {
        SysArticle sysArticle = articleMapper.selectById(id);
        List<SysArticle> sysArticleList = new ArrayList<>();
        sysArticleList.add(sysArticle);
        findInit(sysArticleList);
        return sysArticle;
    }

    @Override
    public List<ArchivesWithArticle> findArchives() {
        List<ArchivesWithArticle> archivesWithArticleList = new ArrayList<>();

        try {
            List<String> dates = articleMapper.findArchivesDates();
            dates.forEach(date -> {
                List<SysArticle> sysArticleList = articleMapper.findArchivesByDate(date);
                ArchivesWithArticle archivesWithArticle = new ArchivesWithArticle(date, sysArticleList);
                archivesWithArticleList.add(archivesWithArticle);
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
        return archivesWithArticleList;
    }

}
