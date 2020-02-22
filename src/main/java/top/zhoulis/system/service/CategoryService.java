package top.zhoulis.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import top.zhoulis.common.utils.QueryPage;
import top.zhoulis.system.entity.SysCategory;

import java.util.List;

public interface CategoryService extends IService<SysCategory> {
    IPage<SysCategory> list(SysCategory sysCategory, QueryPage queryPage);

    void add(SysCategory category);

    void update(SysCategory category);

    void delete(Long id);

    /**
     * 根据文章ID查询其关联的分类数据
     * @param articleId
     */
    List<SysCategory> findByArticleId(Long articleId);
}
