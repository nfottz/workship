package com.gdu.workship.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface NoticeBoardService {
  public void loadNoticeBoardList(HttpServletRequest request, Model model);
  public Map<String, Object> loadNoticeBoardList2(HttpServletRequest request);
  
  public int increaseHit(int noticeNo);
  public void getNoticeByNo(int noticeNo, Model model);
  public ResponseEntity<Resource> download(int noticeFileNo, String userAgent);
  public ResponseEntity<Resource> downloadAll(int noticeNo);
  public int addNotice(MultipartHttpServletRequest request);
  public int removeNotice(int noticeNo);
  public int modifyNotice(MultipartHttpServletRequest multipartRequest);
  public int removeNoticeFile(int noticeFileNo);
}
