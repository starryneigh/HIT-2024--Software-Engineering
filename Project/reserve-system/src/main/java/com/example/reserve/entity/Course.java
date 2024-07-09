package com.example.reserve.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("course")
public class Course {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  private String name;

  private Integer teacherId;

  private String courseDate;

  private Integer selectedNum = 0;

  private Integer maxNum = 50;

  private String info;

}
