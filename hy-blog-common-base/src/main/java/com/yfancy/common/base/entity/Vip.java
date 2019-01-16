package com.yfancy.common.base.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Vip {

    private int id;

    private int type;

    private Date createTime;

    private Date endTime;

    private int userId;
}
