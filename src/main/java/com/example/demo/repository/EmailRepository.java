package com.example.demo.repository;

public interface EmailRepository {
   public void  sendLoginNotificationEmail(String toEmail, String userName, String password);
}
