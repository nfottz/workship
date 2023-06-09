package com.gdu.workship.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.workship.service.AttendanceService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/attendance")
@Controller
public class AttendanceController {

	private final AttendanceService attendanceService;
	
	@GetMapping("/attendList.do")
	public String attendanceMain(@RequestParam("memberNo") int memberNo, Model model) {
		attendanceService.getAttendancePage(memberNo, model);
		return "attendance/attendList";
	}
	
	@ResponseBody
	@GetMapping("/aStart.do")
	public Map<String, Object> aStart(@RequestParam("memberNo") int memberNo) {
		return attendanceService.aStart(memberNo);
	}
	
	@ResponseBody
	@GetMapping("/aEnd.do")
	public Map<String, Object> aEnd(@RequestParam("memberNo") int memberNo) {
		return attendanceService.aEnd(memberNo);
	}
	
	@ResponseBody
	@GetMapping(value="/search.do", produces="application/json")
	public Map<String, Object> search(HttpServletRequest request){
		return attendanceService.search(request);
	}
	
	@GetMapping("/attendManage.html")
	public String attendManage(Model model) {
		attendanceService.getAttendManagePage(model);
		return "attendance/attendManage";
	}
	
	@GetMapping("/attendManageList.do")
	public String attendManageSearch(HttpServletRequest request, Model model) {
		attendanceService.attendManageSearch(request, model);
		return "attendance/attendManage";
	}
	
	@ResponseBody
	@PostMapping("/modifyAttendance.do")
	public Map<String, Object> modifyAttendance(HttpServletRequest request) {
		return attendanceService.modifyAttendance(request);
	}
	
}
