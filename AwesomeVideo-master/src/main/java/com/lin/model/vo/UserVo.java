import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
@ApiModel(value="\u7528\u6237\u89c6\u56fe\u5bf9\u8c61", description="\u8fd9\u662f\u7528\u6237\u89c6\u56fe\u5bf9\u8c61")
@Data
public class UserVo
implements Serializable {
    @ApiModelProperty(hidden=true)
    private String id;
    @ApiModelProperty(hidden=true)
    private String userToken;
    @ApiModelProperty(value="\u7528\u6237\u540d", name="username", example="jack", required=true)
    private String username;
    @ApiModelProperty(value="\u5bc6\u7801", name="password", example="123456", required=true)
    @JsonIgnore
    private String password;
    @ApiModelProperty(value="\u662f\u5426\u5173\u6ce8\u8be5\u7528\u6237", name="username", example="jack", required=true)
    private boolean isFollow;
    @ApiModelProperty(hidden=true)
    private String faceImage;
    @ApiModelProperty(value="\u6635\u79f0", name="nickname", example="jack123")
    private String nickname;
    @ApiModelProperty(hidden=true)
    private Integer fansCounts;
    @ApiModelProperty(hidden=true)
    private Integer followCounts;
    @ApiModelProperty(hidden=true)
    private Integer receiveLikeCounts;
}
