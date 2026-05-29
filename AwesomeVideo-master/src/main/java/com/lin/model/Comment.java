package com.lin.model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
@ApiModel(value="\u8bc4\u8bba\u5bf9\u8c61", description="\u8fd9\u662f\u8bc4\u8bba\u5bf9\u8c61")
@Table(name="comments")
@Data
public class Comment
implements Serializable {
    @ApiModelProperty(value="\u8bc4\u8bbaId", name="videoId", example="1902057AAGAS0000", required=true)
    @Id
    private String id;
    @Column(name="father_comment_id")
    @ApiModelProperty(value="\u7236\u8bc4\u8bbaId", name="fatherCommentId", example="1805240G4G19R0PH", required=true)
    private String fatherCommentId;
    @ApiModelProperty(value="\u88ab\u8bc4\u8bba\u8005Id", name="toUserId", example="180930HXSB796AK4", required=true)
    @Column(name="to_user_id")
    private String toUserId;
    @ApiModelProperty(value="\u89c6\u9891Id", name="videoId", example="1902057AAGAS0B2W", required=true)
    @Column(name="video_id")
    private String videoId;
    @ApiModelProperty(value="\u7559\u8a00\u8005Id", name="fromUserId", example="180930DRXM99CKKP", required=true)
    @Column(name="from_user_id")
    private String fromUserId;
    @ApiModelProperty(hidden=true)
    @Column(name="create_time")
    private Date createTime;
    @ApiModelProperty(value="\u8bc4\u8bba\u5185\u5bb9", name="comment", example="\u8fd9\u4e2a\u89c6\u9891\u592a\u597d\u770b\u4e86", required=true)
    private String comment;
}
