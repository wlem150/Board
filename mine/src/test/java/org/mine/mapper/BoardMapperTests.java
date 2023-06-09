package org.mine.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mine.domain.BoardVO;
import org.mine.domain.Criteria;
import org.mine.domain.ReplyVO;
import org.mine.service.AOPSampleService;
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
	public void test() {
		List<BoardVO> list = mapper.getList();
		list.forEach(board -> log.info(board));
	}
}
