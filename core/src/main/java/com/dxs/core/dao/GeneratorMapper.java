package com.dxs.core.dao;

import com.dxs.core.domain.Generator;

public interface GeneratorMapper {
    int deleteByPrimaryKey(Integer pkId);

    int insert(Generator record);

    int insertSelective(Generator record);
}