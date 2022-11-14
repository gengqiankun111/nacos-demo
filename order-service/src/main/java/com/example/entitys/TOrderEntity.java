package com.example.entitys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName("t_order")
public class TOrderEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("id")
    private Integer id;

    @TableField("order_no")
    private String orderNo;

    @TableField("order_address")
    private String orderAddress;

    @TableField("total_price")
    private BigDecimal totalPrice;


}
