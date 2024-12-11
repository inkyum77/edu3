package com.ict.edu3.domain.guestbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ict.edu3.domain.guestbook.vo.GuestBookVO;

@Mapper
public interface GuestBookMapper {
  int GuestBookUpdate(GuestBookVO gvo); 
  List<GuestBookVO> getGuestBookList();
  GuestBookVO getGuestBookDetail(String gb_idx);
  int deleteGuestBookDetail(String gb_idx);
  int updateGuestBookDetail(GuestBookVO gvo);
  int writeGuestBook(GuestBookVO gvo);
}
