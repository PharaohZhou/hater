package top.zhoulis.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import top.zhoulis.common.utils.QueryPage;
import top.zhoulis.system.entity.SysTag;

import java.util.List;

public interface TagService extends IService<SysTag> {

    /**
     * 查询所有，为博客前台服务，查询并封装每个标签的文章数量
     *
     * @return
     */
    List<SysTag> findAll();

    /**
     * 分页查询
     *
     * @return
     */
    IPage<SysTag> list(SysTag sysTag, QueryPage queryPage);

    /**
     * 新增标签
     * @param tag
     */
    void add(SysTag tag);

    /**
     * 删除标签
     * @param id
     */
    void delete(Long id);

    /**
     * 更新标签
     * @param tag
     */
    void update(SysTag tag);

    /**
     * 根据文章id查找关联的分类数据
     * @param articleId
     * @return
     */
    List<SysTag> findByArticleId(Long articleId);
}
