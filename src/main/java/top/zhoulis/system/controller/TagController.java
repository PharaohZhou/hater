package top.zhoulis.system.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zhoulis.common.annotation.Log;
import top.zhoulis.common.controller.BaseController;
import top.zhoulis.common.exception.GlobalException;
import top.zhoulis.common.utils.QueryPage;
import top.zhoulis.common.utils.R;
import top.zhoulis.system.entity.SysTag;
import top.zhoulis.system.service.TagService;

import java.util.Map;

@RestController
@RequestMapping("/api/tag")
@Api(value = "TagController", tags = {"标签管理接口"})
public class TagController extends BaseController {

    @Autowired
    private TagService tagService;

    @GetMapping("/findAll")
    public R newList() {
        return new R<>(tagService.findAll());
    }

    @GetMapping("/list")
    public R<Map<String, Object>> list(SysTag sysTag, QueryPage queryPage) {
        return new R<>(super.getData(tagService.list(sysTag, queryPage)));
    }

    @PostMapping
    @Log("新增标签")
    public R save(@RequestBody SysTag tag) {
        try {
            tagService.add(tag);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @PutMapping
    @Log("更新标签")
    public R update(@RequestBody SysTag tag) {
        try {
            tagService.update(tag);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Log("删除标签")
    public R delete(@PathVariable Long id) {
        try {
            tagService.delete(id);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }

    }
}
