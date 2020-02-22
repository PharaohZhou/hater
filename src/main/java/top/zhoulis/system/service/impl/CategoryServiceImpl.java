package top.zhoulis.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zhoulis.common.utils.QueryPage;
import top.zhoulis.system.entity.SysCategory;
import top.zhoulis.system.mapper.CategoryMapper;
import top.zhoulis.system.service.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, SysCategory> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public IPage<SysCategory> list(SysCategory sysCategory, QueryPage queryPage) {
        IPage<SysCategory> page = new Page<>(queryPage.getPage(),queryPage.getLimit());
        LambdaQueryWrapper<SysCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(SysCategory::getId);
        queryWrapper.like(StringUtils.isNotBlank(sysCategory.getName()), SysCategory::getName, sysCategory.getName());
        return categoryMapper.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional
    public void add(SysCategory category) {
        if (!exists(category)) {
            categoryMapper.insert(category);
        }
    }

    @Override
    @Transactional
    public void update(SysCategory category) {
        categoryMapper.updateById(category);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        categoryMapper.deleteById(id);
    }

    @Override
    public List<SysCategory> findByArticleId(Long articleId) {
        return categoryMapper.findCategoryByArticleId(articleId);
    }

    private boolean exists(SysCategory category) {
        LambdaQueryWrapper<SysCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysCategory::getName, category.getName());
        return categoryMapper.selectList(queryWrapper).size() > 0 ? true : false;
    }
}
