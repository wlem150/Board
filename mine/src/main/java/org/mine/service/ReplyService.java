package org.mine.service;

import java.util.List;

import org.mine.domain.Criteria;
import org.mine.domain.ReplyPageDTO;
import org.mine.domain.ReplyVO;

public interface ReplyService {
	public int register(ReplyVO vo);
	public ReplyVO get(long rno);
	public int modify(ReplyVO vo);
	public int remove(long rno);
	public List<ReplyVO> getListWithPaging(Criteria cri, long bno);
	
	public ReplyPageDTO getListPage(Criteria cri, long bno);
}
