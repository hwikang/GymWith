package kr.goott.gymcarry.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.scribejava.core.model.OAuth2AccessToken;

import kr.goott.gymcarry.auth.NaverLoginBO;
import kr.goott.gymcarry.model.dao.UserDAOInterface;
import kr.goott.gymcarry.model.dto.CommunityDTO;
import kr.goott.gymcarry.model.dto.UserDTO;

@Controller
@RequestMapping("/user/*")
public class UserController {
	final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Inject
	UserDAOInterface userDAO;
	@Inject
	NaverLoginBO naverLoginBO;
	
	@RequestMapping(value = "userJoin.do")
	public ModelAndView userJoin(HttpSession session) {
		logger.info("userjoin page view...");
		/* 네아로 인증 URL을 생성하기 위하여 getAuthorizationUrl을 호출 */
		String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);		
		return new ModelAndView("user/userJoin","url",naverAuthUrl);
	}

	@RequestMapping(value = "login.do")
	public ModelAndView userLogin(HttpSession session) {
		logger.info("userLogin page view...");
		/* 네아로 인증 URL을 생성하기 위하여 getAuthorizationUrl을 호출 */
		String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);	
		return new ModelAndView("user/userLogin","url",naverAuthUrl);
	}
	
	@RequestMapping(value = "naverLogin.do")
	public ModelAndView naverLogin(@RequestParam String code, @RequestParam String state, HttpSession session) throws Exception{
		
		/* 네이버 아이디로 로그인 인증이 끝나면 callback 처리 과정에서 AccessToken을 발급 받을 수 있다.*/
		OAuth2AccessToken oauthToken = naverLoginBO.getAccessToken(session, code, state);
		/* 발급 받은 AccessToken을 이용하여 현재 로그인한 네이버의 사용자 프로필 정보를 조회할 수 있다. */
		UserDTO naverUser = naverLoginBO.getUserProfile(oauthToken);
		/* 네이버 사용자 프로필 정보를 이용하여 가입되어 있는 사용자를 DB에서 조회하여 가져온다. */
		UserDTO naverid = userDAO.naverIdCheck(naverUser);
		if(naverid!=null) { //기존 사용자가 존재하면 강제 로그인!
			session.setAttribute("userid", naverid.getUserid());
			session.setAttribute("username", naverid.getUsername());
			session.setAttribute("loginCheck", "Y");
			return new ModelAndView("/home");			
		}else {//아이디값이 null이면 아이디 생성해야지			
			return new ModelAndView("user/registerNaver", "naverUser", naverUser);
		}		
	}
	
	@RequestMapping(value ="naverRegister.do", method = RequestMethod.POST)
	public ModelAndView naverRegister(@ModelAttribute UserDTO dto) {
		logger.info("naverRegister -> registerDone page view...");
		userDAO.insertNaverUser(dto);
		ModelAndView mav = new ModelAndView();
		mav.addObject("userid", dto.getUserid());
		System.out.println(dto.getUserid());
		mav.setViewName("user/registerDone");
		return mav;
	}
	
	@RequestMapping(value = "loginChk.do", method = RequestMethod.POST)
	public ModelAndView userLoginChk(@ModelAttribute UserDTO dto, HttpSession session) {
		logger.info("loginCheck page view...");
		// 일치하면 이름이 넘어오고 틀리면 null이 넘어옴
		UserDTO dto2 = userDAO.loginCheck(dto);
		if (dto2!= null) { // 맞으면
			// 세션변수 등록
			session.setAttribute("userid", dto2.getUserid());
			session.setAttribute("username", dto2.getUsername());
			session.setAttribute("loginCheck", "Y");
			return new ModelAndView("/home");
		}else {
			return new ModelAndView("redirect:login.do");
		}
	}

	@RequestMapping(value = "logout.do")
	public String logout(HttpSession session) {
		logger.info("logout success...");
		session.invalidate();
		return "/home";
	}

	@RequestMapping(value = "registerEmail.do")
	public String emailLogin() {
		logger.info("registerEmail page view...");
		return "user/registerEmail";
	}

	@RequestMapping(value = "direct.do", method = RequestMethod.POST)
	public ModelAndView registerEmail(@ModelAttribute UserDTO dto) {
		logger.info("registerDone page view...");
		userDAO.insertUser(dto);
		ModelAndView mav = new ModelAndView();
		mav.addObject("userid", dto.getUserid());
		System.out.println(dto.getUserid());
		mav.setViewName("user/registerDone");
		return mav;
	}

	// 경호 안와서 멋쟁이 윤성이 수정
	@RequestMapping(value = "myProfile.do", method = RequestMethod.GET)
	public ModelAndView myProfile(HttpSession session) {
		logger.info("myprofile page view...");

			UserDTO dto = new UserDTO();
			String userid = (String) session.getAttribute("userid");
			logger.info(userid+"-------------------------------------");
			dto = userDAO.userInfo(userid);
			
			return new ModelAndView("user/myProfile", "dto", dto);
	}
	//윤성이 끝
	
	@RequestMapping(value = "regAddInfo.do")
	public ModelAndView regAddInfo(@RequestParam String userid) {
		logger.info("regAddInfo page view...");
		UserDTO dto = new UserDTO();
		System.out.println("reguserid=" + userid);
		dto = userDAO.userInfo(userid);
		return new ModelAndView("user/regAddInfo", "userDTO", dto);
	}


	@RequestMapping(value="regAddDone.do", method = RequestMethod.POST) public
	String regAddDone(@ModelAttribute UserDTO dto) {
	logger.info("regAddDone page view"); 
	logger.info(dto.toString());
	int cnt = userDAO.addInfoUser(dto);
		if(cnt==1) { 
			return "/home";
		}
		else { 
			return "redirect:/regAddInfo"; 
			} 
	}
	
	@RequestMapping(value = "findId.do")
	public String findId() {
		logger.info("findId page view...");
		return "user/findId";
	}
	@RequestMapping(value = "findIdresult.do")
	public ModelAndView findIdSuccess(@ModelAttribute UserDTO dto) {
		logger.info("findIdresult page view");
		String userid = userDAO.findId(dto);
		if(userid!=null) {			
			return new ModelAndView("user/findIdSuccess","userid",userid);
		}else {
			return new ModelAndView("user/findIdFail");
		}
	}
	@RequestMapping(value = "findPwd.do")
	public String findPwd() {
		logger.info("findPwd page view...");
		return "user/findPwd";
	}
	
	@RequestMapping(value="idcheck.do")
	@ResponseBody
	public Map<Object, Object> idcheck(@RequestBody String userid){
		int cnt = 0;
		Map<Object, Object> map = new HashMap<Object, Object>();
		cnt = userDAO.idCheckCount(userid);
		map.put("cnt", cnt);
		
		return map;
	}
}
