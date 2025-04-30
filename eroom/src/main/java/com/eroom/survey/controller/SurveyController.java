package com.eroom.survey.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eroom.employee.dto.StructureDto;
import com.eroom.employee.entity.Employee;
import com.eroom.employee.service.EmployeeService;
import com.eroom.security.EmployeeDetails;
import com.eroom.survey.dto.SurveyDto;
import com.eroom.survey.dto.SurveyItemDto;
import com.eroom.survey.dto.VoteRequest;
import com.eroom.survey.dto.VoteResultDto;
import com.eroom.survey.service.SurveyItemService;
import com.eroom.survey.service.SurveyService;
import com.eroom.survey.service.SurveyVoteService;
import com.eroom.survey.service.SurveyVoterService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/survey")
public class SurveyController {
	private final SurveyService surveyService;
	private final SurveyItemService surveyItemService;
	private final SurveyVoterService surveyVoterService;
	private final SurveyVoteService surveyVoteService;
	private final EmployeeService employeeService;

	@GetMapping("/list")
	public String surveyList(Model model) {

	    List<SurveyDto> surveyDtoList = surveyService.findAllSurveyWithVoterCount();
	    List<StructureDto> structureList = employeeService.findTeams();

	    model.addAttribute("surveyList", surveyDtoList);
	    model.addAttribute("structureList", structureList);

	    return "survey/list";
	}


	@GetMapping("/ongoing")
	public String surveyOngoing(Model model) {
		
	    List<SurveyDto> surveyDtoList = surveyService.findAllSurveyWithVoterCount();
	    List<StructureDto> structureList = employeeService.findTeams();

	    model.addAttribute("surveyList", surveyDtoList);
	    model.addAttribute("structureList", structureList);
		
		return "survey/ongoing";
	}

	@GetMapping("/closed")
	public String surveyClosed(Model model) {
		
	    List<SurveyDto> surveyDtoList = surveyService.findAllSurveyWithVoterCount();
	    List<StructureDto> structureList = employeeService.findTeams();

	    model.addAttribute("surveyList", surveyDtoList);
	    model.addAttribute("structureList", structureList);
		
		return "survey/closed";
	}

	@PostMapping("/create")
	public String createSurvey(SurveyDto surveyDto, SurveyItemDto surveyItemDto, @RequestParam("selectedTeamIds") List<String> selectedTeamIds) {
		// SurveyDto 값 확인
		System.out.println("제목: " + surveyDto.getSurveyTitle());
		System.out.println("복수 선택: " + surveyDto.getAllowMultiple());
		System.out.println("익명 투표: " + surveyDto.getAnonymousVote());
		System.out.println("마감일: " + surveyDto.getDeadline());

		// SurveyItemDto 값 확인
		System.out.println("항목 리스트:");
		for (String item : surveyItemDto.getItems()) {
			System.out.println("- " + item);
		}

		// 현재 사용자의 이름 가져오기
		EmployeeDetails userDetails = (EmployeeDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Employee employee = userDetails.getEmployee();
		if (employee == null) {
			return "/login";
		}
		String writer = employee.getEmployeeName();
		surveyDto.setWriter(writer);

		Long surveyNo = surveyService.saveSurvey(surveyDto, surveyItemDto);

		// 선택된 팀에 투표권한 부여
		for (String teamId : selectedTeamIds) {
		    List<Employee> members = employeeService.findEmployeesByTeamId(teamId);
		    for (Employee member : members) {
		        surveyVoterService.saveSurveyVoters(surveyNo, member.getEmployeeNo());
		    }
		}
		return "redirect:/survey/list";
	}

	@GetMapping("/detail")
	@ResponseBody
	public List<VoteResultDto> surveyDetail(@RequestParam("id") Long surveyNo) {
	    return surveyVoteService.findVoteResults(surveyNo);
	}

	@PostMapping("/vote")
	@ResponseBody
	public Map<String, Object> submitVote(@RequestBody VoteRequest voteRequest) {
	    Map<String, Object> response = new HashMap<>();

	    try {
	    	boolean success = surveyVoteService.saveVote(voteRequest);

	        if (!success) {
	            response.put("success", false);
	            response.put("message", "투표 권한이 없습니다.");
	        } else {
	            response.put("success", true);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.put("success", false);
	        response.put("message", "예상치 못한 오류가 발생했습니다.");
	    }

	    return response;
	}
	
	@GetMapping("/voter-count")
	@ResponseBody
	public Map<String, Integer> getVoterCount(@RequestParam("id") Long surveyId) {
		int count = surveyVoteService.getVoterCount(surveyId);

		Map<String, Integer> result = new HashMap<>();
		result.put("voterCount", count);
		return result;
	}
	
	@PostMapping("/delete")
	@ResponseBody
	public Map<String, Object> deleteSurvey(@RequestParam("id") Long surveyNo) {
	    Map<String, Object> response = new HashMap<>();

	    try {
	        int result = surveyService.deleteSurvey(surveyNo);
	        if (result > 0) {
	            response.put("success", true);
	            response.put("message", "삭제되었습니다.");
	        } else {
	            response.put("success", false);
	            response.put("message", "삭제 실패했습니다.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.put("success", false);
	        response.put("message", "오류가 발생했습니다.");
	    }
	    return response;
	}

}
