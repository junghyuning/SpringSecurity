package xyz.itwill.security;

import com.github.scribejava.core.builder.api.DefaultApi20;

public class NaverLoginApi extends DefaultApi20 {
	public NaverLoginApi() {
		// TODO Auto-generated constructor stub
	}
	
	private static class InstanceHolder{
		private static final NaverLoginApi INSTANCE = new NaverLoginApi();
	}
	
	public static NaverLoginApi instance() {
		return InstanceHolder.INSTANCE;
	}
	
	//로그인 토큰을 사용하여 로그인 인증 처리하는 API URL 주소를 반환하는 메서드
	@Override
	public String getAccessTokenEndpoint() {
		// TODO Auto-generated method stub
		return "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code";
	}
	
	//로그인 토큰을 발급받기위한 API의 URL 주소를 반환하는 메서드
	@Override
	protected String getAuthorizationBaseUrl() {
		// TODO Auto-generated method stub
		return "https://nid.naver.com/oauth2.0/authorize";
	}

}
