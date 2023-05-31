package com.brian.admin.service;

import com.brian.admin.vo.HomeDataVO;
import com.brian.common.core.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;

/**
 * @author Brian
 * @date 2023/5/26
 **/
public interface HomeService {

    Result<HomeDataVO> getData() throws ExecutionException, InterruptedException;

    Result<?> report(HttpServletRequest request);
}
