/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse.maven_webmail.model;

import cse.maven_webmail.control.CommandType;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class TrashTransperDAO {
    private static final Logger logger = LoggerFactory.getLogger(TrashTransperDAO.class);
    private DataSource dataSource;
    private String userId;
    private String messageName;
    public String subject;
    private String fileName;
    
    public String getFileName() {
        return fileName;
    }
    
    public void setMessageName(String messageName) {
        this.messageName = messageName;
        logger.info("메일 이름 : {}", messageName);
    }
    
    public String getUserId() {
        return userId;
    }
    
     public void download() {
        Connection con = null;
        ResultSet res = null;    
        String sql = "SELECT message_body FROM trash WHERE message_name = ?";
        try {
            con = (Connection) dataSource.getConnection(); // connection
            PreparedStatement preparedstatement = con.prepareStatement(sql);
            preparedstatement.setString(1, messageName );


            try (ResultSet resultSet = preparedstatement.executeQuery()) {
                if (resultSet.next()) {
                    try (InputStream inputStream = resultSet.getBinaryStream(1)) {
                        TrashTransperDTO trashMessageFormatter = new TrashTransperDTO();
                        trashMessageFormatter.setMailStream(inputStream);
                        trashMessageFormatter.parse();
                        fileName = trashMessageFormatter.getFileName();
                        try (InputStream fileStream = trashMessageFormatter.getFileStream();
                             FileOutputStream fileOutputStream = new FileOutputStream("c:/jsp/" + fileName);
                        ) {
                            byte[] decoded = fileStream.readAllBytes();
                            fileOutputStream.write(decoded);
                        }
                    }
                } else {
                    logger.error("messageName을 못찾았음 : {}", messageName);
                }
            }
        } catch (Exception e) {
                logger.error(e.getMessage());
            } 
    }
    
    public String getSubject(){
        Connection con = null;
        ResultSet res = null;
        String sql = "SELECT  message_body FROM trashbox WHERE message_name= ?";
        try{
                con = (Connection) dataSource.getConnection(); // connection
                PreparedStatement preparedstatement = con.prepareStatement(sql);
                preparedstatement.setString(1, messageName );
                //stmt= con.createStatement();
                res = preparedstatement.executeQuery();
                 //res = stmt.executeQuery("SELECT * FROM trashbox WHERE repository_name= '"+userId+"'"); // run
                while(res.next()){
                    StringBuilder buffer = new StringBuilder();
                    TrashTransperDTO tDTO = new TrashTransperDTO();
                    try (InputStream inputStream = res.getBinaryStream(1)) {
                        tDTO.setMailStream(inputStream);
                        tDTO.parse();
                    }
           
                    buffer.append("보낸 사람 : ").append(tDTO.getFromAddress()).append("<br>");
                    buffer.append("받은 사람 : ").append(tDTO.getToAddress()).append("<br>");
                    buffer.append("Cc : ").append(tDTO.getCc() == null ? "" : tDTO.getCc()).append("<br>");
                    buffer.append("보낸 날짜 : ").append(tDTO.getDate()).append("<br>");
                    buffer.append("제목 : ").append(tDTO.getSubject()).append("<br>");
                    buffer.append("<hr>");
                    buffer.append(tDTO.getBody());
                    buffer.append("<hr>");
                    
                    buffer.append("<form action=\"write_mail.jsp\" method=\"POST\">  ");
                    buffer.append("<input type=\"hidden\" name=\"fromAddress\" value=\"").append(tDTO.getFromAddress());
                    buffer.append("\"/>");
                    buffer.append("<input type=\"hidden\" name=\"toAddress\" value=\"").append( tDTO.getToAddress());
                    buffer.append("\"/>");
                    String toss_sub ="re)"+tDTO.getSubject();
                    buffer.append("<input type=\"hidden\" name=\"subject\" value=\"").append( toss_sub);
                    buffer.append("\"/>");
                    String toss_body =tDTO.getBody()+"\n"+"re>>"+"\n";
                    buffer.append("<input type=\"hidden\" name=\"body\" value=\"").append( toss_body);
                    buffer.append("\"/>");
                    buffer.append("<button type=\"submit\">답장</button>");
                    
                    if (tDTO.getFileName() != null) {
                        buffer.append("<form action=\"trash.do\" method=\"POST\">");
                        buffer.append("<input type=\"hidden\" name=\"menu\" value=\"").append(CommandType.DOWNLOAD_COMMAND).append("\"/>");
                        buffer.append("<input type=\"hidden\" name=\"messageName\" value=\"").append(messageName).append("\"/>");
                        buffer.append("파일 : <input type=\"submit\" value=\"");
                        buffer.append(tDTO.getFileName());
                        buffer.append("\"/>");
                    }
                    buffer.append("</form>");
//                  파일 다운로드 부분 문제   
                   subject=buffer.toString();
                }
                
            }catch (Exception e) {
                logger.error(e.getMessage());
            } 
        return subject; 
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
        logger.info("유저 ID : {}", userId);
        
    }
    public TrashTransperDAO(){
        try{
            //Class.forName(driver);
            Context context = new InitialContext();
            dataSource = (DataSource)context.lookup("java:comp/env/jdbc/JamesWebmail");
        } catch(Exception e){
                logger.error(e.getMessage());
        }
    }
    
    public List<String> select() {
            List<String> list = new LinkedList<>();
            // 연결을 위한 Connection 객체
            Connection con = null;
            // 통신하기 위한 PreparedStatement 객체
           // Statement stmt = null;
            // select 결과 값을 담기 위한 ResultSet 객체
            ResultSet res = null;
            // 실제로 DB에 접근하는 부분
            String sql = "SELECT message_name, message_body FROM trashbox WHERE repository_name= ? ORDER BY last_updated desc ";
            try{
                con = (Connection) dataSource.getConnection(); // connection
                PreparedStatement preparedstatement = con.prepareStatement(sql);
                preparedstatement.setString(1, userId);
                //stmt= con.createStatement();
                res = preparedstatement.executeQuery();
                 //res = stmt.executeQuery("SELECT * FROM trashbox WHERE repository_name= '"+userId+"'"); // run
                while(res.next()){
                    StringBuilder buffer = new StringBuilder();
                    String messageName = res.getString(1);
                    TrashTransperDTO tDTO = new TrashTransperDTO();
                    try (InputStream inputStream = res.getBinaryStream(2)) {
                        tDTO.setMailStream(inputStream);
                        tDTO.parse();
                    }
                    buffer.append("<tr>");
                    buffer.append("<td>");
                    buffer.append(res.getRow());
                    buffer.append("</td>");
                    buffer.append("<td>");
                    
                    buffer.append(tDTO.getFromAddress());
                    buffer.append("</td>");
                    
                    buffer.append("<td>");
                    buffer.append("<form action=\"show_trash_message.jsp\" method=\"POST\">");
                    buffer.append("<input type=\"hidden\" name=\"messageName\" value=\"");
                    buffer.append(URLEncoder.encode(messageName, StandardCharsets.UTF_8)).append("\"/>");
                    buffer.append("<input type=\"submit\" class=\"submitLink\" value=\"");
                    buffer.append(tDTO.getSubject());
                    buffer.append("\"/>");
                    buffer.append("</form>");
                    buffer.append("</td>");
                    
                    buffer.append("<td>");
                    buffer.append(tDTO.getDate().substring(0,tDTO.getDate().length()-12));
                    buffer.append("</td>");
                    
                    buffer.append("<td>");
                    buffer.append("<form action=\"Trash.do\" method=\"POST\">");
                    buffer.append("<input type=\"hidden\" name=\"menu\" value=\"");
                    buffer.append(CommandType.DELETE_MAIL_COMMAND).append("\"/>");
                    buffer.append("<input type=\"hidden\" name=\"messageName\" value=\"");
                    buffer.append(messageName).append("\"/>");
                    buffer.append("<input type=\"submit\" class=\"submitLink\" value=\"");
                    buffer.append("완전 삭제");
                    buffer.append("\"/>");
                    buffer.append("</form>");
                    buffer.append("</td>");
                    
                    buffer.append("<td>");
                    buffer.append("<form action=\"Trash.do\" method=\"POST\">");
                    buffer.append("<input type=\"hidden\" name=\"menu\" value=\"");
                    buffer.append(CommandType.RESTORE_MAIL_COMMAND).append("\"/>");
                    buffer.append("<input type=\"hidden\" name=\"messageName\" value=\"");
                    buffer.append(messageName).append("\"/>");
                    buffer.append("<input type=\"submit\" class=\"submitLink\" value=\"");
                    buffer.append("메세지 복원");
                    buffer.append("\"/>");
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
    
    public boolean trashDel(){
        boolean checkDel;
        String sql = "DELETE FROM trashbox WHERE message_name = ?";
        Connection con = null;
            try{
                con = (Connection) dataSource.getConnection(); // connection
                PreparedStatement preparedstatement = con.prepareStatement(sql);
                preparedstatement.setString(1, messageName);
                preparedstatement.executeUpdate();
                checkDel= true;
            } catch (Exception e) {
                logger.error(e.getMessage());
                checkDel= false;
            }
        return checkDel;
    }
    
    public boolean trashRes(){
        boolean checkRes;
        String sql1 = "INSERT INTO inbox SELECT * FROM trashbox WHERE message_name= ?";
        String sql2 = "DELETE FROM trashbox WHERE message_name = ?";
        Connection con = null;
        try{
                con = (Connection) dataSource.getConnection(); // connection
                PreparedStatement restoreStatement = con.prepareStatement(sql1);
                PreparedStatement deleteStatement = con.prepareStatement(sql2);
              
                restoreStatement.setString(1, messageName);
                restoreStatement.executeUpdate();
                
                deleteStatement.setString(1, messageName);
                deleteStatement.executeUpdate();
                
                checkRes= true;
            } catch (Exception e) {
                logger.error(e.getMessage());
                checkRes= false;
            } 
        return checkRes;
    }
}
