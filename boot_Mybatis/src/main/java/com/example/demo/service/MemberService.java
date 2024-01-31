package com.example.demo.service;

import java.util.List;

import com.example.demo.security.MemberVO;

public interface MemberService {

	int insert(MemberVO mvo);

	List<MemberVO> getList(MemberVO mvo);

}
