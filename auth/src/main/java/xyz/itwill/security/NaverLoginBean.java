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
	private static final String CLIENT_ID="xi6thXCdpMBmXIrqcirU";
	private static final String CLIENT_SAECRET="HJJjPBiVan";
	private static final String REDIRECT_URI="http://localhost:8000/auth/naver/callback";
	private static final String SESSION_STATE="naverState";
	//����� �������� ��ȸ�ϱ� ���� API�� URL �ּ� ����
	private static final String PROFILE_API_URI="https://openapi.naver.com/v1/nid/me";
	
	//���̹� �α��� ����� �����ϴ� API�� ȣ���Ͽ� ���(code�� state)�� ��ȯ�ϴ� �޼ҵ� 
	public String getAuthorizationUrl(HttpSession session) {
		//������ ��ȿ�� ������ ���� �������� �߻��Ͽ� ���� - Ŭ���̾�Ʈ�� ���������� �˻��ϱ� ���� ��
		String state=UUID.randomUUID().toString();
		
		//�������� ���� �Ӽ������� ���� - CSRF ��ū�� ������ ��Ȱ
		session.setAttribute(SESSION_STATE, state);
		
		//�α��� ����� ��û�ϱ� ���� ������ ����� OAuth20Service ��ü ����
		OAuth20Service oAuth20Service=new ServiceBuilder()
				.apiKey(CLIENT_ID)
				.callback(REDIRECT_URI)
				.state(state)
				.build(NaverLoginApi.instance());
		
		//���̹� �α��� ����� ��û�ϴ� API�� ��û�Ͽ� ����� ��ȯ
		// => Callback URL �ּҿ� ���ǹ��ڿ��� code�� state �̸����� ���� ����� URL �ּ� ��ȯ 
		return oAuth20Service.getAuthorizationUrl();
	}
	
	//�α��� ó���� �α��� ������� ���� ��ū�� �߱޹޴� API�� ȣ���Ͽ� ����� ���� ��ū�� ��ȯ�ϱ� ���� �޼ҵ�
	public OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws IOException {
		//������ ��ȿ�� ������ ���� ���ǿ� ����� �Ӽ���(����)�� ��ȯ�޾� ����
		String sessionState=(String)session.getAttribute(SESSION_STATE);
		
		//�Ű������� ���޹��� ���� ���ǿ� ����� ���� �ٸ� ���
		// => �α��� ��û Ŭ���̾�Ʈ�� ��ū ��û Ŭ���̾�Ʈ�� �ٸ� ��� - ���������� ��û
		if(!StringUtils.pathEquals(sessionState, state)) {
			return null;
		}
		
		//����� ���� ��ū�� �߱� �ޱ� ���� ������ ����� OAuth20Service ��ü ����
		OAuth20Service oAuth20Service=new ServiceBuilder()
				.apiKey(CLIENT_ID)
				.apiSecret(CLIENT_SAECRET)
				.callback(REDIRECT_URI)
				.state(state)
				.build(NaverLoginApi.instance());
		
		//����� ���� ��ū�� �߱��ϴ� API�� ��û�Ͽ� ��ū�� �߱޹޾� ���� 
		OAuth2AccessToken accessToken=oAuth20Service.getAccessToken(code);
		
		return accessToken;
	}
	
	//����� ���� ��ū�� ����Ͽ� ������� �������� �����ϴ� API�� ȣ���ϴ� �޼ҵ�
	public String getUserProfile(OAuth2AccessToken accessToken) throws IOException {
		OAuth20Service oAuth20Service=new ServiceBuilder()
				.apiKey(CLIENT_ID)
				.apiSecret(CLIENT_SAECRET)
				.callback(REDIRECT_URI)
				.build(NaverLoginApi.instance());
		
		//������� �������� �����ϴ� API�� ��û�ϱ� ���� ��ü ����
		OAuthRequest request=new OAuthRequest(Verb.GET, PROFILE_API_URI, oAuth20Service);
		
		//����� ���� ��ū�� API ��û ��ü�� �����Ͽ� �α��� ����� ������ ��û
		oAuth20Service.signRequest(accessToken, request);
		
		//��û�� ���� ���(����� ������)�� ����޾� ����
		Response response=request.send();
		
		//���(JSON) ��ȯ
		return response.getBody();
	}
}