package com.koreait.matzip;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/hehe")
//@RequestMapping를 class위에 적어줘도됨 이렇게되면 1차주소가 hehe가됨 EX)localhost:8090/hehe/hello , localhost:8090/hehe/home
public class Memo {
	
	private static final Logger logger = LoggerFactory.getLogger(Memo.class);
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET) //여기 value에 맵핑할 주소를 적어놓음
									//Post로 받고싶다면 method부분을 POST로 바꿔주면됨 (기본은 GET임)
	public String hello(Model model,  HttpServletRequest request) { //만약에 Model을 쓰기싫다면(오리지날을 추구한다면) HttpServletRequest request써도됨
		model.addAttribute("myName","다성바보");
		request.setAttribute("msg", "다성이 === 바보");
		return "hello";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) { //Model객체가 request에서 setAttribute하는 역할
		logger.info("Welcome home! The client locale is {}.", locale); //그냥 콘솔에 찍어주는것
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate ); //우리가 request.setAtrribute 하던거
		
		return "home"; //String타입으로 열고싶은 파일명을 적어주면됨
	}
	
}
