package xyz.itwill.security;

import com.github.scribejava.core.builder.api.DefaultApi20;

//�α��� ���� API ������ �����ϱ� ���� Ŭ����
// => DefaultApi20 Ŭ������ ��ӹ޾� �ۼ�
public class NaverLoginApi extends DefaultApi20 {
	protected NaverLoginApi() {
		
	}
	
	private static class InstanceHolder {
		private static final NaverLoginApi INSTANCE = new NaverLoginApi();		
	}
	
	public static NaverLoginApi instance() {
		return InstanceHolder.INSTANCE;
	}
	
	//����� ���� ��ū�� �����ޱ� ���� API�� URL �ּҸ� ��ȯ�ϴ� �޼ҵ�
	@Override
	public String getAccessTokenEndpoint() {
		return "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code";
	}

	//�α��� ó���� ���� API�� URL �ּҸ� ��ȯ�ϴ� �޼ҵ�
	@Override
	protected String getAuthorizationBaseUrl() {
		return "https://nid.naver.com/oauth2.0/authorize";
	}
	
}