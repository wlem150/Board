package org.mine.service;

import java.util.List;

import org.mine.domain.BoardVO;

public interface BoardService {
	public void register(BoardVO board);
	public boolean modify(BoardVO board);
	public boolean remove(long bno);
	public BoardVO get(long bno);
	public List<BoardVO> getList();
}