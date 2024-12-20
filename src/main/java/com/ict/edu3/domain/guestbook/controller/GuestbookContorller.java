package com.ict.edu3.domain.guestbook.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ict.edu3.domain.auth.vo.DataVO;
import com.ict.edu3.domain.guestbook.service.GuestBookService;
import com.ict.edu3.domain.guestbook.vo.GuestBookVO;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@RestController
@RequestMapping("/api/guestbook")
public class GuestbookContorller {

  @Autowired
  private GuestBookService guestBookService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  //get은 request, post는 responsebody로 params 값을 받는다.
  // 리스트 보기
  @GetMapping("/list")
  public DataVO getGuestBookList() {
    DataVO dataVO = new DataVO();
    try {
      List<GuestBookVO> list = guestBookService.getGuestBookList();
      dataVO.setData(list);
      dataVO.setSuccess(true);
      dataVO.setMessage("게스트북 조회 성공");
    } catch (Exception e) {
      dataVO.setSuccess(false);
      dataVO.setMessage("게스트북 조회 실패");
    }

    return dataVO;
  }

  //상세 보기
  @GetMapping("/detail/{gb_idx}")
  public DataVO getGuestBookDetail(@PathVariable("gb_idx") String gb_idx) {
      DataVO dataVO = new DataVO();
      try {
          // log.info("gb_idx : " + gb_idx);
          GuestBookVO gvo = guestBookService.getGuestBookDetail(gb_idx);
          if (gvo == null) {
              dataVO.setSuccess(false);
              dataVO.setMessage("게스트북 상세보기 실패");
              return dataVO;
          }
          dataVO.setSuccess(true);
          dataVO.setMessage("게스트북 상세보기 성공");
          dataVO.setData(gvo);
      } catch (Exception e) {
          dataVO.setSuccess(false);
          dataVO.setMessage("게스트북 상세보기 실패");
      }
      return dataVO;
  }

  // 삭제하기
  @GetMapping("/delete/{gb_idx}")
  public DataVO deleteGuestBookDetail(@PathVariable("gb_idx") String gb_idx, Authentication authentication) {
    // security를 거치면 authentication이 채워짐.
    DataVO dataVO = new DataVO();
    try {
      // 로그인 여부 확인
      if(authentication == null){
        dataVO.setSuccess(false);
        dataVO.setMessage("로그인이 필요합니다.");
      }
      int result = guestBookService.deleteGuestBookDetail(gb_idx);

      if(result > 0){
        dataVO.setSuccess(true);
        dataVO.setMessage("게스트북 삭제 성공");
      } else {
        dataVO.setSuccess(false);
        dataVO.setMessage("게스트북 삭제 실패");
        
      }
    } catch (Exception e) {
      dataVO.setSuccess(false);
      dataVO.setMessage("게스트북 삭제 실패");
      
    }
    return dataVO;
  }

  // 업데이트
  @PutMapping("/update/{gb_idx}")
  public DataVO updateGuestBookDetail(@PathVariable("gb_idx") String gb_idx, @RequestBody GuestBookVO gvo, Authentication authentication) {
    // security를 거치면 authentication이 채워짐.
    DataVO dataVO = new DataVO();
    try {
      // 로그인 여부 확인
      if(authentication == null){
        dataVO.setSuccess(false);
        dataVO.setMessage("로그인이 필요합니다.");
      }

      int result = guestBookService.updateGuestBookDetail(gvo);
      
      log.info("gb_idx :" + gb_idx);
      log.info("gvo :" + gvo);


      if(result > 0){
        dataVO.setSuccess(true);
        dataVO.setMessage("게스트북 수정 성공");
      } else {
        dataVO.setSuccess(false);
        dataVO.setMessage("게스트북 수정 실패");
        
      }
    } catch (Exception e) {
      dataVO.setSuccess(false);
      dataVO.setMessage("게스트북 수정 오류");
      
    }
    return dataVO;
  }


  @PostMapping("/write")
  public DataVO updateGuestBookWrite(
    @ModelAttribute("data") GuestBookVO gvo,
    Authentication authentication) {

    DataVO dataVO = new DataVO();
    try {
      // 로그인 여부 확인
      if(authentication == null){
        dataVO.setSuccess(false);
        dataVO.setMessage("로그인이 필요합니다.");
        return dataVO;
      }
      // 로그인한 사람의 id 추출
      gvo.setGb_id(authentication.getName());
      gvo.setGb_pw(passwordEncoder.encode(gvo.getGb_pw()));

      MultipartFile file = gvo.getFile();
      if(file.isEmpty()){
        gvo.setGb_filename("");
      } else {
        UUID uuid = UUID.randomUUID();
        String f_name = uuid.toString() + "_" + file.getOriginalFilename();
        gvo.setGb_filename(f_name);
        
        // 프로젝트 내부의 resources/static/upload 경로
        // String path = new File("src/main/resources/static/upload").getAbsolutePath();
        // 실직적인 파일업로드

        String path = "D:\\upload";
        File uploadDir =  new File(path);

        if(!uploadDir.exists()){
          uploadDir.mkdirs();
        }

        file.transferTo(new File(path, f_name));
      }

      // 게스트북 쓰기
      int result = guestBookService.writeGuestBook(gvo);

      if(result == 0){
        dataVO.setSuccess(false);
        dataVO.setMessage("게스트북 작성 실패");
        return dataVO;
      }
      dataVO.setSuccess(true);
      dataVO.setMessage("게스트북 작성 성공");

    } catch (Exception e) {
      log.info("Exception : " + e);
      dataVO.setSuccess(false);
      dataVO.setMessage("게스트북 쓰기 오류 발생");
    }
    return dataVO;
  }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("filename") String filename) {
        log.info("sdfsdfsdfsdfsdfs");
        try {
            Path filePath = Paths.get("src/main/resources/static/upload").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new FileNotFoundException("File not found: " + filename);
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}