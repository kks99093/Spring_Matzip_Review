package com.koreait.matzip.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.koreait.matzip.Const;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.user.model.UserDTO;
import com.koreait.matzip.user.model.UserVO;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired //bean등록 된애들중에서 service에 넣을수있는애가 있으면 자동으로 넣어준다
	private UserService service;
	//bean등록 - 클래스위에 @적힌애,SpringContainer가 관리하는애
	//Autowired는 여기에 넣을수있는애가 1개만있어야함 여러개라면 에러터짐(어떤걸 넣어야할지 모르니)
	
//로그인ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(Model model, @RequestParam(defaultValue="0") int err) { //화면(jsp파일)한테 뭔가 보내줘야할때는 model을 적어줘야함
		System.out.println("err : " + err);
		if(err == 2) {
			System.out.println("아이디없음");
			model.addAttribute("msg", "아이디 없음");			
		}else if(err == 3) {
			System.out.println("비번틀림");
			model.addAttribute("msg", "비밀번호 틀림");
		}
		model.addAttribute(Const.TITLE, "로그인");
		model.addAttribute(Const.VIEW, "user/login");
		return ViewRef.TEMP_DEFAULT;
	}

	@RequestMapping(value="/login", method = RequestMethod.POST)
	//같은주소값의 GET으로 날렸을떄 POST로 날렸을때 바꿀수있음
	public String login(UserDTO param) { //여기서 중요한거 우리가 getParameter를 하지 않았다는거
		int result = service.login(param);
		
		if(result == 1) {
			System.out.println("로그인 성공");
			return "redirect:/rest/map";
		}
		return "redirect:/user/login?err="+result;
	}
//회원가입ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	@RequestMapping(value="/join", method = RequestMethod.GET)
	public String join(Model model, @RequestParam(defaultValue="0") int err) {
		//@RequestParam ==> 한값만 받을떄 쓰는것, 원래는 무조건 String이지만 바로 Integer를 적을수있다(null이 들어갈수도있기에 int가 아닌 Integer를 적음)
		//쿼리스트링에 보내는 키값과 저기에 적은벼수(err)이 같다면 @RequestParam(required=false) int err이렇게만적어도됨
		//다르다면 @RequestParam(value="err", required=false) int error라고 적어줘야함
		// required =>필수인지 묻는것, 첫페이지에는 없다가 에러떳을때만 생김으로 false를 줘야함
		// 기본값이 true로 되어있기때문에 err같은경우는 false를 해줘야함
		//Integer를 쓰기 싫다면 defaultValue="0"로 기본값을 0으로 줘놓으면됨 그러면 required=false도 필요없음
		System.out.println("err : " + err);
		if(err > 0) {
			model.addAttribute("msg", "에러가 발생하였습니다.");
		}
		model.addAttribute(Const.TITLE, "회원가입");
		model.addAttribute(Const.VIEW, "user/join");
		return ViewRef.TEMP_DEFAULT;
	}
	
	@RequestMapping(value="/join", method = RequestMethod.POST)
	public String join(UserVO param) {
					//여기에 setter가 있는 객체를 적어놓는다면, 이름이 같다면 정보가 다 담겨있다
					//ex)회원가입에서 user_id,user_pw,nm를 넘겨주는데 
					//UserVO에 user_id,user_pw,nm가 있다면 알아서 param에 넣어서 넘겨준다
		int result = service.join(param);
		
		if(result == 1) {
			return "redirect:/user/login";
		}
		return "redirect:/user/join?err="+result;
	}
}
