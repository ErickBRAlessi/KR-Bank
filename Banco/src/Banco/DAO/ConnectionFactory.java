/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.DAO;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Erick Alessi
 */
public class ConnectionFactory {
    final private static String urlMysql = "jdbc:mysql://localhost:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false&characterEncoding=utf-8";
    final private static String userMysql = "root";
    final private static String passwordMysql = "admin";

    static public Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(urlMysql, userMysql, passwordMysql);
//            Class.forName("org.postgresql.Driver");
//            con = DriverManager.getConnection(urlPost, userPost, passwordPost);
//            Class.forName("com.mysql.jdbc.Driver");
//            con = DriverManager.getConnection(urlFreeMysql, userFreeMysql, passwordFreeMysql);

        } catch (Exception e) {
            System.out.println("NÃO CONECTO");
            e.printStackTrace();
        }
        return con;
    }
}
