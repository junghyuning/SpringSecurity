package xyz.itwill.auth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.scribejava.core.model.OAuth2AccessToken;

import lombok.RequiredArgsConstructor;
import xyz.itwill.dto.SecurityAuth;
import xyz.itwill.dto.SecurityUsers;
import xyz.itwill.security.CustomUserDetails;
import xyz.itwill.security.NaverLoginBean;
import xyz.itwill.service.SecurityUsersService;

//���̹��� �α��� OpenAPI�� ����Ͽ� ���� �α����ϴ� ���
//1.���̹� ������ ���Ϳ� OpenAPI ��û ����� ����Ͽ� ���ø����̼� ���
// => ���ø����̼� �̸�, ����� API, ���� ȯ��(���� URL, �ݹ� URL)���� �Է��Ͽ� ���
//2.�� ���ø����̼ǿ��� Client Id, Client Secret  Ȯ��
//3.���̹� �α��� �̹����� �ٿ�ε� �޾� ����ϰ� ���� URL�� ��ũ ����
//4.scribejava-apis ���̺귯���� json-simple ���̺귯�� ������Ʈ�� ���� ó��
//5.�α��� ���� API�� ��û�ϱ� ���� Ŭ���� �ۼ� - NaverLoginApi Ŭ������ NaverLoginBean Ŭ����
//6.��û ó�� �޼ҵ忡�� �α��� ���� API�� ��û�ϱ� ���� Ŭ������ �޼ҵ带 ȣ���Ͽ� ������ �̿��� ���� ó��

@Controller
@RequestMapping("/naver")
@RequiredArgsConstructor
public class NaverLoginController {
	private final NaverLoginBean naverLoginBean;
	private final SecurityUsersService securityUsersService;
	
	//���̹� �α��� �������� ��û�ϱ� ���� ��û ó�� �޼ҵ�
	@RequestMapping("/login")
	public String login(HttpSession session) throws UnsupportedEncodingException {
		String naverAuthUrl=naverLoginBean.getAuthorizationUrl(session);
		return "redirect:"+naverAuthUrl;
	}
	
	//���̹� �α��� ������ Callback URL �������� ó���ϱ� ���� ��û ó�� �޼ҵ�
	@RequestMapping("/callback")
	public String login(@RequestParam String code, @RequestParam String state
			, HttpSession session) throws IOException, ParseException {
		//���̹� �α��� ����ڿ� ���� ���� ��ū�� ��ȯ�ϴ� �޼ҵ� ȣ���Ͽ� ����� ���� ��ū ���� 
		OAuth2AccessToken accessToken=naverLoginBean.getAccessToken(session, code, state);
		
		//���� ��ū�� �̿��Ͽ� �α��� ������� �������� ��ȯ�ϴ� �޼ҵ带 ȣ���Ͽ� ����� ������(JSON)�� ����
		String apiResult=naverLoginBean.getUserProfile(accessToken);
		//{"resultcode":"00","message":"success","response":{"id":"XAfMAwX_vELrzkOKnQPW2B5VSOs4kPM5P0Zl0ZuFY00","nickname":"ocj****","email":"ocj1778@hanmail.com","name":"\uc624\ucc3d\uc911"}}
		//System.out.println(apiResult);
		
		//JSONParser ��ü : JSON ������ ���ڿ��� JSON ��ü�� ��ȯ�ϴ� ����� �����ϴ� ��ü
		JSONParser parser=new JSONParser();
		//JSONParser.parse(String json) : JSON ������ ���ڿ��� Object ��ü�� ��ȯ�ϴ� �޼ҵ�
		Object object=parser.parse(apiResult);
		//Object ��ü�� JSONObject ��ü�� ��ȯ�Ͽ� ����
		JSONObject jsonObject=(JSONObject)object;
		
		//JSON ��ü�� ����� ���� �����޾� ���� - �Ľ�(Parsing)
		//JSONObject.get(String name) : JSONObject ��ü�� ����� ��(��ü)�� ��ȯ�ϴ� �޼ҵ�
		// => Object Ÿ������ ��(��ü)�� ��ȯ�ϹǷ� �ݵ�� ����ȯ�Ͽ� ����
		JSONObject responseObject=(JSONObject)jsonObject.get("response");
		String id=(String)responseObject.get("id");
		String name=(String)responseObject.get("name");
		String email=(String)responseObject.get("email");
		
		//��ȯ���� ���̹� ����� �������� ���� ����Ͽ� Java ��ü�� �ʵ尪���� ����
		SecurityAuth auth=new SecurityAuth();
		auth.setUserid("naver_"+id);
		auth.setAuth("ROLE_USER");
		
		List<SecurityAuth> authList=new ArrayList<SecurityAuth>();
		authList.add(auth);
		
		SecurityUsers users=new SecurityUsers();
		users.setUserid("naver_"+id);
		users.setPasswd(UUID.randomUUID().toString());
		users.setName(name);
		users.setEmail(email);
		users.setEnabled("1");
		users.setSecurityAuthList(authList);
		
		//���̹� �α��� ������� ������ SECURITY_USERS ���̺�� SECURITY_AUTH ���̺� ����
		if(securityUsersService.getSecurityUsers("naver_"+id) == null) {
			securityUsersService.addSecurityUsers(users);
			securityUsersService.addSecurityAuth(auth);
		}
		
		//���̹� �α��� ����� ������ ����Ͽ� UserDetails ��ü(�α��� �����)�� �����Ͽ� ����
		CustomUserDetails customUserDetails=new CustomUserDetails(users);
		
		//UsernamePasswordAuthenticationToken ��ü�� �����Ͽ� Spring Security�� ��� ������
		//���� ����ڷ� ��� ó��
		//UsernamePasswordAuthenticationToken ��ü : ���� ������ ����ڸ� Spring Security��
		//��� ������ ���� ����ڷ� ��� ó���ϴ� ��ü
		Authentication authentication=new UsernamePasswordAuthenticationToken
				(customUserDetails, null, customUserDetails.getAuthorities());
		
		//SecurityContextHolder ��ü : ���� ������� ���� ���� ������ �����ϱ� ���� ��ü
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "redirect:/";
	}
}











