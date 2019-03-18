package com.futao.springmvcdemo.foundation.configuration;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

/**
 * @author futao
 * Created on 2019-03-15.
 */
public class MybatisConfiguration implements TransactionManagementConfigurer {


    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return null;
    }
}
