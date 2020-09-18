package com.koreait.matzip.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.user.model.UserDMI;
import com.koreait.matzip.user.model.UserDTO;
import com.koreait.matzip.user.model.UserVO;

@Service //컴포넌트랑 똑같이생각하면 됨
public class UserService {
	@Autowired
	private UserMapper mapper;

//로그인 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	//1번 로그인성공, 2번 아이디없음, 3번 비번틀림
	public int login(UserDTO param) {
		if(param.getUser_id().equals("")) {
			return Const.NO_ID;
		}		
		
		UserDMI dbUser = mapper.selUser(param);
		
		if(dbUser.getI_user() == 0) {
			return Const.NO_ID;
		}else {
			String salt = dbUser.getSalt();
			String pw = param.getUser_pw();
			String enpw = SecurityUtils.getEncrypt(pw, salt);
			if(!dbUser.getUser_pw().equals(enpw)) {
				return Const.NO_PW;
			}
		}
		return Const.SUCCESS;
	}
	
//회원가입 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ	
	public int join(UserVO param) {
		String pw = param.getUser_pw();
		String salt = SecurityUtils.generateSalt();
		String cryptPw = SecurityUtils.getEncrypt(pw, salt);
		
		param.setSalt(salt);
		param.setUser_pw(cryptPw);
		return mapper.insUser(param);
	}

}
