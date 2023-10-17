package com.hmapi.client;

import com.hmapi.dto.ItemDTO;
import com.hmapi.dto.OrderDetailDTO;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "item-service",fallbackFactory = ItemClientFallbackFactory.class )
public interface ItemClient {

    @GetMapping("/items")
    public List<ItemDTO> queryItemByIds(@RequestParam("ids") List<Long> ids);

    @ApiOperation("批量扣减库存")
    @PutMapping("/stock/deduct")
    public void deductStock(@RequestBody List<OrderDetailDTO> items) throws Exception;

}
