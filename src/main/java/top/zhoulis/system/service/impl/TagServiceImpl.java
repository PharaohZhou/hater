package top.zhoulis.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zhoulis.common.utils.QueryPage;
import top.zhoulis.system.entity.SysTag;
import top.zhoulis.system.mapper.TagMapper;
import top.zhoulis.system.service.ArticleTagService;
import top.zhoulis.system.service.TagService;

import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, SysTag> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public List<SysTag> findAll() {
        return tagMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public IPage<SysTag> list(SysTag sysTag, QueryPage queryPage) {
        IPage<SysTag> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<SysTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(sysTag.getName()), SysTag::getName, sysTag.getName());
        queryWrapper.orderByDesc(SysTag::getId);
        return tagMapper.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional
    public void add(SysTag tag) {
        if (!exists(tag)) {
            tagMapper.insert(tag);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        tagMapper.deleteById(id);
        //删除与文章关联的该标签的关联信息
        articleTagService.deleteByTagsId(id);
    }

    @Override
    @Transactional
    public void update(SysTag tag) {
        tagMapper.updateById(tag);
    }

    @Override
    public List<SysTag> findByArticleId(Long articleId) {
        return tagMapper.findTagByArticleId(articleId);
    }

    private boolean exists(SysTag tag) {
        LambdaQueryWrapper<SysTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysTag::getName, tag.getName());
        return tagMapper.selectList(queryWrapper).size() > 0 ? true : false;
    }
}
