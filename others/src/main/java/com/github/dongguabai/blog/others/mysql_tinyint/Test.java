package com.github.dongguabai.blog.others.mysql_tinyint;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author dongguabai
 * @date 2023-11-30 00:27
 */
public class Test {

    public static String url = "jdbc:mysql://xxxx.151:3306/test_1";
    public static String user = "root";
    public static String password = "root";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS tinyint_test");
            stmt.execute("CREATE TABLE tinyint_test (id TINYINT UNSIGNED, id_signed TINYINT, id_boolean TINYINT(1))");
            stmt.execute("INSERT INTO tinyint_test VALUES (255, -128, 1)");
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM tinyint_test")) {
                while (rs.next()) {
                    System.out.println("id: " + rs.getObject("id").getClass().getName());
                    System.out.println("id_signed: " + rs.getObject("id_signed").getClass().getName());
                    System.out.println("id_boolean: " + rs.getObject("id_boolean").getClass().getName());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}