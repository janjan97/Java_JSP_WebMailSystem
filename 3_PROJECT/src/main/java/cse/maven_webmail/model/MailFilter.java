/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse.maven_webmail.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bhi84
 */
public class MailFilter {
    private static final Logger logger = LoggerFactory.getLogger(MailFilter.class);
    private String userId;
    private DataSource dataSource;
    private ArrayList<String> fill = new ArrayList<String>();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        logger.info("유저 ID : {}", userId);

    }
    
    public void fillter(){
        String sql1 = "SELECT banId FROM bans where registId=?";
        Connection con = null;
        ResultSet res = null;
            try{
                con = (Connection) dataSource.getConnection(); // connection
                PreparedStatement preparedstatement = con.prepareStatement(sql1);
                preparedstatement.setString(1, userId);
                
                res = preparedstatement.executeQuery();
                while(res.next()){
                    System.out.println("asmr" );
                   fill.add(res.getString(1));

                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            } finally {
                try {
                    if(con != null) con.close();
                } catch (Exception e2) {
                    logger.error(e2.getMessage());
                }
            }
        String sql2 = "DELETE FROM inbox where sender=(select distinct sender from inbox where repository_name=?) and repository_name =?";
        try{
                con = (Connection) dataSource.getConnection(); // connection
                for(int i = 0; i<fill.size();i++){
                    PreparedStatement preparedstatement = con.prepareStatement(sql2);
                    preparedstatement.setString(1, fill.get(i));
                    preparedstatement.setString(2, userId);
                    preparedstatement.executeUpdate();
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            } finally {
                try {
                    if(con != null) con.close();
                } catch (Exception e2) {
                   logger.error(e2.getMessage());
                }
            }
    }

    public MailFilter() {
        try{
            //Class.forName(driver);
            Context context = new InitialContext();
            dataSource = (DataSource)context.lookup("java:comp/env/jdbc/JamesWebmail");
        } catch(Exception e){
                logger.error(e.getMessage());
        }
    }
    
}
