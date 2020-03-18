package com.doArtShow.controls.manager;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.doArtShow.controls.Controller;
import com.doArtShow.dao.ManagerDao;
import com.doArtShow.dto.PersonalRequestDto;

public class PersonalRequestController implements Controller {
	ManagerDao managerDao;
	
	public PersonalRequestController setManagerDao(ManagerDao managerDao) {
		this.managerDao = managerDao;
		
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		System.out.println("### PersonalRequestController ###");
		
		String req = (String) model.get("req");
		
		if (req.equals("page")) {
			List<PersonalRequestDto> personalRequestList = managerDao.getPersonalRequestList();
			
			model.put("personalRequestList", personalRequestList);
			
			return "/manager/reqresp/reqresp.jsp";
		} else if (req.equals("resp")) {
			int reqNo = Integer.parseInt((String) model.get("reqNo"));
			
			PersonalRequestDto prDto = managerDao.getPersonalRequest(reqNo);
			
			model.put("prDto", prDto);
			
			return "/manager/reqresp/responseEmail.jsp";
		} else if (req.equals("respEmail")) {	
			String respEmail = (String) model.get("respEmail");
			String respSubject = (String) model.get("respSubject");
			String respMessage = (String) model.get("respMessage");

			final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
			
			// 이메일 객체생성하기
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", "smtp.cafe24.com");
			props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.port", "587");
			props.setProperty("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.auth", "true");
			props.put("mail.debug", "true");
			props.put("mail.store.protocol", "pop3");
			props.put("mail.transport.protocol", "smtp");
			final String username = "doartshow@rladudrl286.cafe24.com";
			final String password = "wjstl:go12";

			
			try{
			    Session session = Session.getDefaultInstance(props, new Authenticator(){
			    	protected PasswordAuthentication getPasswordAuthentication() {
			    		return new PasswordAuthentication(username, password);
			    	}
			    });

				//메세지 설정
				Message msg = new MimeMessage(session);
	
				//보내는사람 받는사람 설정
				msg.setFrom(new InternetAddress("doartshow@rladudrl286.cafe24.com"));

				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(respEmail,false));

				msg.setSubject(respSubject);

				msg.setText(respMessage);
				msg.setSentDate(new Date());
				Transport.send(msg);

			} catch (MessagingException error){ 
				error.printStackTrace();
			}
			
			int reqNo = Integer.parseInt((String) model.get("reqNo"));
			
			int res = managerDao.updatePersonalRequest(reqNo, respMessage);			

			List<PersonalRequestDto> personalRequestList = managerDao.getPersonalRequestList();
			
			model.put("personalRequestList", personalRequestList);
			
			return "/manager/reqresp/reqresp.jsp";
		} else {
			String name = (String) model.get("name");
			String email = (String) model.get("email");
			String message = (String) model.get("message");
			
			int res = managerDao.insertRequest(name, email, message);
			
			return "/client/support/support.jsp";			
		}
	}

}
