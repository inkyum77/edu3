package com.ict.edu3.domain.members.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ict.edu3.domain.auth.vo.MembersVO;
import com.ict.edu3.domain.auth.vo.UserVO;

@Mapper
public interface MembersMapper {
  int joinMember(MembersVO mvo);
  int joinBusinessMember(MembersVO mvo);
  String getMemberIdxById(String m_id);
  String membersIdCheck(String m_id);
  MembersVO getMembersById(String m_id);
  MembersVO findUserByProvider(MembersVO mvo);
  int insertUser(MembersVO mvo);
  UserVO getUserById(String m_id);
  
}
