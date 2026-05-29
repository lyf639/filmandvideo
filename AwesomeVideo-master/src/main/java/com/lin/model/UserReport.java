package com.lin.model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
@ApiModel(value="\u7528\u6237\u4e3e\u62a5\u5bf9\u8c61", description="\u8fd9\u662f\u7528\u6237\u4e3e\u62a5\u5bf9\u8c61")
@Table(name="users_report")
@Data
public class UserReport
implements Serializable {
    @ApiModelProperty(hidden=true)
    @Id
    private String id;
    @ApiModelProperty(value="\u88ab\u4e3e\u62a5\u7684\u7528\u6237id", name="dealUserId", example="180930DRXM99CKKP", required=true)
    @Column(name="deal_user_id")
    private String dealUserId;
    @ApiModelProperty(value="\u88ab\u4e3e\u62a5\u7684\u89c6\u9891id", name="dealVideoId", example="190204HCS3P9539P", required=true)
    @Column(name="deal_video_id")
    private String dealVideoId;
    @ApiModelProperty(value="\u4e3e\u62a5\u6807\u9898", name="title", example="\u5e7f\u544a\u5783\u573e", required=true)
    private String title;
    @ApiModelProperty(value="\u4e3e\u62a5\u8be6\u60c5\u8bf4\u660e", name="title", example="\u5185\u5bb9\u5f71\u54cd\u8eab\u5fc3\u5065\u5eb7", required=true)
    private String content;
    @ApiModelProperty(value="\u4e3e\u62a5\u4eba\u7684id", name="userId", example="180425B0B3N6B25P", required=true)
    @Column(name="userid")
    private String userId;
    @ApiModelProperty(hidden=true)
    @Column(name="create_date")
    private Date createDate;
}
