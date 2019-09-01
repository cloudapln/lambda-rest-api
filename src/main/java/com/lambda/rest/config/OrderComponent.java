

package com.lambda.rest.config;

import com.lambda.rest.dao.OrderDao;
import com.lambda.rest.handler.*;
import com.lambda.rest.handler.s3.GetS3ObjectHandler;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {OrderModule.class})
public interface OrderComponent {
    OrderDao provideOrderDao();

    void inject(CreateOrdersTableHandler requestHandler);

    void inject(CreateOrderHandler requestHandler);

    void inject(DeleteOrderHandler requestHandler);

    void inject(GetOrderHandler requestHandler);

    void inject(GetOrdersHandler requestHandler);

    void inject(UpdateOrderHandler requestHandler);

    void inject(GetS3ObjectHandler requestHandler);

}
