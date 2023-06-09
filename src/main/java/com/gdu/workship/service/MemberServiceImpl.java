package com.gdu.workship.service;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.workship.domain.DepartmentDTO;
import com.gdu.workship.domain.JobDTO;
import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.domain.RetiredMemberDTO;
import com.gdu.workship.mapper.MemberMapper;
import com.gdu.workship.util.MyFileUtil;
import com.gdu.workship.util.PageUtil2;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

  private final MemberMapper memberMapper;
  private final PageUtil2 pageUtil;
  private final MyFileUtil myFileUtil;
  
  @Override
  public void loadMemberList(HttpServletRequest request, Model model) {

    // 파라미터 page가 전달되지 않는 경우 page=1로 처리한다.
    Optional<String> opt1 = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt1.orElse("1"));
    
    // 전체 레코드 개수를 구한다.
    int totalRecord = memberMapper.getMemberCount();
    
    int recordPerPage = 10;

    // 파라미터 column이 전달되지 않는 경우 column=MEMBER_NO로 처리한다.
    Optional<String> opt2 = Optional.ofNullable(request.getParameter("column"));
    String column = opt2.orElse("A.MEMBER_NO");
    
    // 파라미터 query가 전달되지 않는 경우 query=""로 처리한다. (query가 없으면 null값, 처음 화면이동 요청..)
    Optional<String> opt3 = Optional.ofNullable(request.getParameter("query"));
    String query = opt3.orElse("");
    
    
    // DB로 보낼 Map 만들기
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("column", column);
    map.put("query", query);
    // PageUtil(Pagination에 필요한 모든 정보) 계산하기
    map.put("recordPerPage", recordPerPage);    // end 대신 recordPerPage를 전달한다.
    pageUtil.setPageUtil(page, (column.isEmpty() && query.isEmpty()) ? totalRecord : memberMapper.getMemberSearchCount(map), recordPerPage);
    map.put("begin", pageUtil.getBegin());      // begin은 0부터 시작한다. (PageUtil.java 참고)
    
    // begin ~ end 사이의 목록 가져오기
    List<MemberDTO> memberList = memberMapper.getMemberList(map);
    List<DepartmentDTO> deptList = memberMapper.getDeptList();
    model.addAttribute("memberList", memberList);
    model.addAttribute("deptList", deptList);
    System.out.println(map);
    System.out.println(memberList);
    // pagination.jsp로 전달할(forward)할 정보 저장하기
    if(column.isEmpty() || query.isEmpty()) {
      model.addAttribute("pagination", pageUtil.getPagination(request.getContextPath() + "/member/member.html"));
    } else {
      model.addAttribute("pagination", pageUtil.getPagination(request.getContextPath() + "/member/member.html?column=" + column + "&query=" + query));
    }
  }
  
  @Override
  public Map<String, Object> loadMemberList2(HttpServletRequest request) {

    // Optional<String> opt1 = Optional.ofNullable(request.getParameter("page"));
    // int page = Integer.parseInt(opt1.orElse("1"));
    
    String strPage = request.getParameter("page");
    int page = 1;
    if(strPage != null && strPage.isEmpty() == false) page = Integer.parseInt(strPage);
    if(page < 1) page = 1;
    int totalRecord = memberMapper.getMemberCount();
    
    int recordPerPage = 10;
    
    Optional<String> opt2 = Optional.ofNullable(request.getParameter("column"));
    String column = opt2.orElse("A.MEMBER_NO");
    
    Optional<String> opt3 = Optional.ofNullable(request.getParameter("query"));
    String query = opt3.orElse("");
    
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("column", column);
    map.put("query", query);
    // PageUtil(Pagination에 필요한 모든 정보) 계산하기
    map.put("recordPerPage", recordPerPage);    // end 대신 recordPerPage를 전달한다.
    pageUtil.setPageUtil(page, (column.isEmpty() && query.isEmpty()) ? totalRecord : memberMapper.getMemberSearchCount(map), recordPerPage);
    map.put("begin", pageUtil.getBegin());      // begin은 0부터 시작한다. (PageUtil.java 참고)

    List<MemberDTO> memberList = memberMapper.getMemberList(map);
    List<DepartmentDTO> deptList = memberMapper.getDeptList();
    
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("query", query);
    result.put("memberList", memberList);
    result.put("deptList", deptList);
    
    result.put("pageUtil", pageUtil);
    result.put("pagination", pageUtil.getPagination("/member/memberList2.do?column=" + column + "&query=" + query));
    System.out.println(result.get("pageUtil"));
    System.out.println(result);
    System.out.println(pageUtil.getBeginPage());
    System.out.println(pageUtil.getEndPage());
    System.out.println(pageUtil.getTotalPage());
    return result;
  }
  
  @Override
  public Map<String, Object> loadRetiredMemberList(HttpServletRequest request) {
    
    // Optional<String> opt1 = Optional.ofNullable(request.getParameter("page"));
    // int page = Integer.parseInt(opt1.orElse("1"));
    
    String strPage = request.getParameter("page");
    int page = 1;
    if(strPage != null && strPage.isEmpty() == false) page = Integer.parseInt(strPage);
    if(page < 1) page = 1;
    int totalRecord = memberMapper.getRetiredMemberCount();
    
    int recordPerPage = 10;
    
    Map<String, Object> map = new HashMap<String, Object>();
    // PageUtil(Pagination에 필요한 모든 정보) 계산하기
    map.put("recordPerPage", recordPerPage);    // end 대신 recordPerPage를 전달한다.
    pageUtil.setPageUtil(page, totalRecord, recordPerPage);
    map.put("begin", pageUtil.getBegin());      // begin은 0부터 시작한다. (PageUtil.java 참고)

    List<RetiredMemberDTO> retiredMemberList = memberMapper.getRetiredMemberList(map);
    
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("memberList", retiredMemberList);
    
    result.put("pageUtil", pageUtil);
    result.put("pagination", pageUtil.getPagination("/member/retiredMemberList.do"));
    System.out.println(result.get("pageUtil"));
    System.out.println(result);
    System.out.println(pageUtil.getBeginPage());
    System.out.println(pageUtil.getEndPage());
    System.out.println(pageUtil.getTotalPage());
    return result;
  }
  
  @Override
  public Map<String, Object> verifyEmail(String emailId) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("enableEmail", memberMapper.selectMemberByEmailId(emailId) == null);
    return map;
  }
  
  @Override
  public void loadDeptList(Model model) {
    model.addAttribute("deptList", memberMapper.getDeptList());
  }
  
  @Override
  public int addMember(MultipartHttpServletRequest request) {
    
    String emailId = request.getParameter("emailId");
    String memberName = request.getParameter("memberName");
    String tel = request.getParameter("tel");
    String birthday = request.getParameter("birthday");
    int deptNo = request.getParameter("deptNo").isEmpty() ? 1 : Integer.parseInt(request.getParameter("deptNo"));
    int jobNo = request.getParameter("jobNo").isEmpty() ? 6 : Integer.parseInt(request.getParameter("jobNo"));
    String pw = request.getParameter("pw");
    
    String deptName = memberMapper.selectDept(deptNo).getDeptName();
    String jobName = memberMapper.selectJob(jobNo).getJobName();
    
    DepartmentDTO dept = new DepartmentDTO();
    dept.setDeptNo(deptNo);
    dept.setDeptName(deptName);
    JobDTO job = new JobDTO();
    job.setJobNo(jobNo);
    job.setJobName(jobName);
    
    MemberDTO member = new MemberDTO();
    member.setEmailId(emailId);
    member.setMemberName(memberName);
    member.setTel(tel);
    member.setBirthday(birthday);
    member.setPw(pw);
    member.setDepartmentDTO(dept);
    member.setJobDTO(job);
    
    MultipartFile file = request.getFile("attachImage");
    if(file != null && file.isEmpty() == false) {
      
      try {
        String path = myFileUtil.getPath();
        
        File dir = new File(path);
        if(dir.exists() == false) {
          dir.mkdirs();
        }
        
        String extName = null;
        
        String originName = file.getOriginalFilename();
        
        if(originName.endsWith("tar.gz")) {
          extName = "tar.gz";
        } else {
          String[] array = originName.split("\\.");
          extName = array[array.length - 1];  // 배열의 마지막 인덱스
        }
        System.out.println(path);
        System.out.println(originName);
        String fileName = UUID.randomUUID().toString().replace("-", "") + "." + extName;
        System.out.println(fileName);

        File savefile = new File(dir, fileName);
        
        file.transferTo(savefile); 
        
        /* 썸네일(첨부 파일이 이미지인 경우에만 썸네일이 가능) */
        
        String contentType = Files.probeContentType(savefile.toPath());  // 이미지 파일의 Content-Type : image/jpeg, image/png, image/gif, ...
        
        boolean hasThumbnail = contentType != null && contentType.startsWith("image");
        
        if(hasThumbnail) {
          File thumbnail = new File(dir, "s_" + fileName);
          Thumbnails.of(savefile)
            .size(100, 100)
            .toFile(thumbnail);

          member.setProfileFilePath(path);
          member.setProfileFileName(fileName);
          savefile.delete();
        }
        
        
      } catch(Exception e) {
        e.printStackTrace();
        
        return 0;
      }
      
    }
    
    int addResult = memberMapper.createMember(member);
    
    return addResult;
    
  }
  
  @Override
  public void memberDetail(int memberNo, Model model) {
    MemberDTO member = new MemberDTO();
    member = memberMapper.getMemberByNo(memberNo);
    String sep = Matcher.quoteReplacement(File.separator);
    String profileImage = member.getProfileFilePath() + sep + member.getProfileFileName();
    model.addAttribute("m", member);
    model.addAttribute("profileImage", profileImage);
    model.addAttribute("deptList", memberMapper.getDeptList());
  }
  
  @Override
  public int modifyMember(MultipartHttpServletRequest request) {
    int memberNo = Integer.parseInt(request.getParameter("memberNo")); 
    String memberName = request.getParameter("memberName");
    String tel = request.getParameter("tel");
    String birthday = request.getParameter("birthday");
    int status = request.getParameter("status").isEmpty() ? 0 : Integer.parseInt(request.getParameter("status"));
    int deptNo = request.getParameter("deptNo").isEmpty() ? 1 : Integer.parseInt(request.getParameter("deptNo"));
    int jobNo = request.getParameter("jobNo").isEmpty() ? 6 : Integer.parseInt(request.getParameter("jobNo"));
    String pw = request.getParameter("pw");
    
    String deptName = memberMapper.selectDept(deptNo).getDeptName();
    String jobName = memberMapper.selectJob(jobNo).getJobName();
    
    DepartmentDTO dept = new DepartmentDTO();
    dept.setDeptNo(deptNo);
    dept.setDeptName(deptName);
    JobDTO job = new JobDTO();
    job.setJobNo(jobNo);
    job.setJobName(jobName);
    
    MemberDTO member = new MemberDTO();
    member.setMemberNo(memberNo);
    member.setMemberName(memberName);
    member.setTel(tel);
    member.setBirthday(birthday);
    member.setStatus(status);
    member.setPw(pw);
    member.setDepartmentDTO(dept);
    member.setJobDTO(job);
    
    MultipartFile file = request.getFile("attachImage");
    if(file != null && !file.isEmpty()) {
      
      try {
        String path = myFileUtil.getPath();
        
        File dir = new File(path);
        if(dir.exists() == false) {
          dir.mkdirs();
        }
        
        String extName = null;
        
        String originName = file.getOriginalFilename();
        
        if(originName.endsWith("tar.gz")) {
          extName = "tar.gz";
        } else {
          String[] array = originName.split("\\.");
          extName = array[array.length - 1];  // 배열의 마지막 인덱스
        }
        System.out.println(path);
        System.out.println(originName);
        String fileName = UUID.randomUUID().toString().replace("-", "") + "." + extName;
        System.out.println(fileName);

        File savefile = new File(dir, fileName);
        
        file.transferTo(savefile);
        
        /* 썸네일(첨부 파일이 이미지인 경우에만 썸네일이 가능) */
        
        String contentType = Files.probeContentType(savefile.toPath());  // 이미지 파일의 Content-Type : image/jpeg, image/png, image/gif, ...
        
        boolean hasThumbnail = contentType != null && contentType.startsWith("image");
        
        if(hasThumbnail) {
          File thumbnail = new File(dir, "s_" + fileName);
          Thumbnails.of(savefile)
            .size(100, 100)
            .toFile(thumbnail);

          member.setProfileFilePath(path);
          member.setProfileFileName(fileName);
          savefile.delete();
        }
        
        
      } catch(Exception e) {
        e.printStackTrace();
        
        return 0;
      }
      
    }
    
    int modifyResult = memberMapper.modifyMember(member);
    
    return modifyResult;
  }
  
  @Override
  public ResponseEntity<byte[]> display(int memberNo) {
    System.out.println("display 서비스");
    MemberDTO profileDTO = memberMapper.getMemberByNo(memberNo);
    System.out.println(profileDTO);
    ResponseEntity<byte[]> profileImage = null;
    
    try {
      File thumbnail = new File(profileDTO.getProfileFilePath(), "s_" + profileDTO.getProfileFileName());
      System.out.println(thumbnail);
      profileImage = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(thumbnail), HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return profileImage;
    
  }
  
  @Override
  public int removeMember(int memberNo) {

    int removeResult = memberMapper.removeMember(memberNo);

    return removeResult;
  }

}
