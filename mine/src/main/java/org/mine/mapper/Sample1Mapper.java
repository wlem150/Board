package org.mine.mapper;

import org.apache.ibatis.annotations.Insert;

public interface Sample1Mapper {
	@Insert("insert into tbl_sample (col1) values (#{data})")
	public int insertCol1(String data);
}
