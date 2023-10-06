package com.hmpay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hmpay.domain.dto.PayApplyDTO;
import com.hmpay.domain.dto.PayOrderFormDTO;
import com.hmpay.domain.po.PayOrder;
import org.apache.ibatis.annotations.Mapper;


/**
 * <p>
 * 支付订单 服务类
 * </p>
 *
 * @author 虎哥
 * @since 2023-05-16
 */
public interface IPayOrderService extends IService<PayOrder> {

    String applyPayOrder(PayApplyDTO applyDTO);

    void tryPayOrderByBalance(PayOrderFormDTO payOrderFormDTO);
}
