package top.zhoulis.system.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zhoulis.common.annotation.Log;
import top.zhoulis.common.controller.BaseController;
import top.zhoulis.common.utils.QueryPage;
import top.zhoulis.common.utils.R;
import top.zhoulis.system.entity.SysLink;
import top.zhoulis.system.service.LinkService;

@RestController
@RequestMapping("/api/link")
@Api(value = "LinkController", tags = {"友链管理接口"})
public class LinkController extends BaseController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public R list(SysLink sysLink, QueryPage queryPage) {
        return new R<>(super.getData(linkService.list(sysLink, queryPage)));
    }

    @PostMapping
    @Log("新增友链")
    public R save(@RequestBody SysLink sysLink) {
        try {
            linkService.add(sysLink);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @PutMapping
    @Log("更新友链")
    public R update(@RequestBody SysLink sysLink) {
        try {
            linkService.update(sysLink);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    @Log("删除友链")
    public R delete(@PathVariable Long id) {
        try {
            linkService.delete(id);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
