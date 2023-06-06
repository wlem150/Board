package org.mine.mapper;

import java.util.List;

import org.mine.domain.BoardVO;
import org.mine.domain.Criteria;

public interface BoardMapper {
	public List<BoardVO> getList();
	public void insert(BoardVO board);
	public void insertSelectKey(BoardVO board);
	public BoardVO read(Long bno);
	public long delete(Long bno);
	public long update(BoardVO board);
	
	public List<BoardVO> getListWithPaging(Criteria cri);
	public int getTotalCount(Criteria cri);
}