package com.eroom.attendance.dto;

import java.time.LocalDateTime;

import com.eroom.attendance.entity.Attendance;
import com.eroom.employee.entity.Employee;
import com.eroom.employee.entity.Structure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AttendanceDto {
	
	private Long attendance_no;
	private Long employee_no;
	private LocalDateTime attendance_check_in_time;
	private LocalDateTime attendance_check_out_time;
	private String attendance_late_yn;
	private String attendance_early_leave_yn;
	
	// 출퇴근 구분용
	private String attendanceType;
	
	
	public Attendance toEntity() {
		return Attendance.builder()
						.attendanceNo(attendance_no)
						.employee(Employee.builder().employeeNo(employee_no).build())
						.attendanceCheckInTime(attendance_check_in_time)
						.attendanceCheckOutTime(attendance_check_out_time)
						.attendanceLateYn(attendance_late_yn)
						.attendanceEarlyLeaveYn(attendance_early_leave_yn)
						.build();
		
	}
	
	public AttendanceDto toDto(Attendance attendance) {
		return AttendanceDto.builder()
							.attendance_no(attendance.getAttendanceNo())
							.employee_no(attendance.getEmployee().getEmployeeNo())
							.attendance_check_in_time(attendance.getAttendanceCheckInTime())
							.attendance_check_out_time(attendance.getAttendanceCheckOutTime())
							.attendance_late_yn(attendance.getAttendanceLateYn())
							.attendance_early_leave_yn(attendance.getAttendanceEarlyLeaveYn())
							.build();
		
	}

}
