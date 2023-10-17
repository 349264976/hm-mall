package com.hmapi.client;

import com.hmapi.dto.ItemDTO;
import com.hmapi.dto.OrderDetailDTO;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;


@Slf4j
public class ItemClientFallbackFactory implements FallbackFactory<ItemClient> {
    @Override
    public ItemClient create(Throwable cause) {
        return new ItemClient() {
            @Override
            public List<ItemDTO> queryItemByIds(List<Long> ids) {
                //查询失败返回空集合
                return Collections.emptyList();
            }

            @Override
            public void deductStock(List<OrderDetailDTO> items) throws Exception {
                //查询失败返回空集合
               throw new Exception("Failed to duplicate stock");
            }
        };
    }
}