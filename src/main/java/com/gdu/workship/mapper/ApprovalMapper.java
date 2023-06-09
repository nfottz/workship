package com.gdu.workship.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.ApprovalDTO;
import com.gdu.workship.domain.ApprovalFileDTO;
import com.gdu.workship.domain.ApprovalLineDTO;
import com.gdu.workship.domain.DepartmentDTO;
import com.gdu.workship.domain.MemberDTO;

@Mapper
public interface ApprovalMapper {
  
  
  // 부서 가져오기
  public List<DepartmentDTO> selectDeptList();
  
  // 해당 부서에 대한 멤버 가져오기
  public List<MemberDTO> selectMemberList(int deptNo);
  
  // 멤버 가져오기
  public MemberDTO getMemberDTO(int memberNo);
  
  // 체크된 멤버 가져오기
  public List<MemberDTO> selectInsertCheckMemberList(Map<String, Object> map); 
  
  // 결재선 멤버 가져오기
  public List<MemberDTO> selectApprovalMmberList(Map<String, Object> map); 
  
  // 참조선 멤버 가져오기
  public List<MemberDTO> selectReferenceMmberList(Map<String, Object> map); 
  
  // 결재 상신 
  public int insertApproval(ApprovalDTO approvalDTO);
  
  // 결재 상신시 결재라인 
  public int insertApprovalLine(Map<String, Object> map);
  
  // 결재 상신시 참조라인
  public int insertReferenceLine(Map<String, Object> map);
  
  // 결재 상신시 첨부파일
  public int insertApprovalAttach(ApprovalFileDTO approvalFileDTO);
  
  // 결재시 결재문서 업데이트
  public int updateApprovalDoc(int approvalNo);
  
  // 최종 결재시 결재문서 업데이트
  public int completeApprovalDoc(int approvalNo);
  
  // 결재시 결재라인 업데이트
  public int updateApprovalMember(Map<String, Object> map);
  
  // 결재문서 회수
  public int updateRejectDoc(int approvalNo);
  
  // 반려시 결재라인 업데이트
  public int updateRejectMember(Map<String, Object> map);  
  
  // 결재문서 삭제
  public int deleteApproval(int approvalNo);
  
  // 결재문서 삭제시 결재라인 삭제
  public int deleteApprovalLine(int approvalNo);
  
  // 결재문서 삭제시 참조라인 삭제
  public int deleteReferenceLine(int approvalNo);
  
  // 결재문서 삭제시 첨부파일 삭제
  public int deleteApprovalFile(int approvalNo);
  
  // 결재 문서 회수하기
  public int updateRetrieveDoc(int approvalNo);
  
  // 참조멤버 카운드
  public int getReferenceCountByNo(int approvalNo);
  
  // 첨부파일 카운드
  public int getAttachCount(int approvalNo);
  
  
  // 결재문서 리스트 관련
  public int getApprovalCount(); 
  public int getApprovalCountByQuery(Map<String, Object> map);
  public int getApprovalCountApprovalStatus(Map<String, Object> mpa);
  public List<ApprovalDTO> getApprovalList(Map<String, Object> map);
  public int getApprovalSearchCount(Map<String, Object> map);
  public List<ApprovalDTO> getApprovalAutoComplete(Map<String, Object> map);
  
  // 참조문서 리스트 관련
  public int getReferenceCount(); 
  public int getReferenceCountByQuery(Map<String, Object> map);
  public List<ApprovalDTO> getReferenceList(Map<String, Object> map);
  public int getReferenceSearchCount(Map<String, Object> map);
  public List<ApprovalDTO> getReferenceAutoComplete(Map<String, Object> map);
  
  // 회수문서 리스트 관련
  public int getRetrieveCount(); 
  public int getRetrieveCountByQuery(Map<String, Object> map);
  public int getRetrieveCountApprovalStatus(Map<String, Object> mpa);
  public List<ApprovalDTO> getRetrieveList(Map<String, Object> map);
  public int getRetrieveSearchCount(Map<String, Object> map);
  public List<ApprovalDTO> getRetrieveAutoComplete(Map<String, Object> map);
  
  // 결재문서 상세보기
  public ApprovalDTO selectApprovalByNo(ApprovalDTO approvalDTO);
  
  // 결재문서에 대한 결재멤버 가져오기
  public List<ApprovalLineDTO> selectApprovalLineMembers(int approvalNo);
  
  // 결재번호에 대한 첨부파일 가져오기
  public List<ApprovalFileDTO> selectApprovalFilesByApprovalNo(int approvalNo);
  
  // 첨부파일번호에 대한 첨부파일 가져오기
  public ApprovalFileDTO selectApprovalFilesByAttachNo(int attachNo);  
  
}
