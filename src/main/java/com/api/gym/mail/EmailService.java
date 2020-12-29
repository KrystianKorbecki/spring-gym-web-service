package com.api.gym.mail;

import com.api.gym.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService implements EmailSender
{
    private JavaMailSender javaMailSender;

    EmailService(JavaMailSender javaMailSender)
    {
        this.javaMailSender = javaMailSender;
    }


    @Override
    public void sendEmail(String to, String title, String content)
    {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setReplyTo("gym@gym.pl");
            helper.setFrom("gym@gym.pl");
            helper.setSubject(title);
            helper.setText(content, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mail);
    }

    public void sendConfirmEmail(String to, String code)
    {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setReplyTo("gym@gym.pl");
            helper.setFrom("gym@gym.pl");
            helper.setSubject("Confirm email");
            helper.setText("localhost:8080/confirm/?code=" + code);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mail);
    }

    public void changePasswordEmail(String to, User user)
    {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setReplyTo("gym@gym.pl");
            helper.setFrom("gym@gym.pl");
            helper.setSubject("Change password");
            helper.setText("Change your password, for user" + user.getUserName() +
                    " " + user.getLastName() + " go to this page localhost:8080/remind/change/" + user.getConfirmationCode(), true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mail);
    }


}

