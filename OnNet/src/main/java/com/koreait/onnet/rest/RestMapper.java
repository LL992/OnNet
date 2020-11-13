package com.koreait.onnet.rest;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.onnet.rest.model.RestDMI;
import com.koreait.onnet.rest.model.RestPARAM;
import com.koreait.onnet.rest.model.RestRecMenuVO;

@Mapper
public interface RestMapper {
	int insRest(RestPARAM param);
	int insRestRecMenu(RestRecMenuVO param);
	int insRestMenu(RestRecMenuVO param);
	List<RestDMI>selRestList(RestPARAM param);
	RestDMI selRest(RestPARAM param);
	List<RestRecMenuVO> selRestRecMenus(RestPARAM param);
	int delrestRecMenu(RestPARAM param);
	int delRestMenu(RestPARAM param);
	int delRest(RestPARAM param);
}
