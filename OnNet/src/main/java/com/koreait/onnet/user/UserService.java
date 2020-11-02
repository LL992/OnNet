package com.koreait.onnet.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.onnet.Const;
import com.koreait.onnet.SecurityUtils;
import com.koreait.onnet.model.UserVO;
import com.koreait.onnet.user.model.UserDMI;
import com.koreait.onnet.user.model.UserPARAM;

@Service
public class UserService {

	@Autowired
	private UserMapper mapper;
	
	//1번 로그인 성공, 2번 아이디 없음, 3번 비번 틀림
	public int login(UserPARAM param) {
		if(param.getUser_id().equals("")) { return Const.NO_ID; }

		UserDMI dbUser = mapper.selUser(param);		
		if(dbUser == null) { return Const.NO_ID; }

		String cryptPw = SecurityUtils.getEncrypt(param.getUser_pw(), dbUser.getSalt());
		if(!cryptPw.equals(dbUser.getUser_pw())) { return Const.NO_PW; }

		param.setUser_pw(null);
		param.setNm(dbUser.getNm());
		param.setProfile_img(dbUser.getProfile_img());
		return Const.SUCCESS;
		}

		public int join(UserVO param) {
			String pw = param.getUser_pw();
			String salt = SecurityUtils.generateSalt();
			String cryptPw = SecurityUtils.getEncrypt(pw, salt);

			param.setSalt(salt);
			param.setUser_pw(cryptPw);

			
			return mapper.insUser(param);
		}
}