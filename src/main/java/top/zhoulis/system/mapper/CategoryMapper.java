package top.zhoulis.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.zhoulis.system.entity.SysCategory;

import java.util.List;

public interface CategoryMapper extends BaseMapper<SysCategory> {
    List<SysCategory> findCategoryByArticleId(Long articleId);
}
