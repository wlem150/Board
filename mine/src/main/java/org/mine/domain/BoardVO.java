package org.mine.domain;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class BoardVO {
	private Long bno;
	private String title;
	private String writer;
	private String content;
	private Date regDate;
	private Date updateDate;
	private int replyCnt;
	
	private List<BoardAttachVO> attachList;
	
}
