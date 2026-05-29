package com.lin.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class Reports implements Serializable {
    private String id; // 举报id
    private String title; // 标题
    private String content; // 详情
    private Date createDate; // 创建日期

    private String dealUsername; // 被处理用户名

    private String dealVideoId; // 被处理的视频id
    private String videoDesc; // 视频描述
    private String videoPath; // 视频路径
    private Integer status; // 视频状态

    private String submitUsername; // 举报者用户名
}
