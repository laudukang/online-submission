package me.laudukang.persistence.service.impl;

import me.laudukang.persistence.service.ICustomEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/12
 * <p>Time: 14:44
 * <p>Version: 1.0
 */
@Service
public class CustomEmailService implements ICustomEmailService {
    @Autowired
    private MailSender mailSender;
    @Autowired
    private SimpleMailMessage simpleMailMessage;

    //@Async
    @Override
    public void send(String toAddress, String subject, String content) {
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        try {
            mailSender.send(simpleMailMessage);
        } catch (MailException ex) {
            try {
                Thread.sleep(1000 * 5);//5s后重发一次
                mailSender.send(simpleMailMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
        }
    }
}
