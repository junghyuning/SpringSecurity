package xyz.itwill.auth;

import java.util.Map;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import lombok.RequiredArgsConstructor;
import xyz.itwill.dto.SecurityBoard;
import xyz.itwill.service.SecurityBoardService;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class SecurityBoardController {
	private final SecurityBoardService securityBoardService;
	
	@RequestMapping("/list")
	public String list(@RequestParam Map<String, Object> map, Model model) {
		 model.addAttribute("result", securityBoardService.getSecurityBoardList(map));
		 model.addAttribute("search", map);
		 return "board/list";
	}

	//@PreAuthorize : ��û ó�� �޼ҵ忡 �ʿ��� ������ �����ϱ� ���� ������̼�
	// => value �Ӽ��� SpEL�� �Ӽ������� ���� - �ٸ� �Ӽ��� ���� ��� �Ӽ����� ���� ����
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String register() {
		return "board/register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String register(@ModelAttribute SecurityBoard board) {
		board.setSubject(HtmlUtils.htmlEscape(board.getSubject()));
		board.setContent(HtmlUtils.htmlEscape(board.getContent()));
		securityBoardService.addSecurityBoard(board);		
		return "redirect:/board/list";
	}
	
	@RequestMapping(value="/detail")
	public String detail(@RequestParam Map<String, Object> map, Model model) {
		int idx = Integer.parseInt((String)map.get("idx"));
		model.addAttribute("securityBoard",securityBoardService.getSecurityBoardByIdx(idx));
		model.addAttribute("search",map);
		return "board/detail";
	}
	
	//�α��� ������� ���̵�� �Խñ� �ۼ��ڰ�  ���� ��쿡�� ��û ó�� �޼��� ȣ
	@PreAuthorize("hasRole('ROLE_ADMIN')principal.userid == #map['writer']")
	@RequestMapping(value="/modify")
	public String modify(@RequestParam Map<String, Object> map, Model model) {
		int idx = Integer.parseInt((String)map.get("idx"));
		model.addAttribute("securityBoard",securityBoardService.getSecurityBoardByIdx(idx));
		model.addAttribute("search", map);
		return "board/modify";
	}
}














