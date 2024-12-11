package com.ict.edu3.domain.members.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ict.edu3.domain.auth.vo.MembersVO;

@Mapper
public interface MembersMapper {
  int joinMember (MembersVO mvo);
  String membersIdCheck(String m_id);
  MembersVO getMembersById(String m_id);
  MembersVO findUserByProvider(MembersVO mvo);
  int insertUser(MembersVO mvo);
  
}
