package com.hmapi.client;

import com.hmapi.dto.ItemDTO;
import com.hmapi.dto.OrderDetailDTO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("item-service")
public interface ItemClient {

    @GetMapping("/items")
   List<ItemDTO> queryItemByIds(@RequestParam("ids") List<Long> ids);

    @PutMapping("/stock/deduct")
    void deductStock(@RequestBody List<OrderDetailDTO> items);

}
