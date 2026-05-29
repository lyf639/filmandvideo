package com.lin.controller;

import com.lin.controller.BaseController;
import com.lin.model.User;
import com.lin.model.vo.UserVo;
import com.lin.service.UserService;
import com.lin.utils.JsonResult;
import com.lin.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value="\u7528\u6237\u6ce8\u518c\u767b\u9646\u7684\u63a5\u53e3", tags={"\u6ce8\u518c\u767b\u9646\u7684Controller"})
@RestController
public class RegisterLoginController
extends BaseController {
    @Autowired
    private UserService userService;

    @ApiOperation(value="\u7528\u6237\u6ce8\u518c", notes="\u7528\u6237\u6ce8\u518c\u7684\u63a5\u53e3")
    @PostMapping(value={"/register"})
    public JsonResult register(@RequestBody User user) throws Exception {
        if (StringUtils.isBlank((CharSequence)user.getUsername()) || StringUtils.isBlank((CharSequence)user.getPassword())) {
            return JsonResult.errorMsg("\u7528\u6237\u540d\u548c\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a");
        }
        boolean isExist = this.userService.queryUsernameIsExist(user.getUsername());
        if (isExist) {
            return JsonResult.errorMsg("\u7528\u6237\u540d\u5df2\u5b58\u5728\uff0c\u8bf7\u66f4\u6362\u4e00\u4e2a\u518d\u5c1d\u8bd5");
        }
        user.setUsername(user.getUsername());
        user.setNickname(user.getUsername());
        user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
        user.setFansCounts(0);
        user.setReceiveLikeCounts(0);
        user.setFollowCounts(0);
        this.userService.saveUser(user);
        user.setPassword("");
        UserVo userVo = this.setUserRedisSessionToken(user);
        return JsonResult.ok(userVo);
    }

    @ApiOperation(value="\u7528\u6237\u767b\u9646", notes="\u7528\u6237\u767b\u9646\u7684\u63a5\u53e3")
    @PostMapping(value={"/login"})
    public JsonResult login(@RequestBody User user) throws Exception {
        if (StringUtils.isBlank((CharSequence)user.getUsername()) || StringUtils.isBlank((CharSequence)user.getPassword())) {
            return JsonResult.errorMsg("\u7528\u6237\u540d\u548c\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a");
        }
        User resultUser = this.userService.queryUserForLogin(user.getUsername(), MD5Utils.getMD5Str(user.getPassword()));
        if (resultUser == null) {
            return JsonResult.errorMsg("\u7528\u6237\u540d\u6216\u5bc6\u7801\u9519\u8bef");
        }
        resultUser.setPassword("");
        UserVo userVo = this.setUserRedisSessionToken(resultUser);
        return JsonResult.ok(userVo);
    }

    @ApiOperation(value="\u7528\u6237\u6ce8\u9500", notes="\u7528\u6237\u6ce8\u9500\u7684\u63a5\u53e3")
    @ApiImplicitParam(name="userId", value="\u7528\u6237id", required=true, dataType="String", paramType="query")
    @PostMapping(value={"/logout"})
    public JsonResult logout(String userId) {
        this.redis.del("user-redis-session:" + userId);
        return JsonResult.ok();
    }

    private UserVo setUserRedisSessionToken(User user) {
        String uniqueToken = UUID.randomUUID().toString();
        this.redis.set("user-redis-session:" + user.getId(), uniqueToken, 1800000L);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties((Object)user, (Object)userVo);
        userVo.setUserToken(uniqueToken);
        return userVo;
    }
}
