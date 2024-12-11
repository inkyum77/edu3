package com.ict.edu3.domain.guestbook.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestBookVO {
	private String gb_idx, gb_writer, gb_subject, gb_pw, gb_content, gb_email, gb_date, gb_filename, gb_id;
	private MultipartFile file;	
}
