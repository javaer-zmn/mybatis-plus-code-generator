package org.rxy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZMN
 * @since 2023-04-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("efm_user")
public class EfmUser implements Serializable {
    @TableField("hobby")
    /**
     * 爱好
     */
    private String hobby;
    @TableField("age")
    /**
     * 年龄
     */
    private Integer age;
    @TableField("id")
    /**
     * 主键
     */
    private String id;
    @TableField("user_name")
    /**
     * 用户名
     */
    private String userName;
    @TableField("ext_info")
    /**
     * 拓展信息
     */
    private String extInfo;

    @TableField(exist = false)
    private String AddTimeStr;

}