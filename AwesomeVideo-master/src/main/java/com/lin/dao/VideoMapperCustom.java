import com.lin.model.vo.VideoVo;
import com.lin.utils.MyMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VideoMapperCustom
extends MyMapper<Video> {
    public List<VideoVo> queryAllVideos(@Param(value="videoDesc") String var1, @Param(value="userId") String var2);

    public void addVideoLikeCount(String var1);

    public void reduceVideoLikeCount(String var1);

    public List<VideoVo> queryMyLikeVideos(String var1);

    public List<VideoVo> queryMyFollowVideos(String var1);
}
