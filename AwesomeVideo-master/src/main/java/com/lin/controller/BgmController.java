package com.lin.controller;

import com.lin.controller.BaseController;
import com.lin.service.BgmService;
import com.lin.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="\u80cc\u666f\u97f3\u4e50\u4e1a\u52a1\u7684\u63a5\u53e3", tags={"\u80cc\u666f\u97f3\u4e50\u4e1a\u52a1\u7684Controller"})
@RestController
@RequestMapping(value={"/bgm"})
public class BgmController
extends BaseController {
    @Autowired
    private BgmService bgmService;

    @ApiOperation(value="\u83b7\u53d6\u80cc\u666f\u97f3\u4e50\u5217\u8868", notes="\u83b7\u53d6\u80cc\u666f\u97f3\u4e50\u5217\u8868\u7684\u63a5\u53e3")
    @PostMapping(value={"/list"})
    public JsonResult list() {
        return JsonResult.ok(this.bgmService.queryBgmList());
    }
}
