package xyz.itwill.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import xyz.itwill.dto.SecurityReply;
import xyz.itwill.repository.SecurityBoardRepository;
import xyz.itwill.repository.SecurityReplyRepository;
import xyz.itwill.repository.SecurityUsersRepository;

@Service
@RequiredArgsConstructor
public class SecurityReplyServiceImpl implements SecurityReplyService {
	private final SecurityUsersRepository securityUsersRepository;
	private final SecurityBoardRepository securityBoardRepository;
	private final SecurityReplyRepository securityReplyRepository;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addSecurityReply(SecurityReply reply) {
		if(securityUsersRepository.selectSecurityUsersByUserid(reply.getWriter()) == null) {
			new IllegalArgumentException("�ۼ��ڸ� ã�� �� �����ϴ�.");
		}
		securityReplyRepository.insertSecurityReply(reply);
	}

	@Override
	public List<SecurityReply> getSecurityReplyList(int boardIdx) {
		if(securityBoardRepository.selectSecurityBoardByIdx(boardIdx) == null) {
			new IllegalArgumentException("�Խñ��� ã�� �� �����ϴ�.");
		}
		return securityReplyRepository.selectSecurityReplyList(boardIdx);
	}
}