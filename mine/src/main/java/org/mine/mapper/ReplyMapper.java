package org.mine.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mine.domain.Criteria;
import org.mine.domain.ReplyVO;

public interface ReplyMapper {
	public int insert(ReplyVO vo);
	public ReplyVO read(long rno);
	public int delete(long rno);
	public int update(ReplyVO reply);
	public List<ReplyVO> getListWithPaging(
			@Param("cri") Criteria cri,
			@Param("bno") long bno
			);
	public int getCountByBno(long bno);
}
