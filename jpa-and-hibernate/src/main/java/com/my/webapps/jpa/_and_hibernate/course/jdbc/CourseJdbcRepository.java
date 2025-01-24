package com.my.webapps.jpa._and_hibernate.course.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CourseJdbcRepository {

	private JdbcTemplate springJdbcTemplate;
	private static String IQ = """
			insert into course(id , namee, author)
			values(1, 'Abc', 'me');
			""";
	
	public void insert() {
		
		springJdbcTemplate.update("IQ");
		
	}
}
