package com.lin.model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
@ApiModel(value="\u7528\u6237\u5bf9\u8c61", description="\u8fd9\u662f\u7528\u6237\u5bf9\u8c61")
@Table(name="users")
@Data
public class User
implements Serializable {
    @ApiModelProperty(hidden=true)
    @Id
    private String id;
    @ApiModelProperty(value="\u7528\u6237\u540d", name="username", example="jack", required=true)
    private String username;
    @ApiModelProperty(value="\u5bc6\u7801", name="password", example="123456", required=true)
    private String password;
    @ApiModelProperty(hidden=true)
    @Column(name="face_image")
    private String faceImage;
    @ApiModelProperty(value="\u6635\u79f0", name="nickname", example="jack123")
    private String nickname;
    @ApiModelProperty(hidden=true)
    @Column(name="fans_counts")
    private Integer fansCounts;
    @ApiModelProperty(hidden=true)
    @Column(name="follow_counts")
    private Integer followCounts;
    @ApiModelProperty(hidden=true)
    @Column(name="receive_like_counts")
    private Integer receiveLikeCounts;
}
