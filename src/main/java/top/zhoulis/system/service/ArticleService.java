package top.zhoulis.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import top.zhoulis.common.utils.QueryPage;
import top.zhoulis.system.entity.SysArticle;
import top.zhoulis.system.entity.dto.ArchivesWithArticle;

import java.util.List;

public interface ArticleService extends IService<SysArticle> {

    /**
     * 分页查询
     * @param sysArticle
     * @param queryPage
     * @return
     */
    IPage<SysArticle> list(SysArticle sysArticle, QueryPage queryPage);

    void add(SysArticle sysArticle);

    void update(SysArticle sysArticle);

    void delete(Long id);

    List<SysArticle> findAll();

    SysArticle findById(Long id);

    /**
     * 查询按照时间归档的整合数据，
     * 格式：[{"2018-01", [{article},{article}...]}, {"2018-02", [{article}, {article}...]}]
     *
     * @return
     */
    List<ArchivesWithArticle> findArchives();
}
