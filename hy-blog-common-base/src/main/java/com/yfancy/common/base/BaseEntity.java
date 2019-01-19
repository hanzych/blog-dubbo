package com.yfancy.common.base;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Date;


@Data
@Slf4j
public class BaseEntity implements Serializable {

    private Date editTime;





}
