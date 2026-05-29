import lombok.Data;
@Data
public class Bgm
implements Serializable {
    @Id
    private String id;
    private String author;
    private String name;
    private String path;
}
