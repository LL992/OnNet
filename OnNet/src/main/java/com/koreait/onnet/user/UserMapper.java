package com.koreait.onnet.user;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.onnet.model.UserVO;
import com.koreait.onnet.user.model.UserDMI;
import com.koreait.onnet.user.model.UserPARAM;

@Mapper
public interface UserMapper {
	public int insUser(UserVO p);
	public UserDMI selUser(UserPARAM  p);
}
