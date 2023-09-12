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
	//�α��ο� �ʿ��� ������ ������ �ʵ�(���) ����
	private static final String CLINT_ID="mHXwNpAULbrozZWrhDo2";
	private static final String CLINT_SECRET="totvPNJ11j";
	private static final String REDIRECT_URI="http://localhost:8000/auth/naver/callback";
	private static final String SESSION_STATE="naver_state";
	private static final String PROFILE_ID="https://openapi.naver.com/v1/nid/me";
	private static final String PROFILE_API_URI = null;
	
	//���̹� �α��� ����� ����Ͽ� ��ū�� �߱޹ޱ����� �޼���
	public String getAuthorizationUrl(HttpSession session) {
		//������ ��ȿ�� ������ ���� �������� �߻��Ͽ� ����
		String state = UUID.randomUUID().toString();
		
		//�������� ���� �Ӽ������� ����
		session.setAttribute(SESSION_STATE, state);
		
		//Scribe ���̺귯���� Ŭ������ ��ū�� �߱޹ޱ����� API�� URL �ּҰ� ����� ��ü ����
		OAuth20Service oAuth20Service = new ServiceBuilder()
				.apiKey(CLINT_ID)
				.apiSecret(CLINT_SECRET)
				.callback(REDIRECT_URI)
				.state(state)
				.build(NaverLoginApi.instance());
		
		//���̹� �α��� ����� ��û�ϴ� API �ּ� ��ȯ
		return oAuth20Service.getAuthorizationUrl();
	}
	
	//�α��� ó�� �� �ݹ� ����� �̿��Ͽ� �α��� ������� ���� ��ū�� �߱޹޾� ��ȯ�ϱ����� �޼���
	public OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws IOException{
		//������ ��ȿ�� ������ ���� ���ǿ� ����� �Ӽ���(����)�� ��ȯ�޾� ����
		String SessionState = (String)session.getAttribute(SESSION_STATE);
		
		//�Ű������� ���޹��� ���� ���ǿ� ����� ���� �ٸ� ��� - ���������� ��û
		if(StringUtils.pathEquals(SessionState, state)) {
			return null;
		}
		
		OAuth20Service oAuth20Service = new ServiceBuilder()
				.apiKey(CLINT_ID)
				.apiSecret(CLINT_SECRET)
				.callback(REDIRECT_URI)
				.state(state)
				.build(NaverLoginApi.instance());
		
		//����� ���� ��ū�� �߱��ϴ� API�� ��û�Ͽ� ��ū�� �߱޹޾� ����
		OAuth2AccessToken accessToken = oAuth20Service.getAccessToken(code);
		
		return accessToken;
	}
	
	//����� ���� ��ū�� ����Ͽ� ������� �������� �����ϴ� API�� ȣ���ϴ� �޼���
	public String getUserProfile(OAuth2AccessToken accessToken) throws IOException {
		OAuth20Service oAuth20Service = new ServiceBuilder()
				.apiKey(CLINT_ID)
				.apiSecret(CLINT_SECRET)
				.callback(REDIRECT_URI)
				.build(NaverLoginApi.instance());
		
		OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_API_URI, 	oAuth20Service);
		
		oAuth20Service.signRequest(accessToken, request);
		
		//��û������ ���(����� ������)�� �߱޹޾�
		Response response = request.send();
		
		//���(JSON ����) ��ȯ
		return response.getBody();
		
	}
}
