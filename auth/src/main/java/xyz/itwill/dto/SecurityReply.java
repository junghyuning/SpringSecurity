package xyz.itwill.dto;

import lombok.Data;

/*
create table security_reply(idx number primary key, writer varchar2(100)
    , content varchar2(1000), regdate date, board_idx number
    , constraint reply_borad_idx_fk foreign key(board_idx) references security_board(idx) on delete cascade);
    
create sequence security_reply_seq;
*/

@Data
public class SecurityReply {
	private int idx;
	private String writer;
	private String content;
	private String regdate;
	private int boardIdx;
	//SECURITY_USERS ���̺� ����� ��� �ۼ����� �̸��� �����ϱ� ���� �ʵ�
	private String name;
}