package com.gdu.workship.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO {
	private int attendanceNo;
	private int memberNo;
	private Date date;
	private Date astarttime;
	private Date aendtime;
	private Date worktime;
	private String attendance;
}