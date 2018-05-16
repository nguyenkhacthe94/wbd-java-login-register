package com.codegym.service;
import com.codegym.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserService {
    public String authorizeLogin(User user) {
        String username = user.getUsername();
        String password = user.getPassword();

        String dbusername = "";
        String dbpassword = "";

        String encodePassword = encodeMD5(password);

        try {
            Connection con = ConnectionUtil.getConnection("localhost", "root", "aptx4869", "mydb", "3306");

            PreparedStatement pstmt = null; //create statement

            pstmt = con.prepareStatement("select * from users where username=? and password=?");
            pstmt.setString(1, username);
            pstmt.setString(2, encodePassword);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                dbusername = rs.getString("username");
                dbpassword = rs.getString("password");

                if (username.equals(dbusername) && encodePassword.equals(dbpassword)) {
                    return "SUCCESS LOGIN";
                }
            }

            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "WRONG USERNAME AND PASSWORD";
    }
    public String authorizeRegister(User user) {
        String firstname = user.getFirstName();
        String lastname = user.getLastName();
        String username = user.getUsername();
        String password = user.getPassword();

        try {
            Connection con = ConnectionUtil.getConnection("localhost", "root", "aptx4869", "mydb", "3306");

            PreparedStatement pstmt = null;

            pstmt = con.prepareStatement("insert into users(firstname,lastname,username,password) values(?,?,?,md5(?))");
            pstmt.setString(1, firstname);
            pstmt.setString(2, lastname);
            pstmt.setString(3, username);
            pstmt.setString(4, password);
            pstmt.executeUpdate();

            pstmt.close();
            con.close();
            return "SUCCESS REGISTER";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "FAIL REGISTER";
    }
    public String encodeMD5(String password) {
        StringBuffer sb = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());

            byte byteData[] = md.digest();

            sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
