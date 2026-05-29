package com.lin.controller;

import com.lin.controller.BaseController;
import com.lin.enums.VideoStatusEnum;
import com.lin.model.Comment;
import com.lin.model.Video;
import com.lin.service.BgmService;
import com.lin.service.VideoService;
import com.lin.utils.FFmpegUtils;
import com.lin.utils.JsonResult;
import com.lin.utils.PagedResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(value="\u89c6\u9891\u76f8\u5173\u4e1a\u52a1\u7684\u63a5\u53e3", tags={"\u89c6\u9891\u76f8\u5173\u4e1a\u52a1\u7684Controller"})
@RestController
@RequestMapping(value={"/video"})
public class VideoController
extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VideoController.class);
    @Autowired
    private BgmService bgmService;
    @Autowired
    private VideoService videoService;
    @Value("${video.upload.path}")
    private String videoUploadPath;
    @Value("${ffmpeg.path}")
    private String ffmpegPath;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @ApiOperation(value="\u4e0a\u4f20\u89c6\u9891", notes="\u4e0a\u4f20\u89c6\u9891\u7684\u63a5\u53e3")
    @ApiImplicitParams(value={@ApiImplicitParam(name="userId", value="\u7528\u6237id", required=true, dataType="String", paramType="form"), @ApiImplicitParam(name="bgmId", value="\u80cc\u666f\u97f3\u4e50id", dataType="String", paramType="form"), @ApiImplicitParam(name="videoSeconds", value="\u80cc\u666f\u97f3\u4e50\u64ad\u653e\u957f\u5ea6", required=true, dataType="double", paramType="form"), @ApiImplicitParam(name="videoWidth", value="\u89c6\u9891\u5bbd\u5ea6", required=true, dataType="int", paramType="form"), @ApiImplicitParam(name="videoHeight", value="\u89c6\u9891\u9ad8\u5ea6", required=true, dataType="int", paramType="form"), @ApiImplicitParam(name="desc", value="\u89c6\u9891\u63cf\u8ff0", dataType="String", paramType="form")})
    @PostMapping(value={"/upload"}, headers={"content-type=multipart/form-data"})
    public JsonResult upload(String userId, String bgmId, double videoSeconds, int videoWidth, int videoHeight, String desc, @ApiParam(value="\u4e0a\u4f20\u7684\u89c6\u9891", required=true) MultipartFile file) throws IOException {
        if (StringUtils.isBlank((CharSequence)userId)) {
            return JsonResult.errorMsg("\u7528\u6237id\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String finalVideoPath = "";
        String uploadPathDB = String.format("/%s/video", userId);
        FileOutputStream out = null;
        InputStream in = null;
        try {
            if (file != null) {
                String fileName = file.getOriginalFilename();
                if (StringUtils.isNoneBlank((CharSequence[])new CharSequence[]{fileName})) {
                    finalVideoPath = String.format("%s/%s/video/%s", videoUploadPath, userId, fileName);
                    uploadPathDB = String.format("/%s/video/%s", userId, fileName);
                    System.out.println(finalVideoPath);
                    File outFile = new File(finalVideoPath);
                    if (outFile.getParentFile() != null && !outFile.getParentFile().exists() && !outFile.getParentFile().isDirectory()) {
                        outFile.getParentFile().mkdirs();
                    }
                    out = new FileOutputStream(outFile);
                    in = file.getInputStream();
                    IOUtils.copy((InputStream)in, (OutputStream)out);
                }
            } else {
                JsonResult.errorMsg("\u4e0a\u4f20\u6587\u4ef6\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u4e0a\u4f20\u5931\u8d25\uff01");
            }
        }
        catch (IOException e) {
            JsonResult outFile = JsonResult.errorMsg("\u4e0a\u4f20\u6587\u4ef6\u5931\u8d25\uff01");
            return outFile;
        }
        finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
        System.out.println("chengle");
        FFmpegUtils ffmpegUtils = new FFmpegUtils(ffmpegPath);
        ffmpegUtils.createVideoThumbnail(finalVideoPath);
        LOGGER.info("uploadPathDB = {}", (Object)uploadPathDB);
        LOGGER.info("coverPath = {}", (Object)(uploadPathDB + ".jpg"));
        LOGGER.info("finalVideoPath {}= ", (Object)finalVideoPath);
        Video video = new Video();
        video.setAudioId(bgmId);
        video.setUserId(userId);
        video.setVideoSeconds(Float.valueOf((float)videoSeconds));
        video.setVideoWidth(videoWidth);
        video.setVideoHeight(videoHeight);
        video.setVideoDesc(desc);
        video.setVideoPath(uploadPathDB);
        video.setStatus(VideoStatusEnum.SUCCESS.value);
        video.setCoverPath(uploadPathDB + ".jpg");
        video.setCreateTime(new Date());
        String videoId = this.videoService.saveVideo(video);
        return JsonResult.ok(videoId);
    }

    @ApiOperation(value="\u5206\u9875\u548c\u641c\u7d22\u67e5\u8be2\u89c6\u9891\u5217\u8868", notes="\u5206\u9875\u548c\u641c\u7d22\u67e5\u8be2\u89c6\u9891\u5217\u8868\u7684\u63a5\u53e3")
    @ApiImplicitParams(value={@ApiImplicitParam(name="isSaveRecord", value="\u662f\u5426\u4fdd\u5b58\u8bb0\u5f55", required=true, dataType="int", paramType="query"), @ApiImplicitParam(name="currentPage", value="\u5f53\u524d\u9875\u6570", dataType="int", paramType="query"), @ApiImplicitParam(name="pageSize", value="\u6bcf\u9875\u6761\u6570", dataType="int", paramType="query")})
    @PostMapping(value={"/showAll"})
    public JsonResult showAll(@ApiParam(value="\u89c6\u9891\u5bf9\u8c61") @RequestBody Video video, Integer isSaveRecord, Integer currentPage, Integer pageSize) {
        if (currentPage == null) {
            currentPage = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        PagedResult result = this.videoService.getAllVideos(video, isSaveRecord, currentPage, pageSize);
        return JsonResult.ok(result);
    }

    @ApiOperation(value="\u83b7\u53d6\u6211\u6536\u85cf(\u70b9\u8d5e)\u8fc7\u7684\u89c6\u9891\u5217\u8868", notes="\u6211\u6536\u85cf(\u70b9\u8d5e)\u8fc7\u7684\u89c6\u9891\u5217\u8868\u7684\u63a5\u53e3")
    @ApiImplicitParams(value={@ApiImplicitParam(name="userId", value="\u7528\u6237id", required=true, dataType="int", paramType="query"), @ApiImplicitParam(name="page", value="\u5f53\u524d\u9875\u6570", dataType="int", paramType="query")})
    @PostMapping(value={"/showMyLike"})
    public JsonResult showMyLike(String userId, Integer page) {
        if (StringUtils.isBlank((CharSequence)userId)) {
            return JsonResult.errorMsg("\u7528\u6237id\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (page == null) {
            page = 1;
        }
        int pageSize = 6;
        PagedResult result = this.videoService.queryMyLikeVideos(userId, page, pageSize);
        return JsonResult.ok(result);
    }

    @ApiOperation(value="\u83b7\u53d6\u5173\u6ce8\u7684\u4eba\u53d1\u7684\u89c6\u9891\u5217\u8868", notes="\u83b7\u53d6\u5173\u6ce8\u7684\u4eba\u53d1\u7684\u89c6\u9891\u5217\u8868\u7684\u63a5\u53e3")
    @ApiImplicitParams(value={@ApiImplicitParam(name="userId", value="\u7528\u6237id", required=true, dataType="int", paramType="query"), @ApiImplicitParam(name="page", value="\u5f53\u524d\u9875\u6570", dataType="int", paramType="query")})
    @PostMapping(value={"/showMyFollow"})
    public JsonResult showMyFollow(String userId, Integer page) {
        if (StringUtils.isBlank((CharSequence)userId)) {
            return JsonResult.errorMsg("\u7528\u6237id\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (page == null) {
            page = 1;
        }
        int pageSize = 6;
        PagedResult result = this.videoService.queryMyFollowVideos(userId, page, pageSize);
        return JsonResult.ok(result);
    }

    @ApiOperation(value="\u83b7\u53d6\u70ed\u641c\u8bcd", notes="\u83b7\u53d6\u70ed\u641c\u8bcd\u7684\u63a5\u53e3")
    @PostMapping(value={"/hot"})
    public JsonResult hot() {
        return JsonResult.ok(this.videoService.getHotWords());
    }

    @ApiOperation(value="\u7528\u6237\u7ed9\u89c6\u9891\u70b9\u8d5e", notes="\u7528\u6237\u7ed9\u89c6\u9891\u70b9\u8d5e\u7684\u63a5\u53e3")
    @PostMapping(value={"/userLike"})
    public JsonResult userLike(String userId, String videoId, String videoCreatorId) {
        this.videoService.userLikeVideo(userId, videoId, videoCreatorId);
        return JsonResult.ok();
    }

    @ApiOperation(value="\u7528\u6237\u7ed9\u89c6\u9891\u53d6\u6d88\u70b9\u8d5e", notes="\u7528\u6237\u7ed9\u89c6\u9891\u53d6\u6d88\u70b9\u8d5e\u7684\u63a5\u53e3")
    @PostMapping(value={"/userUnLike"})
    public JsonResult userUnLike(String userId, String videoId, String videoCreatorId) {
        this.videoService.userUnlikeVideo(userId, videoId, videoCreatorId);
        return JsonResult.ok();
    }

    @ApiOperation(value="\u4fdd\u5b58\u7528\u6237\u8bc4\u8bba", notes="\u4fdd\u5b58\u7528\u6237\u8bc4\u8bba\u7684\u63a5\u53e3")
    @ApiImplicitParams(value={@ApiImplicitParam(name="fatherCommentId", value="\u7236\u8bc4\u8bbaid", required=true, dataType="String", paramType="query"), @ApiImplicitParam(name="toUserId", value="\u88ab\u8bc4\u8bba\u7528\u6237id", required=true, dataType="String", paramType="query")})
    @PostMapping(value={"/saveComment"})
    public JsonResult saveComment(@RequestBody @ApiParam(value="\u8bc4\u8bba\u5bf9\u8c61", required=true) Comment comment, String fatherCommentId, String toUserId) {
        comment.setFatherCommentId(fatherCommentId);
        comment.setToUserId(toUserId);
        this.videoService.saveComment(comment);
        return JsonResult.ok();
    }

    @ApiOperation(value="\u83b7\u53d6\u89c6\u9891\u8bc4\u8bba", notes="\u83b7\u53d6\u89c6\u9891\u8bc4\u8bba\u7684\u63a5\u53e3")
    @ApiImplicitParams(value={@ApiImplicitParam(name="videoId", value="\u89c6\u9891id", required=true, dataType="String", paramType="query"), @ApiImplicitParam(name="page", value="\u5f53\u524d\u9875\u6570", dataType="String", paramType="query"), @ApiImplicitParam(name="pageSize", value="\u6bcf\u9875\u8bc4\u8bba\u6570", dataType="String", paramType="query")})
    @PostMapping(value={"/getVideoComments"})
    public JsonResult getVideoComments(String videoId, Integer page, Integer pageSize) {
        if (StringUtils.isBlank((CharSequence)videoId)) {
            return JsonResult.ok();
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        PagedResult list = this.videoService.getAllComments(videoId, page, pageSize);
        return JsonResult.ok(list);
    }
}
