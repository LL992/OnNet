package com.koreait.onnet.rest;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.koreait.onnet.rest.model.RestDMI;
import com.koreait.onnet.rest.model.RestPARAM;

@Mapper
public interface RestMapper {
	public List<RestDMI> selRestList(RestPARAM param);
}
