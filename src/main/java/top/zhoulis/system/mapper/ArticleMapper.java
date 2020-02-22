package top.zhoulis.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.zhoulis.system.entity.SysArticle;

import java.util.List;

public interface ArticleMapper extends BaseMapper<SysArticle> {
    List<String> findArchivesDates();

    List<SysArticle> findArchivesByDate(String date);
}
