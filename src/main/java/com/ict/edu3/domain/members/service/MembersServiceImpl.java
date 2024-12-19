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

    // 일반회원가입
    int result = membersMapper.joinMember(mvo);
    
    // mvo에 사업자번호가 없다면 바로 반환
    if(mvo.getBusiness_number().isEmpty() || mvo.getBusiness_number().equals("")){
      return result;
    }
    
    // 아니면 사업자 테이블에 사업자 가입
    String m_idx = membersMapper.getMemberIdxById(mvo.getM_id());
    mvo.setM_idx(m_idx);
    return membersMapper.joinBusinessMember(mvo);
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

  @Override
  public String getMemberIdxById(String m_id) {
    return membersMapper.getMemberIdxById(m_id);
  }
}
