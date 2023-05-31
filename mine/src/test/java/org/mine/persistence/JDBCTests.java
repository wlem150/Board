package org.mine.persistence;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.ibatis.javassist.bytecode.stackmap.BasicBlock.Catch;
import org.junit.Test;

import lombok.extern.log4j.Log4j;

@Log4j
public class JDBCTests {
	static {
		try {
			 Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testConnection() {
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","ForStudy","ForStudy");;
			log.info(con);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
