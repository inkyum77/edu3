package com.ict.edu3.domain.guestbook.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.edu3.domain.guestbook.mapper.GuestBookMapper;
import com.ict.edu3.domain.guestbook.vo.GuestBookVO;

@Service
public class GuestBookServiceImpl implements GuestBookService{
  @Autowired
  private GuestBookMapper guestBookMapper;

  @Override
  public List<GuestBookVO> getGuestBookList() {
    return guestBookMapper.getGuestBookList();
  }

  @Override
  public GuestBookVO getGuestBookDetail(String gb_idx) {
    
    return guestBookMapper.getGuestBookDetail(gb_idx);
  }

  @Override
  public int deleteGuestBookDetail(String gb_idx) {
    return guestBookMapper.deleteGuestBookDetail(gb_idx);
  }

  @Override
  public int updateGuestBookDetail(GuestBookVO gvo) {
    return guestBookMapper.updateGuestBookDetail(gvo);
  }

  //작성하기
  @Override
  public int writeGuestBook(GuestBookVO gvo) {
    return guestBookMapper.writeGuestBook(gvo);
  }
}

