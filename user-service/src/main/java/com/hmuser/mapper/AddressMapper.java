package com.hmuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hmuser.domain.po.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.MapperConfig;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 虎哥
 * @since 2023-05-05
 */
@Mapper
public interface AddressMapper extends BaseMapper<Address> {

    @Select("select * from address")
    Address selectaddressget();
}
