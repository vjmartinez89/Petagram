package com.vjmartinez.petagram.utils;

import android.util.Log;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

public class EmailUtils {

    /**
     * Send a e-mail whit the message text to the recipient email
     * @param recipient The recipient email
     * @param message The message
     * @param subject The email subject
     * @return true if send is successful, false otherwise
     */
    public static boolean sendMail(String recipient, String message, String subject){
        Properties props = new Properties();
        props.put("mail.smtp.host", "my-mail-server");
        Session session = Session.getInstance(props, null);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom("petagram@vjmartinez.com");
            msg.setRecipients(Message.RecipientType.TO,
                    recipient);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(message);
            Transport.send(msg /*, "me@example.com", "my-password"*/);
            return true;
        } catch (MessagingException mex) {
            Log.e("ERROR", mex.getMessage(), mex);
            return false;
        }
    }
}
