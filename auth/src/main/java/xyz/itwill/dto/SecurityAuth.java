package xyz.itwill.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // �Ű������� ���� ������
@AllArgsConstructor
public class SecurityAuth {
	private String userid;
	private String auth;
}
