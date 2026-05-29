public interface CommentMapperCustom
extends MyMapper<Comment> {
    public List<CommentVo> queryComments(String var1);
}
