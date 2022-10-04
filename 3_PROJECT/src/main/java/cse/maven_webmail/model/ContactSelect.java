/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse.maven_webmail.model;

import cse.maven_webmail.control.CommandType;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bhi84
 */
public class ContactSelect {
    private String userId;
    private DataSource dataSource;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public List<String> select(){
        List<String> list = new LinkedList<>();
            // 연결을 위한 Connection 객체
            Connection con = null;
            // 통신하기 위한 PreparedStatement 객체
           // Statement stmt = null;
            // select 결과 값을 담기 위한 ResultSet 객체
            ResultSet res = null;
            // 실제로 DB에 접근하는 부분
            String sql = "SELECT username, tel from users where username !=?";
            try{
                con = (Connection) dataSource.getConnection(); // connection
                PreparedStatement preparedstatement = con.prepareStatement(sql);
                preparedstatement.setString(1, userId);
                //stmt= con.createStatement();
                res = preparedstatement.executeQuery();
                while(res.next()){
                    StringBuilder buffer = new StringBuilder();
                    buffer.append("<tr>");
                    buffer.append("<td>");
                    buffer.append(res.getString(1));
                    buffer.append("</td>");
                    
                    buffer.append("<td>");
                    buffer.append(res.getString(2));
                    buffer.append("</td>");
                    
                    buffer.append("<td>");
                    buffer.append("<form action=\"Ban.do\" method=\"POST\">");
                    buffer.append("<input type=\"hidden\" name=\"menu\" value=\"");
                    buffer.append(CommandType.BAN_USER).append("\"/>");
                    buffer.append("<input type=\"hidden\" name=\"userId\" value=\"");
                    buffer.append(userId).append("\"/>");
                    buffer.append("<input type=\"hidden\" name=\"banUser\" value=\"");
                    buffer.append(res.getString(1)).append("\"/>");
                    buffer.append("<input type=\"submit\" class=\"submitLink\" value=\"");
                    buffer.append("차단");
                    buffer.append("\"/>");
                    buffer.append("</form>");
                    buffer.append("</td>");
                    
                    buffer.append("<td>");
                    buffer.append("<form action=\"write_mail.jsp\" method=\"POST\">  ");
                    buffer.append("<input type=\"hidden\" name=\"fromAddress\" value=\"").append(res.getString(1));
                    buffer.append("\"/>");
                    buffer.append("<button type=\"submit\">메일 보내기</button>");
                    buffer.append("</form>");
                     buffer.append("</td>");
                    
                    buffer.append("</tr>");
                    list.add(buffer.toString());
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            } 
            
     return list;
    }
    
    
    private static final Logger logger = LoggerFactory.getLogger(ContactSelect.class);
    public ContactSelect() {
        try{
            //Class.forName(driver);
            Context context = new InitialContext();
            dataSource = (DataSource)context.lookup("java:comp/env/jdbc/JamesWebmail");
        } catch(Exception e){
                logger.error(e.getMessage());
        }
    }
 
    
}
