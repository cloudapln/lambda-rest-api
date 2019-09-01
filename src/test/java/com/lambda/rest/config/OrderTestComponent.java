package com.lambda.rest.config;

import com.lambda.rest.dao.OrderDao;
import com.lambda.rest.handler.OrderHandlerTestBase;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {OrderModule.class})
public interface OrderTestComponent {
    OrderDao provideOrderDao();
    void inject(OrderHandlerTestBase integrationTest);
}
