package org.mine.service;

import java.util.List;

import org.mine.domain.Criteria;
import org.mine.domain.ReplyPageDTO;
import org.mine.domain.ReplyVO;
import org.mine.mapper.ReplyMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class ReplyServiceImpl implements ReplyService {
	private ReplyMapper mapper;
	
	@Override
	public int register(ReplyVO vo) {
		return mapper.insert(vo);
	}

	@Override
	public ReplyVO get(long rno) {
		return mapper.read(rno);
	}

	@Override
	public int modify(ReplyVO vo) {
		return mapper.update(vo);
	}

	@Override
	public int remove(long rno) {
		return mapper.delete(rno);
	}

	@Override
	public List<ReplyVO> getListWithPaging(Criteria cri, long bno) {
		return mapper.getListWithPaging(cri, bno);
	}

	@Override
	public ReplyPageDTO getListPage(Criteria cri, long bno) {
		return new ReplyPageDTO(mapper.getCountByBno(bno), mapper.getListWithPaging(cri, bno));
	}	
}
