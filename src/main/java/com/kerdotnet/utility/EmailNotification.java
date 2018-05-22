package com.kerdotnet.utility;

import com.kerdotnet.entity.BookItem;
import com.kerdotnet.entity.User;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;

/**
 * Send emails to user
 * Yevhen Ivanov, 2018-05-22
 */
public class EmailNotification {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotification.class);

    public static void sendEmailToUser(User user, BookItem bookItem) throws EmailException {
        sendEmail(user,"Weblibrary notification: please return books to the library", getTextBody(user, bookItem));
    }

    private static void sendEmail(User user, String subject, String body) throws EmailException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("email");

        SimpleEmail email = new SimpleEmail();
        email.setSmtpPort(587);
        email.setAuthenticator(new DefaultAuthenticator(resourceBundle.getString("email.username"),
                resourceBundle.getString("email.password")));
        email.setDebug(false);
        email.setHostName(resourceBundle.getString("email.smtp"));
        email.setFrom(resourceBundle.getString("email.setfrom"));
        email.setSubject(subject);
        email.setMsg(body);
        email.addTo(user.getEmail());
        LOGGER.debug("Email to is: " + user.getEmail());
        email.setTLS(true);
        email.send();
    }

    private static String getTextBody(User user, BookItem bookItem){
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(user.getFirstName()).append(" ")
                .append(user.getLastName()).append(",").append("\n").append("\n")
                .append("Please be informed that the book ").append(bookItem.getDescription())
                .append(" has to be returned. The term of reading has expired.").append("\n")
                .append("If the book will not be returned in 3 days, ")
                .append("you will be charged the cost of the new book.")
                .append("\n").append("\n")
                .append("Best Regards,").append("\n")
                .append("Administration");
        return sb.toString();
    }

}
