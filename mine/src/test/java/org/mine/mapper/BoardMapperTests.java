package org.mine.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mine.domain.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTests {
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;
	
	@Test
	public void update() {
		BoardVO board = new BoardVO();
		board.setContent("업데이트");
		board.setWriter("업데이트");
		board.setTitle("업데이트");
		board.setBno(2L);
		log.info(board);
		mapper.update(board);
		
	}

}
