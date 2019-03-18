package com.futao.springmvcdemo.controller.business;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.futao.springmvcdemo.annotation.impl.interceptor.RequestLogInterceptor;
import com.futao.springmvcdemo.model.entity.ApiControllerDescription;
import com.futao.springmvcdemo.model.system.ErrorMessageFields;
import com.futao.springmvcdemo.service.StatisticService;
import com.futao.springmvcdemo.service.notbusiness.I18nService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 统计
 *
 * @author futao
 * Created on 2018/10/11.
 */
@RestController
@RequestMapping(path = "statistic", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class StatisticController {

//    @Resource
//    private OnlineHttpSessionListener onlineHttpSessionListener;

    @Resource
    private RequestLogInterceptor requestLogInterceptor;

    @Resource
    private StatisticService statisticService;

    /**
     * 获取当前在线人数
     *
     * @return
     */
//    @ApiOperation("统计当前在线人数")
//    @Role(UserRoleEnum.ADMIN)
//    @GetMapping("onlinePeopleQuantity")
//    public SingleValueResult<Integer> onlinePeopleQuantity() {
//        return new SingleValueResult<>(onlineHttpSessionListener.getOnlinePeopleQuantity().get());
//    }

    /**
     * 统计接口的请求次数
     *
     * @return
     */
    @ApiOperation("统计接口的请求次数")
    @GetMapping("apiRequest")
    public ConcurrentHashMap<String, AtomicInteger> apiRequest() {
        return requestLogInterceptor.getApiRequestStatistic();
    }

    /**
     * 统计所有的错误码
     *
     * @return
     */
    @ApiOperation("统计所有的错误码")
    @GetMapping("errorMessages")
    public List<ErrorMessageFields> errorMessages() throws IllegalAccessException {
        List<ErrorMessageFields> errorMessages = statisticService.getErrorMessages();
        System.out.println(JSONObject.toJSONString(errorMessages));
        return errorMessages;
    }

    /**
     * 统计所有的枚举类信息
     *
     * @return
     */
    @ApiOperation("统计所有的枚举类信息")
    @GetMapping("enumList")
    public Map<String, JSONArray> enumList() {
        return statisticService.enumList();
    }


    /**
     * 统计系统中所有的api
     *
     * @return
     */
    @ApiOperation("统计系统中所有的api")
    @GetMapping(path = "apiList")
    public ArrayList<ApiControllerDescription> apiList() throws InterruptedException {
        System.out.println(I18nService.getMessage("welcome"));
        System.out.println(I18nService.getMessage("test"));
        return statisticService.apiList();
    }

}
