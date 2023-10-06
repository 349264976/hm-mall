package com.hmuser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.hmuser.domain.po.Address;
import com.hmuser.mapper.AddressMapper;
import com.hmuser.service.IAddressService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2023-05-05
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

}
