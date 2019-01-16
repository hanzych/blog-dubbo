package com.yfancy.service.user.api.vo;

import com.yfancy.common.base.BaseEntity;
import lombok.Data;


@Data
public class UserVo extends BaseEntity {

    private int id;

    private String name;

    private int age;

    private int sex;

    private String pic;

    private int vip;

    private String nickName;

}
