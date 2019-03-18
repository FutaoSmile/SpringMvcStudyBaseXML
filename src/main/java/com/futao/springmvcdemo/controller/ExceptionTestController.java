package com.futao.springmvcdemo.controller;

import com.futao.springmvcdemo.foundation.ApplicationException;
import com.futao.springmvcdemo.foundation.LogicException;
import com.futao.springmvcdemo.foundation.configuration.HibernateValidatorConfiguration;
import com.futao.springmvcdemo.model.entity.User;
import com.futao.springmvcdemo.model.system.ErrorMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * 统一异常处理测试接口
 *
 * @author futao
 * Created on 2018/9/23-0:28.
 */
@RequestMapping(path = "exception", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
@Validated
@Api("异常示例")
public class ExceptionTestController {

    /**
     * 业务逻辑异常
     */
    @ApiOperation("业务逻辑异常")
    @GetMapping(path = "logicException")
    public void logicException() {
        throw LogicException.le(ErrorMessage.LogicErrorMessage.LOGIC_EXCEPTION);
    }

    /**
     * 系统未捕获异常
     */
    @ApiOperation("系统未捕获异常")
    @GetMapping(path = "systemException")
    public void systemException() {
        throw new NullPointerException("空指针了，哥门!!!");
    }


    /**
     * 验证框架测试
     *
     * @param param
     */
    @ApiOperation("Hibernate Validator 框架校验异常")
    @PostMapping("validatorTest")
    public void validatorTest(
            @RequestParam("param")
            @NotNull
                    int param
    ) {
        User user = new User();
//        user.setRole(param);

        HibernateValidatorConfiguration.validate(user);
    }

    /**
     * applicationException
     */
    @PostMapping("applicationException")
    @ApiOperation("applicationException测试")
    public void applicationException() {
        throw ApplicationException.ae("ApplicationErrorMessage");
    }
}
