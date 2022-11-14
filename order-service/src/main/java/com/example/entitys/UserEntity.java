package com.example.entitys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author <a href="https://www.fengwenyi.com?code">Erwin Feng</a>
 * @since 2022-05-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("user")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("address")
    private String address;

    @TableField("gender")
    private String gender;

    @TableField("email")
    private String email;

    @TableField("birthday")
    private LocalDate birthday;


}
