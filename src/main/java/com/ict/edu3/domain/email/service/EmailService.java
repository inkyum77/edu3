package com.ict.edu3.domain.email.service;

import com.ict.edu3.domain.email.vo.VerificationCode;

public interface EmailService {
  public boolean sendVerificationEmail(String toEmail);
  public boolean verifyCode(String email, String inputCode);
  public VerificationCode getVerificationCode(String email);
}
