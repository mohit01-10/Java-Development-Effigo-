package com.my.webapps.jpa_and_hibernate_postgres.course.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.my.webapps.jpa_and_hibernate_postgres.course.Subject;

@Repository
public class CourseJdbcRepository {
	
	@Autowired
    private JdbcTemplate springJdbcTemplate;

    private static final String IQ = """
            insert into mytestdb.courses.subject(id , name, author)
            values(?, ?, ?);
            """;
    
    private static final String DQ = """
            delete from mytestdb.courses.subject 
            where id = ?
            """;
    private static final String SQ = """
            select * from mytestdb.courses.subject 
            where id = ?
            """;


    public void insert(Subject subject) {
        springJdbcTemplate.update(IQ, subject.getId(),subject.getName(), subject.getAuthor());  // Correctly passing the SQL string
    }
    
    public void deleteById(long id) {
        springJdbcTemplate.update(DQ, id);  // Correctly passing the SQL string
    }
    
    public Subject findById(long id) {
        return springJdbcTemplate.queryForObject(SQ, new BeanPropertyRowMapper<>(Subject.class), id);  // Correctly passing the SQL string
    }

}
