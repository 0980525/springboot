package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.security.MemberVO;
import com.example.demo.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RequestMapping("/member/*")
@RequiredArgsConstructor
@Controller
public class MemberController {

	private final MemberService msv;
	private final PasswordEncoder passwordEncoder;
	@GetMapping("/register")
	public String join() {
		return "/member/register";
	}
	@PostMapping("/register")
	public String register(MemberVO mvo) {
		log.info(">>>mvo>>>{}",mvo);
		mvo.setPwd(passwordEncoder.encode(mvo.getPwd()));
		int isOk = msv.insert(mvo);
		return isOk > 0? "/index":"/member/register";
		
	}
	
	
	@GetMapping("/login")
	public String login() {
		return "/member/login";
	}
	
	private void logout(HttpServletRequest req, HttpServletResponse res) {
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
			new SecurityContextLogoutHandler().logout(req, res, authentication);
		
	}
	@PostMapping("/logout")
	public void logout() {
		
	}
	@GetMapping("/list")
	public void list(Model m, MemberVO mvo) {
		List<MemberVO> list = msv.getList(mvo);
		log.info("list >>>>>>{}",list);
		m.addAttribute("list",list);
	}
}
