package com.lin.controller;

import com.lin.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {
    @Autowired
    protected RedisOperator redis;
    @Value("${video.upload.path}")
    protected String fileBase;
    @Value("${ffmpeg.path}")
    protected String ffmpegExe;
    protected static final String USER_REDIS_SESSION = "user-redis-session";
    protected static final Integer PAGE_SIZE = 5;
}
