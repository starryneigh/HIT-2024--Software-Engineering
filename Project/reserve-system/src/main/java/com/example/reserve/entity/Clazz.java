package com.example.reserve.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("clazz")
public class Clazz {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  private String name;

  private String info;

}
