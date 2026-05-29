import java.io.Serializable;
import lombok.Data;
@ApiModel(value="\u53d1\u5e03\u8005\u4e0e\u89c6\u9891\u89c6\u56fe\u5bf9\u8c61", description="\u8fd9\u662f\u53d1\u5e03\u8005\u4e0e\u89c6\u9891\u89c6\u56fe\u5bf9\u8c61")
@Data
public class PublisherVideoVo
implements Serializable {
    private UserVo publisher;
    private boolean userLikeVideo;
}
