package com.lin.model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
@ApiModel(value="\u89c6\u9891\u5bf9\u8c61", description="\u8fd9\u662f\u89c6\u9891\u5bf9\u8c61")
@Table(name="videos")
@Data
public class Video
implements Serializable {
    @ApiModelProperty(value="\u89c6\u9891id", name="id", example="10001", required=true)
    @Id
    private String id;
    @ApiModelProperty(value="\u53d1\u5e03\u8005id", name="userId", example="180425BNSR1CG0H0", required=true)
    @Column(name="user_id")
    private String userId;
    @ApiModelProperty(value="\u7528\u6237\u4f7f\u7528\u97f3\u9891\u7684id", name="audioId", example="18052674D26HH32P")
    @Column(name="audio_id")
    private String audioId;
    @ApiModelProperty(value="\u89c6\u9891\u63cf\u8ff0", name="videoDesc")
    @Column(name="video_desc")
    private String videoDesc;
    @Column(name="video_path")
    private String videoPath;
    @Column(name="video_seconds")
    private Float videoSeconds;
    @Column(name="video_width")
    private Integer videoWidth;
    @Column(name="video_height")
    private Integer videoHeight;
    @Column(name="cover_path")
    private String coverPath;
    @Column(name="like_counts")
    private Long likeCounts;
    private Integer status;
    @Column(name="create_time")
    private Date createTime;
}
