package xyz.itwill.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 매개변수가 없는 생성자
@AllArgsConstructor
public class SecurityAuth {
	private String userid;
	private String auth;
}
