package com.lin.controller;

import com.lin.controller.BaseController;
import com.lin.model.User;
import com.lin.model.UserReport;
import com.lin.model.vo.PublisherVideoVo;
import com.lin.model.vo.UserVo;
import com.lin.service.UserService;
import com.lin.utils.JsonResult;
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
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(value="\u7528\u6237\u76f8\u5173\u4e1a\u52a1\u7684\u63a5\u53e3", tags={"\u7528\u6237\u76f8\u5173\u4e1a\u52a1\u7684Controller"})
@RestController
@RequestMapping(value={"/user"})
public class UserController
extends BaseController {
    @Autowired
    private UserService userService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @ApiOperation(value="\u7528\u6237\u4e0a\u4f20\u5934\u50cf", notes="\u7528\u6237\u4e0a\u4f20\u5934\u50cf\u7684\u63a5\u53e3")
    @ApiImplicitParam(name="userId", value="\u7528\u6237id", required=true, dataType="String", paramType="query")
    @PostMapping(value={"/uploadFace"}, headers={"content-type=multipart/form-data"})
    public JsonResult uploadFace(String userId, @ApiParam(value="\u4e0a\u4f20\u7684\u56fe\u7247", required=true) MultipartFile file) {
        if (StringUtils.isBlank((CharSequence)userId)) {
            return JsonResult.errorMsg("\u7528\u6237id\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String uploadPathDB = null;
        FileOutputStream out = null;
        InputStream in = null;
        try {
            if (file != null) {
                String fileName = file.getOriginalFilename();
                if (StringUtils.isNoneBlank((CharSequence[])new CharSequence[]{fileName})) {
                    String finalFacePath = String.format("%s/%s/face/%s", fileBase, userId, fileName);
                    uploadPathDB = String.format("/%s/face/%s", userId, fileName);
                    File outFile = new File(finalFacePath);
                    if (outFile.getParentFile() != null && !outFile.getParentFile().isDirectory()) {
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
            JsonResult jsonResult = JsonResult.errorMsg("\u4e0a\u4f20\u6587\u4ef6\u5931\u8d25\uff01");
            return jsonResult;
        }
        finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
        User user = new User();
        user.setId(userId);
        user.setFaceImage(uploadPathDB);
        this.userService.updateUserInfo(user);
        return JsonResult.ok(uploadPathDB);
    }

    @ApiOperation(value="\u67e5\u8be2\u7528\u6237\u4fe1\u606f", notes="\u67e5\u8be2\u7528\u6237\u4fe1\u606f\u7684\u63a5\u53e3")
    @ApiImplicitParams(value={@ApiImplicitParam(name="userId", value="\u7528\u6237id", required=true, dataType="String", paramType="query"), @ApiImplicitParam(name="fanId", value="\u7c89\u4e1did", required=true, dataType="String", paramType="query")})
    @PostMapping(value={"/query"})
    public JsonResult query(String userId, String fanId) {
        if (StringUtils.isBlank((CharSequence)userId)) {
            return JsonResult.errorMsg("\u7528\u6237id\u4e0d\u80fd\u4e3a\u7a7a");
        }
        User userInfo = this.userService.queryUserInfo(userId);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties((Object)userInfo, (Object)userVo);
        userVo.setFollow(this.userService.queryIfFollow(userId, fanId));
        return JsonResult.ok(userVo);
    }

    @ApiOperation(value="\u67e5\u8be2\u53d1\u5e03\u8005\u4fe1\u606f", notes="\u67e5\u8be2\u53d1\u5e03\u8005\u4fe1\u606f\u7684\u63a5\u53e3")
    @ApiImplicitParams(value={@ApiImplicitParam(name="loginUserId", value="\u767b\u9646\u7528\u6237id", required=true, dataType="String", paramType="query"), @ApiImplicitParam(name="videoId", value="\u89c6\u9891id", required=true, dataType="String", paramType="query"), @ApiImplicitParam(name="publishUserId", value="\u53d1\u5e03\u8005id", required=true, dataType="String", paramType="query")})
    @PostMapping(value={"/queryPublisher"})
    public JsonResult queryPublisher(String loginUserId, String videoId, String publishUserId) {
        if (StringUtils.isBlank((CharSequence)publishUserId)) {
            return JsonResult.errorMsg("\u53d1\u5e03\u8005id\u4e0d\u80fd\u4e3a\u7a7a");
        }
        User userInfo = this.userService.queryUserInfo(publishUserId);
        UserVo publisher = new UserVo();
        BeanUtils.copyProperties((Object)userInfo, (Object)publisher);
        boolean userLikeVideo = this.userService.isUserLikeVideo(loginUserId, videoId);
        PublisherVideoVo publisherVideoVo = new PublisherVideoVo();
        publisherVideoVo.setPublisher(publisher);
        publisherVideoVo.setUserLikeVideo(userLikeVideo);
        return JsonResult.ok(publisherVideoVo);
    }

    @ApiOperation(value="\u5173\u6ce8\u7528\u6237", notes="\u5173\u6ce8\u7528\u6237\u7684\u63a5\u53e3")
    @ApiImplicitParams(value={@ApiImplicitParam(name="userId", value="\u7528\u6237id", required=true, dataType="String", paramType="query"), @ApiImplicitParam(name="fanId", value="\u7c89\u4e1did", required=true, dataType="String", paramType="query")})
    @PostMapping(value={"/beYourFans"})
    public JsonResult beYourFans(String userId, String fanId) {
        if (StringUtils.isBlank((CharSequence)userId) || StringUtils.isBlank((CharSequence)fanId)) {
            return JsonResult.errorMsg("userId\u548cfanId\u4e0d\u80fd\u4e3a\u7a7a");
        }
        this.userService.saveUserFanRelation(userId, fanId);
        return JsonResult.ok("\u5173\u6ce8\u6210\u529f");
    }

    @ApiOperation(value="\u53d6\u6d88\u5173\u6ce8\u7528\u6237", notes="\u53d6\u6d88\u5173\u6ce8\u7528\u6237\u7684\u63a5\u53e3")
    @ApiImplicitParams(value={@ApiImplicitParam(name="userId", value="\u7528\u6237id", required=true, dataType="String", paramType="query"), @ApiImplicitParam(name="fanId", value="\u7c89\u4e1did", required=true, dataType="String", paramType="query")})
    @PostMapping(value={"/dontBeYourFans"})
    public JsonResult dontBeYourFans(String userId, String fanId) {
        if (StringUtils.isBlank((CharSequence)userId) || StringUtils.isBlank((CharSequence)fanId)) {
            return JsonResult.errorMsg("userId\u548cfanId\u4e0d\u80fd\u4e3a\u7a7a");
        }
        this.userService.deleteUserFanRelation(userId, fanId);
        return JsonResult.ok("\u53d6\u6d88\u5173\u6ce8\u6210\u529f");
    }

    @ApiOperation(value="\u4e3e\u62a5\u7528\u6237", notes="\u4e3e\u62a5\u7528\u6237\u7684\u63a5\u53e3")
    @PostMapping(value={"/reportUser"})
    public JsonResult reportUser(@RequestBody UserReport userReport) {
        this.userService.reportUser(userReport);
        return JsonResult.ok("\u4e3e\u62a5\u6210\u529f\u2026\u6709\u4f60\u5e73\u53f0\u66f4\u7f8e\u597d");
    }
}
