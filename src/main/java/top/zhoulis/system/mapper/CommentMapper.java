package top.zhoulis.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.zhoulis.common.utils.QueryPage;
import top.zhoulis.system.entity.SysComment;

import java.util.List;

public interface CommentMapper extends BaseMapper<SysComment> {
    List<SysComment> findAll(@Param("state") String state, @Param("queryPage") QueryPage queryPage);
}
