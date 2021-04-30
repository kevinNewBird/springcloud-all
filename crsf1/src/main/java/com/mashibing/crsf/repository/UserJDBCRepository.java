package com.mashibing.crsf.repository;

import com.mashibing.crsf.entity.CusUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

/***********************
 * @Description: 用户操作类 <BR>
 * @author: zhao.song
 * @since: 2021/4/29 15:22
 * @version: 1.0
 ***********************/
@Repository
public class UserJDBCRepository {


    //5.2.6.RELEASE该类被移除,支持到5.2.2.RELEASE
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public CusUser findByUsername(String username) {
        return jdbcTemplate.queryForObject("select username,password,enabled from users where username='" + username + "'"
                , new BeanPropertyRowMapper<>(CusUser.class));
    }

    public void test() throws ClassNotFoundException, SQLException {
        //注册驱动
        Class.forName("com.mysql.jdbc.Driver");

        String url = "jdbc:mysql:///test00?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false";

        Connection conn = DriverManager.getConnection(url, "root", "123456");

        PreparedStatement pstat = conn.prepareStatement("select * from users");
        boolean execute = pstat.execute();
        ResultSet resultSet = pstat.getResultSet();
        while (resultSet.next()) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            System.out.println(metaData.getColumnCount());
            System.out.println(metaData.getColumnName(1));
            System.out.println(resultSet.getString("username"));
        }

    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        new UserJDBCRepository().test();
    }

}
