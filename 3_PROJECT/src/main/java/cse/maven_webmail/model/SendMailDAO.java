/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse.maven_webmail.model;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bhi84
 */
public class SendMailDAO {
    private static final Logger logger = LoggerFactory.getLogger(TrashTransperDAO.class);
    private DataSource dataSource;
    private String userId;
    private String messageName;
    public String subject;
    public SendMailDAO() {
        try{
            //Class.forName(driver);
            Context context = new InitialContext();
            dataSource = (DataSource)context.lookup("java:comp/env/jdbc/JamesWebmail");
        } catch(Exception e){
                e.printStackTrace();
        }
    }
    
    public void setMessageName(String messageName) {
        logger.info("메일 제목 : {}", messageName);

        this.messageName = messageName;
    }
   
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getSubject(){
        Connection con = null;
        ResultSet res = null;
        String sql = "SELECT  message_body FROM inbox WHERE message_name= ?";
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
                    buffer.append("</form>");
//                  파일 다운로드 부분 문제   
                   subject=buffer.toString();
                }
                
            }catch (Exception e) {
                logger.error(e.getMessage());
            } finally {
                try {
                    if(res != null) res.close();
                    //if(stmt != null) stmt.close();
                    if(con != null) con.close();
                } catch (Exception e2) {
                    logger.error(e2.getMessage());
                }
            }
        return subject; 
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
            String sql = "select message_name, message_body from inbox where sender=(select distinct sender from inbox where SUBSTRING_INDEX(sender, '@', 1)  =?) and repository_name !=? order by last_updated desc";
            try{
                con = (Connection) dataSource.getConnection(); // connection
                PreparedStatement preparedstatement = con.prepareStatement(sql);
                preparedstatement.setString(1, userId);
                preparedstatement.setString(2, userId);
                //stmt= con.createStatement();
                res = preparedstatement.executeQuery();
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
                    buffer.append(tDTO.getToAddress());
                    buffer.append("</td>");
                    
                    buffer.append("<td>");
                    buffer.append("<form action=\"show_send_message.jsp\" method=\"POST\">");
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
                     list.add(buffer.toString());
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            } finally {
                try {
                    if(res != null) res.close();
                    //if(stmt != null) stmt.close();
                    if(con != null) con.close();
                } catch (Exception e2) {
                    logger.error(e2.getMessage());
                }
            }
            return list;
    }
}
