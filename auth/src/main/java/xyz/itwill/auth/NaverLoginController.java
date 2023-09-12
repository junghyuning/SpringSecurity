package xyz.itwill.auth;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


//Naver 로그인 Open API를 사용하여 간편로그인하는 방법
//=> 

@Controller
@RequestMapping("/naver")
public class NaverLoginController {
	@RequestMapping("/login")
	public String login(Model model, HttpSession session) throws UnsupportedEncodingException {
		 String clientId = "mHXwNpAULbrozZWrhDo2";//애플리케이션 클라이언트 아이디값";
		    String redirectURI = URLEncoder.encode("http://localhost:8000/auth/naver/callback", "UTF-8");
		    SecureRandom random = new SecureRandom();
		    String state = new BigInteger(130, random).toString();
		    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
		    apiURL += "&client_id=" + clientId;
		    apiURL += "&redirect_uri=" + redirectURI;
		    apiURL += "&state=" + state;
		    session.setAttribute("state", state);
		return"rediredt:";
	}
}
