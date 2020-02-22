package top.zhoulis.system.controller;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zhoulis.common.annotation.Log;
import top.zhoulis.common.constants.CommonConstant;
import top.zhoulis.common.controller.BaseController;
import top.zhoulis.common.exception.GlobalException;
import top.zhoulis.common.utils.AddressUtil;
import top.zhoulis.common.utils.IpUtil;
import top.zhoulis.common.utils.QueryPage;
import top.zhoulis.common.utils.R;
import top.zhoulis.system.entity.SysComment;
import top.zhoulis.system.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/api/comment")
@Api(value = "CommentController", tags = {"评论管理接口"})
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/list")
    public R list(SysComment sysComment, QueryPage queryPage) {
        return new R<>(super.getData(commentService.list(sysComment, queryPage)));
    }

    @PostMapping
    @Log("保存评论")
    public R save(@RequestBody SysComment comment, HttpServletRequest request) {
        try {
            String ip = IpUtil.getIpAddr(request);
            comment.setTime(new Date());
            comment.setIp(ip);
            comment.setAddress(AddressUtil.getAddress(ip));
            String header = request.getHeader(CommonConstant.USER_AGENT);
            UserAgent userAgent = UserAgent.parseUserAgentString(header);
            Browser browser = userAgent.getBrowser();
            OperatingSystem operatingSystem = userAgent.getOperatingSystem();
            comment.setDevice(browser.getName() + "," + operatingSystem.getName());
            commentService.add(comment);
            return new R();
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @PutMapping
    @Log("更新评论")
    public R update(@RequestBody SysComment comment) {
        try {
            commentService.update(comment);
            return new R();
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Log("删除评论")
    public R delete(@PathVariable Long id) {
        try {
            commentService.delete(id);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
