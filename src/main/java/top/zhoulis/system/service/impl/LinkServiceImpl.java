package top.zhoulis.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zhoulis.common.utils.QueryPage;
import top.zhoulis.system.entity.SysLink;
import top.zhoulis.system.mapper.LinkMapper;
import top.zhoulis.system.service.LinkService;

@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, SysLink> implements LinkService {

    @Autowired
    private LinkMapper linkMapper;

    @Override
    public IPage<SysLink> list(SysLink sysLink, QueryPage queryPage) {
        IPage<SysLink> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<SysLink> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(sysLink.getName()), SysLink::getName, sysLink.getName());
        queryWrapper.orderByDesc(SysLink::getId);
        return linkMapper.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional
    public void add(SysLink sysLink) {
            linkMapper.insert(sysLink);
    }

    @Override
    @Transactional
    public void update(SysLink sysLink) {
        this.updateById(sysLink);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        linkMapper.deleteById(id);
    }

    private boolean exists(SysLink sysLink){
        LambdaQueryWrapper<SysLink> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysLink::getName, sysLink.getName());
        return linkMapper.selectList(queryWrapper).size() > 0 ? true : false;
    }


}
