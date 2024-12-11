package com.ict.edu3.domain.members.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.edu3.domain.auth.vo.MembersVO;
import com.ict.edu3.domain.members.mapper.MembersMapper;

@Service
public class MembersServiceImpl implements MembersService{
  @Autowired
  private MembersMapper membersMapper;

  @Override
  public int joinMember(MembersVO mvo) {
    return membersMapper.joinMember(mvo);
  }

  @Override
  public String membersIdCheck(String m_id) {
    return membersMapper.membersIdCheck(m_id);
  }

  @Override
  public MembersVO getMembersById(String m_id) {
    return membersMapper.getMembersById(m_id);
  }

  @Override
  public MembersVO findUserByProvider(MembersVO mvo) {
    return membersMapper.findUserByProvider(mvo);
  }

  @Override
  public int insertUser(MembersVO mvo) {
    return membersMapper.insertUser(mvo);
  }
}
