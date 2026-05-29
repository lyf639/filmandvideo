package com.lin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lin.dao.CommentMapper;
import com.lin.dao.CommentMapperCustom;
import com.lin.dao.SearchRecordsMapper;
import com.lin.dao.UserLikeVideosMapper;
import com.lin.dao.UserMapper;
import com.lin.dao.VideoMapper;
import com.lin.dao.VideoMapperCustom;
import com.lin.model.Comment;
import com.lin.model.SearchRecords;
import com.lin.model.UserLikeVideos;
import com.lin.model.Video;
import com.lin.model.vo.CommentVo;
import com.lin.model.vo.VideoVo;
import com.lin.service.VideoService;
import com.lin.utils.PagedResult;
import com.lin.utils.TimeAgoUtils;
import java.util.Date;
import java.util.List;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

@Service
@CacheConfig(cacheNames={"VideoServiceImpl"})
public class VideoServiceImpl
implements VideoService {
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private VideoMapperCustom videoMapperCustom;
    @Autowired
    private SearchRecordsMapper searchRecordsMapper;
    @Autowired
    private UserLikeVideosMapper userLikeVideosMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentMapperCustom commentMapperCustom;
    @Autowired
    private Sid sid;

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    @CacheEvict(allEntries=true)
    public String saveVideo(Video video) {
        VideoServiceImpl videoServiceImpl = this;
        String id = videoServiceImpl.sid.nextShort();
        video.setId(id);
        this.videoMapper.insertSelective(video);
        return id;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    @CacheEvict(allEntries=true)
    public PagedResult getAllVideos(Video video, Integer isSaveRecord, Integer currentPage, Integer pageSize) {
        String desc = video.getVideoDesc();
        String userId = video.getUserId();
        if (isSaveRecord != null && isSaveRecord == 1) {
            SearchRecords record = new SearchRecords();
            VideoServiceImpl videoServiceImpl = this;
            String recordId = videoServiceImpl.sid.nextShort();
            record.setId(recordId);
            record.setContent(desc);
            this.searchRecordsMapper.insert(record);
        }
        PageHelper.startPage((int)currentPage, (int)pageSize);
        List<VideoVo> list = this.videoMapperCustom.queryAllVideos(desc, userId);
        PageInfo pageInfo = new PageInfo(list);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(currentPage);
        pagedResult.setTotal(pageInfo.getPages());
        pagedResult.setRows(list);
        pagedResult.setRecords(pageInfo.getTotal());
        return pagedResult;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS)
    @Cacheable(key="targetClass + methodName")
    public List<String> getHotWords() {
        return this.searchRecordsMapper.getHotWords();
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    @CacheEvict(allEntries=true)
    public void userLikeVideo(String userId, String videoId, String videoCreatorId) {
        VideoServiceImpl videoServiceImpl = this;
        String likeId = videoServiceImpl.sid.nextShort();
        UserLikeVideos ulv = new UserLikeVideos();
        ulv.setId(likeId);
        ulv.setUserId(userId);
        ulv.setVideoId(videoId);
        this.userLikeVideosMapper.insert(ulv);
        this.videoMapperCustom.addVideoLikeCount(videoId);
        this.userMapper.addReceiveLikeCount(videoCreatorId);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    @CacheEvict(allEntries=true)
    public void userUnlikeVideo(String userId, String videoId, String videoCreatorId) {
        Example example = new Example(UserLikeVideos.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", (Object)userId);
        criteria.andEqualTo("videoId", (Object)videoId);
        this.userLikeVideosMapper.deleteByExample(example);
        this.videoMapperCustom.reduceVideoLikeCount(videoId);
        this.userMapper.reduceReceiveLikeCount(videoCreatorId);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    @CacheEvict(allEntries=true)
    public void saveComment(Comment comment) {
        VideoServiceImpl videoServiceImpl = this;
        String id = videoServiceImpl.sid.nextShort();
        comment.setId(id);
        comment.setCreateTime(new Date());
        this.commentMapper.insert(comment);
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS)
    @Cacheable(key="targetClass + methodName + #p0 + #p1 + #p3")
    public PagedResult getAllComments(String videoId, Integer page, Integer pageSize) {
        PageHelper.startPage((int)page, (int)pageSize);
        List<CommentVo> commentVoList = this.commentMapperCustom.queryComments(videoId);
        for (CommentVo commentVo : commentVoList) {
            String timeAgo = TimeAgoUtils.format(commentVo.getCreateTime());
            commentVo.setTimeAgoStr(timeAgo);
        }
        PageInfo pageList = new PageInfo(commentVoList);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(commentVoList);
        pagedResult.setPage(page);
        pagedResult.setRecords(pageList.getTotal());
        return pagedResult;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS)
    @Cacheable(key="targetClass + methodName + #p0 + #p1 + #p2")
    public PagedResult queryMyLikeVideos(String userId, Integer page, int pageSize) {
        PageHelper.startPage((int)page, (int)pageSize);
        List<VideoVo> list = this.videoMapperCustom.queryMyLikeVideos(userId);
        PageInfo pageList = new PageInfo(list);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setPage(page);
        pagedResult.setRecords(pageList.getTotal());
        pagedResult.setRows(list);
        return pagedResult;
    }

    @Override
    @Cacheable(key="targetClass + methodName + #p0 + #p1 + #p2")
    public PagedResult queryMyFollowVideos(String userId, Integer page, int pageSize) {
        PageHelper.startPage((int)page, (int)pageSize);
        List<VideoVo> list = this.videoMapperCustom.queryMyFollowVideos(userId);
        PageInfo pageList = new PageInfo(list);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setPage(page);
        pagedResult.setRows(list);
        pagedResult.setRecords(pageList.getTotal());
        return pagedResult;
    }
}
