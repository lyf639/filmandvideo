import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
@Table(name="search_records")
@Data
public class SearchRecords
implements Serializable {
    @Id
    private String id;
    private String content;
}
