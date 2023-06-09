package com.gdu.workship.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.workship.domain.MemberDTO;
import com.gdu.workship.domain.VacationApprovalDTO;
import com.gdu.workship.domain.VacationDTO;

@Mapper
public interface VacationMapper {

	// 개인휴가조회페이지
	public MemberDTO getPersonalVacationInfo(int memberNo);
	public List<VacationDTO> getPersonalVacationList(int memberNo);
	
	// 휴가신청내역관리페이지
	public int getApprovalCount(Map<String, Object> map);
	public List<VacationApprovalDTO> approvalSearchList(Map<String, Object> map);
	public int updateApproval(int approvalNo);
	public VacationApprovalDTO selectApprovalByApprovalNo(int approvalNo);
	public int insertVacation(Map<String, Object> map);
	public int insertAttendance(Map<String, Object> map);
	public int getVacationDay(int approvalNo);
	public int updateDayoffCount(Map<String, Object> map);
	
	// 휴가내역관리페이지
	public int getVacationCount(Map<String, Object> map);
	public List<VacationDTO> vacationSearchList(Map<String, Object> map);
	public int modifyVacationApproval(Map<String, Object> map);
	public int modifyVacationDay(VacationDTO vacationDTO);
	public VacationDTO selectMemberNoVacationDayByVacationNo(int vacationNo);
	public int deleteVacationByVacationNo(int vacationNo);
	public VacationDTO getVacationInfoByVacationInfo(int vacationNo);
	
}
