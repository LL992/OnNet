package com.koreait.todoapp;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TodoMapper {
	int insTodo(TodoVO param);
	List<TodoVO> getTooList();
}
