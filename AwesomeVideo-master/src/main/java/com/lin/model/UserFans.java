import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
@Table(name="users_fans")
@Data
public class UserFans
implements Serializable {
    @Id
    private String id;
    @Column(name="user_id")
    private String userId;
    @Column(name="fan_id")
    private String fanId;
}
