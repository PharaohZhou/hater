package top.zhoulis.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.zhoulis.system.entity.SysTag;

import java.util.List;


public interface TagMapper extends BaseMapper<SysTag> {
    List<SysTag> findTagByArticleId(Long articleId);
}
