package xyz.itwill.security;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

@Component
public class NaverLoginBean {
	//로그인에 필요한 정보를 저장한 필드(상수) 선언
	private static final String CLINT_ID="mHXwNpAULbrozZWrhDo2";
	private static final String CLINT_SECRET="totvPNJ11j";
	private static final String REDIRECT_URI="http://localhost:8000/auth/naver/callback";
	private static final String SESSION_STATE="naver_state";
	private static final String PROFILE_ID="https://openapi.naver.com/v1/nid/me";
	private static final String PROFILE_API_URI = null;
	
	//네이버 로그인 기능을 사용하여 토큰을 발급받기위한 메서드
	public String getAuthorizationUrl(HttpSession session) {
		//세션의 유효성 검증을 위한 난수값을 발생하여 저장
		String state = UUID.randomUUID().toString();
		
		//난수값을 세션 속성값으로 저장
		session.setAttribute(SESSION_STATE, state);
		
		//Scribe 라이브러리의 클래스로 토큰을 발급받기위한 API의 URL 주소가 저장된 객체 생성
		OAuth20Service oAuth20Service = new ServiceBuilder()
				.apiKey(CLINT_ID)
				.apiSecret(CLINT_SECRET)
				.callback(REDIRECT_URI)
				.state(state)
				.build(NaverLoginApi.instance());
		
		//네이버 로그인 기능을 요청하는 API 주소 반환
		return oAuth20Service.getAuthorizationUrl();
	}
	
	//로그인 처리 후 콜백 기능을 이용하여 로그인 사용자의 접근 토큰을 발급받아 반환하기위한 메서드
	public OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws IOException{
		//세션의 유효성 검증을 위해 세션에 저장된 속성값(상태)를 반환받아 저장
		String SessionState = (String)session.getAttribute(SESSION_STATE);
		
		//매개변수로 전달받은 값과 세션에 저장된 값이 다른 경우 - 비정상적인 요청
		if(StringUtils.pathEquals(SessionState, state)) {
			return null;
		}
		
		OAuth20Service oAuth20Service = new ServiceBuilder()
				.apiKey(CLINT_ID)
				.apiSecret(CLINT_SECRET)
				.callback(REDIRECT_URI)
				.state(state)
				.build(NaverLoginApi.instance());
		
		//사용자 접근 토큰을 발급하는 API를 요청하여 토큰을 발급받아 저장
		OAuth2AccessToken accessToken = oAuth20Service.getAccessToken(code);
		
		return accessToken;
	}
	
	//사용자 접근 토큰을 사용하여 사용자의 프로필을 제공하는 API를 호출하는 메서드
	public String getUserProfile(OAuth2AccessToken accessToken) throws IOException {
		OAuth20Service oAuth20Service = new ServiceBuilder()
				.apiKey(CLINT_ID)
				.apiSecret(CLINT_SECRET)
				.callback(REDIRECT_URI)
				.build(NaverLoginApi.instance());
		
		OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_API_URI, 	oAuth20Service);
		
		oAuth20Service.signRequest(accessToken, request);
		
		//요청에대한 결과(사용자 프로필)를 발급받아
		Response response = request.send();
		
		//결과(JSON 형태) 반환
		return response.getBody();
		
	}
}
