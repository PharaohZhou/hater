package top.zhoulis.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zhoulis.common.constants.CommonConstant;
import top.zhoulis.common.constants.SiteConstant;
import top.zhoulis.common.exception.GlobalException;
import top.zhoulis.common.utils.QueryPage;
import top.zhoulis.common.utils.TreeUtil;
import top.zhoulis.system.entity.SysComment;
import top.zhoulis.system.entity.dto.Tree;
import top.zhoulis.system.mapper.CommentMapper;
import top.zhoulis.system.service.CommentService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, SysComment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public IPage<SysComment> list(SysComment sysComment, QueryPage queryPage) {
        IPage<SysComment> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<SysComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(SysComment::getId);
        queryWrapper.like(StringUtils.isNotBlank(sysComment.getName()), SysComment::getName, sysComment.getName());
        queryWrapper.like(StringUtils.isNotBlank(sysComment.getUrl()), SysComment::getUrl, sysComment.getUrl());
        return commentMapper.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional
    public void add(SysComment sysComment) {
        commentMapper.insert(sysComment);
    }


    @Override
    @Transactional
    public void update(SysComment sysComment) {
        commentMapper.updateById(sysComment);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        commentMapper.deleteById(id);
    }

    @Override
    public List<SysComment> findAll() {
        return commentMapper.findAll(CommonConstant.DEFAULT_RELEASE_STATUS, new QueryPage(0, SiteConstant.COMMENT_PAGE_LIMIT));

    }

    @Override
    public Map<String, Object> findCommentsList(QueryPage queryPage, String articleId, int sort) {
        LambdaQueryWrapper<SysComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(articleId), SysComment::getArticleId, articleId);
        queryWrapper.eq(SysComment::getSort, sort);
        queryWrapper.orderByDesc(SysComment::getId);
        //先查询所有、再分页
        List<SysComment> list = commentMapper.selectList(queryWrapper);
        List<Tree<SysComment>> trees = new ArrayList<>();

        list.forEach(c -> {
            Tree<SysComment> tree = new Tree<>();
            tree.setId(c.getId());
            tree.setPId(c.getPId());
            tree.setAId(c.getArticleId());
            tree.setName(c.getName());
            tree.setContent(c.getContent());
            tree.setDevice(c.getDevice());
            tree.setSort(c.getSort());
            tree.setTarget(c.getCName());
            tree.setTime(c.getTime());
            tree.setUrl(c.getUrl());
            trees.add(tree);
        });
        Map<String, Object> map = new HashMap<>();

        try {
            List<Tree<SysComment>> treeList = TreeUtil.build(trees);

            if (treeList == null) {
                treeList = new ArrayList<>();
            }

            if (treeList.size() == 0) {
                map.put("rows", new ArrayList<>());
            } else {
                int start = (queryPage.getPage() - 1) * queryPage.getLimit();
                int end = queryPage.getPage() * queryPage.getLimit();
                if (end > treeList.size()) {
                    end = treeList.size();
                }
                map.put("rows", treeList.subList(start, end));
            }
            map.put("count", list.size());
            map.put("total", treeList.size());
            map.put("current", queryPage.getPage());
            map.put("pages", (int)Math.ceil((double)treeList.size() / (double)queryPage.getLimit()));
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
        return map;
    }
}
