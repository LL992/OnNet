package com.koreait.onnet.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonMapper {
	List<CodeVO> selCodeList(CodeVO p);
}
