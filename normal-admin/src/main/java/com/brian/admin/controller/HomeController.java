package com.brian.admin.controller;

import com.brian.admin.service.HomeService;
import com.brian.common.core.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian
 * @date 2023/5/26
 **/
@RestController
@RequestMapping("/home")
public class HomeController {

    @Resource
    private HomeService homeService;

    @RequestMapping("/")
    public Result homeData() throws Exception {
        return homeService.getData();
    }

    @RequestMapping("/report")
    public Result report(HttpServletRequest request) {
        return homeService.report(request);
    }
}
