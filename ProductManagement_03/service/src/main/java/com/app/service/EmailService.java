package com.app.service;

import com.app.exception.MyException;
import com.app.model.Order;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import static j2html.TagCreator.*;


public class EmailService {
    private static final String emailAddress = "janeknazar777@gmail.com";
    private static final String emailPassword = "nazar777";

    public void sendAsHtml(String to, String title, List<Order> orders) {
        try {
            System.out.println("Sending email ...");
            Session session = createSession();
            MimeMessage message = new MimeMessage(session);
            prepareEmailMessage(message, to, title, generateHtmlContent(orders));
            Transport.send(message);
            System.out.println("Email has been sent");
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("send as html exception: " + e.getMessage());
        }
    }

    private String generateHtmlContent(List<Order> orders) {
        return html(
                head(title("Your orders")),
                body(
                        h1("Your orders"),
                        table(
                                thead(th("Date"), th("Name"), th("Total price")),
                                tbody(
                                        each(orders, o -> tr(
                                                td(o.getEstimatedRealizationDate().toString()),
                                                td(o.getProduct().getName()),
                                                td(o.getProduct().getPrice().multiply(BigDecimal.valueOf(o.getQuantity())).toString())
                                        ))
                                )
                        )
                )
        ).renderFormatted();
    }

    private void prepareEmailMessage(MimeMessage mimeMessage, String to, String title, String html) {
        try {
            mimeMessage.setContent(html, "text/html; charset=utf-8");
            mimeMessage.setFrom(new InternetAddress(emailAddress));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            mimeMessage.setSubject(title);
        } catch (Exception e) {
            throw new MyException("prepare email message exception: " + e.getMessage());
        }
    }

    private Session createSession() {
        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAddress, emailPassword);
            }
        });
    }
}
