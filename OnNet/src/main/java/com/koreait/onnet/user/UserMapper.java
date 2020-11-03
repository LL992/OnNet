package com.koreait.onnet.user;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.onnet.user.model.UserDMI;
import com.koreait.onnet.user.model.UserPARAM;
import com.koreait.onnet.user.model.UserVO;

@Mapper
public interface UserMapper {
	public int insUser(UserVO p);
	public UserDMI selUser(UserPARAM  p);
}
