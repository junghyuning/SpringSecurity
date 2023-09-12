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
	
	//�α��� ��ū�� ����Ͽ� �α��� ���� ó���ϴ� API URL �ּҸ� ��ȯ�ϴ� �޼���
	@Override
	public String getAccessTokenEndpoint() {
		// TODO Auto-generated method stub
		return "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code";
	}
	
	//�α��� ��ū�� �߱޹ޱ����� API�� URL �ּҸ� ��ȯ�ϴ� �޼���
	@Override
	protected String getAuthorizationBaseUrl() {
		// TODO Auto-generated method stub
		return "https://nid.naver.com/oauth2.0/authorize";
	}

}
