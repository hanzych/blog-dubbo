package com.yfancy.service.vip.api.vo;

import com.yfancy.common.base.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class VipVo extends BaseEntity {

    private int id;

    private int type;

    private Date createTime;

    private Date endTime;

    private int userId;
}
