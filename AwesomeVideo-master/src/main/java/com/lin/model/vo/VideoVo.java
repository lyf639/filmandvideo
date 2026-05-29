import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;
import lombok.Data;
@ApiModel(value="\u89c6\u9891\u89c6\u56fe\u5bf9\u8c61", description="\u8fd9\u662f\u89c6\u9891\u89c6\u56fe\u5bf9\u8c61")
@Data
public class VideoVo
implements Serializable {
    @Id
    private String id;
    private String userId;
    private String audioId;
    private String videoDesc;
    private String videoPath;
    private Float videoSeconds;
    private Integer videoWidth;
    private Integer videoHeight;
    private String coverPath;
    private Long likeCounts;
    private Integer status;
    private Date createTime;
    private String faceImage;
    private String nickName;
}
