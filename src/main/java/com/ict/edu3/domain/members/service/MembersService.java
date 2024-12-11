package com.ict.edu3.domain.members.service;

import com.ict.edu3.domain.auth.vo.MembersVO;

public interface MembersService {
  public int joinMember(MembersVO mvo);
  public String membersIdCheck(String m_id);
  public MembersVO getMembersById(String m_id);
  public MembersVO findUserByProvider(MembersVO mvo);
  public int insertUser(MembersVO mvo);
}
