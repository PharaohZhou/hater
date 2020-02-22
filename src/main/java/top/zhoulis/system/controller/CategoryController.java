package top.zhoulis.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zhoulis.common.controller.BaseController;
import top.zhoulis.common.utils.QueryPage;
import top.zhoulis.common.utils.R;
import top.zhoulis.system.entity.SysCategory;
import top.zhoulis.system.entity.SysTag;
import top.zhoulis.system.service.CategoryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
@Api(value = "CategoryController", tags = {"分类管理接口"})
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/findAll")
    public R<List<SysCategory>> findAll() {
        return new R<>(categoryService.list(new QueryWrapper<>()));
    }


    @GetMapping("/list")
    public R<Map<String,Object>> list(SysCategory sysCategory, QueryPage queryPage) {
        return new R<>(super.getData(categoryService.list(sysCategory, queryPage)));
    }

    @PostMapping
    public R save(@RequestBody SysCategory category) {
        try {
            categoryService.add(category);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @PutMapping
    public R update(@RequestBody SysCategory category) {
        try {
            categoryService.update(category);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id) {
        try {
            categoryService.delete(id);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
