public interface VideoService {
    public String saveVideo(Video var1);

    public PagedResult getAllVideos(Video var1, Integer var2, Integer var3, Integer var4);

    public List<String> getHotWords();

    public void userLikeVideo(String var1, String var2, String var3);

    public void userUnlikeVideo(String var1, String var2, String var3);

    public void saveComment(Comment var1);

    public PagedResult getAllComments(String var1, Integer var2, Integer var3);

    public PagedResult queryMyLikeVideos(String var1, Integer var2, int var3);

    public PagedResult queryMyFollowVideos(String var1, Integer var2, int var3);
}
