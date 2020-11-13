package com.koreait.todoapp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {
	
	@RequestMapping("/getTodoList")
	public void getTodoList() {
		
	}
}
