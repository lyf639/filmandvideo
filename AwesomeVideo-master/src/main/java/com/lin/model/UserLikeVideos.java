import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
@Table(name="users_like_videos")
@Data
public class UserLikeVideos
implements Serializable {
    @Id
    private String id;
    @Column(name="user_id")
    private String userId;
    @Column(name="video_id")
    private String videoId;
}
