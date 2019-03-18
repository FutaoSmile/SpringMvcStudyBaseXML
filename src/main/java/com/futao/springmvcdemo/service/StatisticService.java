package com.futao.springmvcdemo.service;

import com.alibaba.fastjson.JSONArray;
import com.futao.springmvcdemo.model.entity.ApiControllerDescription;
import com.futao.springmvcdemo.model.system.ErrorMessageFields;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author futao
 * Created on 2019-03-15.
 */
public interface StatisticService {
    /**
     * 获取程序中定义的错误码
     *
     * @return
     * @throws IllegalAccessException
     */
    @Cacheable("errorMessageList")
    List<ErrorMessageFields> getErrorMessages() throws IllegalAccessException;

    /**
     * 获取程序中的枚举值
     *
     * @return
     */
    @Cacheable("enumList")
    abstract Map<String, JSONArray> enumList();

    /**
     * @return
     */
    ArrayList<ApiControllerDescription> apiList();

    ArrayList<ApiControllerDescription> a();
}
