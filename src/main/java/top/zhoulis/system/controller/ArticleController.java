package top.zhoulis.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zhoulis.common.annotation.Log;
import top.zhoulis.common.controller.BaseController;
import top.zhoulis.common.utils.QueryPage;
import top.zhoulis.common.utils.R;
import top.zhoulis.system.entity.SysArticle;
import top.zhoulis.system.entity.dto.ArchivesWithArticle;
import top.zhoulis.system.service.ArticleService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/article")
@Api(value = "ArticleController", tags = {"文章管理接口"})
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;

    /**
     * 查询文章总数量
     */
    @GetMapping("/count")
    public R<Integer> findAllCount(){
        return new R<>(articleService.count(new QueryWrapper<SysArticle>()));
    }

    /**
     * 分页查询文章
     */
    @GetMapping("/list")
    public R<Map<String, Object>> findByPage(SysArticle sysArticle, QueryPage queryPage) {
        return new R<>(super.getData(articleService.list(sysArticle, queryPage)));
    }

    @GetMapping(value = "/archives")
    public R<List<ArchivesWithArticle>> findArchives() {
        return new R<>(articleService.findArchives());
    }

    @PostMapping
    @Log("新增文章")
    public R save(@RequestBody SysArticle sysArticle) {
        try {
            articleService.add(sysArticle);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @PutMapping
    @Log("更新文章")
    public R update(@RequestBody SysArticle sysArticle) {
        try {
            articleService.update(sysArticle);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    @Log("删除文章")
    public R delete(@PathVariable Long id) {
        try {
            articleService.delete(id);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
