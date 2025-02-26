/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Asus
 */
public class MailService {
    //Default host (which use Gmail server)

    private static final String HOST = "smtp.gmail.com";
    //Default port (which is port 587)
    private static final int PORT = 587;

    /*
     * Mail server config: 
     * 1. username, which is the FULL email (abc@gmail.com)
     * 2. For security issue, we use app password (visiting https://support.google.com/accounts/answer/185833?hl=en)
     * 3. Properties object, which store all the other config for the mail service
     */
    private final String username;
    private final String password;
    private final Properties properties;

    public MailService() {
        //Load username and password from .env
        Dotenv dotenv = Dotenv.configure()
                .filename(".env") // Ensures it looks for .env in the classpath
                .load();

        this.username = dotenv.get("EMAIL");
        this.password = dotenv.get("APP_PASSWORD");

        this.properties = new Properties();
        this.properties.put("mail.smtp.auth", "true"); //Allow authentication to send email
        this.properties.put("mail.smtp.host", HOST); //Set up the host (we use Gmail for this service: smtp.gtmail.com)
        this.properties.put("mail.smtp.port", PORT); //Set up the port (usually port 587)
        this.properties.put("mail.smtp.starttls.enable", true); //Enable TLS (Transport Layer Security)
    }

    /**
     * Send HTML email to a list of receivers
     *
     * @param to - The list of receiver's email addresses
     * @param subject - The email subject
     * @param path - The path to the HTML email file
     * @throws jakarta.mail.internet.AddressException
     */
    public void send(List<String> to, String subject, String path) throws AddressException, MessagingException {
        //Parse the list of String to array of InternetAddress
        InternetAddress[] addresses = to.stream().map(email -> {
            try {
                return new InternetAddress(email);
            } catch (AddressException e) {
                throw new RuntimeException("Invalid receive email: " + email, e);
            }
        }).toArray(InternetAddress[]::new);
        send(addresses, subject, path);
    }

    /**
     * Send HTML email to a list of receivers.
     *
     * @param tos - The list of receiver's email addresses
     * @param subject - The email's subject
     * @param path - Path to HTML email file
     * @throws jakarta.mail.internet.AddressException - throws AddressException
     * if email addresses are invalid
     */
    public void send(InternetAddress[] tos, String subject, String path) throws AddressException, MessagingException {
        //Read the content file
        String content = Util.readFileFromResources(path);

        //Get session object
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        //Create message 
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(this.username));
        msg.setSubject(subject);
        msg.setRecipients(Message.RecipientType.TO, tos);

        //Create message body
        MimeBodyPart body = new MimeBodyPart();
        body.setContent(content, "text/html; charset=utf-8");

        //Create multipart message
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(body);
        msg.setContent(multipart);

        //Send message
        Transport.send(msg);

        System.out.println("Email sent successfully");
    }

    public static void main(String[] args) {
        try {
            MailService mailService = new MailService();
            List<String> to = new ArrayList<>();
            // Add your email you want to test
            // to.add("danglnh.ce190707@gmail.com");
            // to.add("danglnh07.work@gmail.com");
            // to.add("thuanpvhce181377@fpt.edu.vn");

            mailService.send(to, "Welcome to mail service", "welcome.html");
        } catch (Exception e) {
            System.out.println("Message: " + e.getMessage());
        }
    }
}
