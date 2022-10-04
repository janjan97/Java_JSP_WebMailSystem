/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cse.maven_webmail.model;

import cse.maven_webmail.control.CommandType;
import java.util.ArrayList;
import javax.mail.Message;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author jongmin
 */
public class MessageFormatter {

    private String userid;  // 파일 임시 저장 디렉토리 생성에 필요
    private HttpServletRequest request = null;

    public MessageFormatter(String userid) {
        this.userid = userid;
    }

    public String getMessageTable(Message[] messages) {
        StringBuilder buffer = new StringBuilder();

        // 메시지 제목 보여주기
        buffer.append("<table>");  // table start
        buffer.append("<tr> "
                + " <th> No. </td> "
                + " <th> 보낸 사람 </td>"
                + " <th> 제목 </td>     "
                + " <th> 보낸 날짜 </td>   "
                + " <th> 삭제 </td>   "
                + " </tr>");
        int count =0;
        for (int i = messages.length - 1; i >= 0; i--) {
            MessageParser parser = new MessageParser(messages[i], userid);
            parser.parse(false);  // envelope 정보만 필요
            // 메시지 헤더 포맷
            // 추출한 정보를 출력 포맷 사용하여 스트링으로 만들기
          
            //내게쓴 메일 안보이게 하기 -혁인
            if(!userid.equals(parser.getFromAddress())){
                count+=1;
                buffer.append(
                         " <td id=no>" + count + " </td> "
                        + " <td id=sender>" + parser.getFromAddress() + "</td>"
                        + " <td id=subject> "
                        + " <a href=show_message.jsp?msgid=" + (i + 1) + " title=\"메일 보기\"> "
                        + parser.getSubject() + "</a> </td>"
                        + " <td id=date>" + parser.getSentDate() + "</td>"
                        + " <td id=delete>"
                        + "<a href=ReadMail.do?menu="
                        + CommandType.DELETE_MAIL_COMMAND
                        + "&msgid=" + (i + 1) + "> 삭제 </a>" + "</td>"
                        + " </tr>");
            }
        }
        buffer.append("</table>");

        return buffer.toString();
//        return "MessageFormatter 테이블 결과";
    }
    
        // 내게 쓴 메일함의 내용을 보여주기 위한 메소드(By J.)
        public String getMyMessageTable(Message[] messages, ArrayList<Integer> test) {
        StringBuilder buffer = new StringBuilder();
        int j = 0;

        // 메시지 제목 보여주기
        buffer.append("<table>");  // table start
        buffer.append("<tr> "
                + " <th> No. </td> "
                + " <th> 보낸 사람 </td>"
                + " <th> 제목 </td>     "
                + " <th> 보낸 날짜 </td>   "
                + " <th> 삭제 </td>   "
                + " </tr>");

        for (int i = messages.length - 1; i >= 0; i--) {
            j = test.get(i);    // Parameter로 받은 내게 쓴 메일의 index값을 하나씩 가져옴.
            MessageParser parser = new MessageParser(messages[i], userid);
            parser.parse(false);  // envelope 정보만 필요
            // 메시지 헤더 포맷
            // 추출한 정보를 출력 포맷 사용하여 스트링으로 만들기
            String cmpId= parser.getFromAddress();

            buffer.append("<tr> "
                    + " <td id=no>" + (i + 1) + " </td> "
                    + " <td id=sender>" + parser.getFromAddress() + "</td>"
                    + " <td id=subject> "
                    + " <a href=show_message.jsp?msgid=" + j + " title=\"메일 보기\"> "
                    + parser.getSubject() + "</a> </td>"
                    + " <td id=date>" + parser.getSentDate() + "</td>"
                    + " <td id=delete>"
                    + "<a href=ReadMail.do?menu="
                    + CommandType.DELETE_MAIL_COMMAND
                    + "&msgid=" + j + "> 삭제 </a>" + "</td>"
                    + " </tr>");
            }
        buffer.append("</table>");

        return buffer.toString();
//        return "MessageFormatter 테이블 결과";
    }

    public String getMessage(Message message) {
        StringBuilder buffer = new StringBuilder();

        // MessageParser parser = new MessageParser(message, userid);
        MessageParser parser = new MessageParser(message, userid, request);
        parser.parse(true);

        buffer.append("보낸 사람: " + parser.getFromAddress() + " <br>");
        buffer.append("받은 사람: " + parser.getToAddress() + " <br>");
        buffer.append("Cc &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : " + parser.getCcAddress() + " <br>");
        buffer.append("보낸 날짜: " + parser.getSentDate() + " <br>");
        buffer.append("제 &nbsp;&nbsp;&nbsp;  목: " + parser.getSubject() + " <br> <hr>");

        buffer.append(parser.getBody());

        String attachedFile = parser.getFileName();
        if (attachedFile != null) {
            buffer.append("<br> <hr> 첨부파일: <a href=ReadMail.do?menu="
                    + CommandType.DOWNLOAD_COMMAND
                    + "&userid=" + this.userid
                    + "&filename=" + attachedFile.replaceAll(" ", "%20")
                    + " target=_top> " + attachedFile + "</a> <br>");
        }
        buffer.append("<form action=\"write_mail.jsp\" method=\"POST\">  ");
        buffer.append("<input type=\"hidden\" name=\"fromAddress\" value=\"").append(parser.getFromAddress());
        buffer.append("\"/>");
        buffer.append("<input type=\"hidden\" name=\"toAddress\" value=\"").append( parser.getToAddress());
        buffer.append("\"/>");
        String toss_sub ="re)"+parser.getSubject();
        buffer.append("<input type=\"hidden\" name=\"subject\" value=\"").append( toss_sub);
        buffer.append("\"/>");
        String toss_body =parser.getBody()+"\n"+"re>>"+"\n";
        buffer.append("<input type=\"hidden\" name=\"body\" value=\"").append( toss_body);
        buffer.append("\"/>");
        buffer.append("<button type=\"submit\">답장</button>");
        buffer.append("</form>");

        return buffer.toString();
    }
    
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
