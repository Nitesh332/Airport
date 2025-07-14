package com.example.demo.service;


import com.example.demo.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class EmailService implements EmailRepository {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendLoginNotificationEmail(String toEmail, String userName, String password) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("sawantnitesh102@gmail.com"); // The 'from' email address
            message.setTo(toEmail);
            message.setSubject("Successful Login Notification");
            message.setText(msg(password,userName));

            javaMailSender.send(message);
            System.out.println("Mail sent successfully to " + toEmail);

        } catch (Exception e) {
            System.err.println("Error while sending mail: " + e.getMessage());
            // You might want to log the exception more robustly
        }
    }

    public String msg(String password,String userName){
        String msg="<html>\n" +
                "\n" +
                "<body>\n" +
                "  <p>Hi <strong>[User's Name]</strong>,</p>\n" +
                "\n" +
                "  <p>We wanted to let you know that your account password was successfully changed.</p>\n" +
                "\n" +
                "  <p>This is your new password: <strong>[NewPassword]</strong><br>.</p>\n" +
                "\n" +
                "  <p>If you did <strong>not</strong> change your password, please reset it immediately using the link below and contact\n" +
                "    our support team:</p>\n" +
                "\n" +
                "  <p>\n" +
                "    <a href=\"[Reset Password Link]\" style=\"color: #2a7ae2;\">Reset Password</a>\n" +
                "  </p>\n" +
                "\n" +
                "  <hr>\n" +
                "  <p>For your security, we recommend always using a strong, unique password.</p>\n" +
                "\n" +
                "  <p>Thank you,<br>l̥\n" +
                "</body>\n" +
                "\n" +
                "</html>";
        msg=msg.replace("[Reset Password Link]","");
        msg=msg.replace("[NewPassword]",password);
        msg=msg.replace("[User's Name]",userName);
        return msg;
    }
}
