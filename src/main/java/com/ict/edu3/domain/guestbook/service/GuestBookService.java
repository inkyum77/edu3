package com.ict.edu3.domain.guestbook.service;

import java.util.List;

import com.ict.edu3.domain.guestbook.vo.GuestBookVO;

public interface GuestBookService {
    List<GuestBookVO> getGuestBookList();
    GuestBookVO getGuestBookDetail(String gb_idx);
    int deleteGuestBookDetail(String gb_idx);
    int updateGuestBookDetail(GuestBookVO gvo);
    int writeGuestBook(GuestBookVO gvo);
}
