package com.ict.edu3.domain.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembersVO {
  private String m_idx, m_id, m_pw, m_name, m_age, m_reg, sns_id, phone, email, address, address_detail, zipcode, sns_email_naver, warn, sns_email_kakao, sns_email_google, sns_provider;
  private String business_number, business_name, business_ceo, started_date, contentId;
}
