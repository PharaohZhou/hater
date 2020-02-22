package top.zhoulis.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 友链实体
 * @author zhou
 */
@Data
@TableName("tb_link")
public class SysLink {

    @TableId(type = IdType.AUTO)
    private Long id;

    @NotNull
    private String name;

    private String url;

}
