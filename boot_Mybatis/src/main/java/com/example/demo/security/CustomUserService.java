package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.repository.MemberMapper;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class CustomUserService implements UserDetailsService {

	@Autowired
	private MemberMapper memberMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		MemberVO mvo = memberMapper.selectEmail(username);
		mvo.setAuthList(memberMapper.selectAuths(username));
		log.info("mvo>>>>>>>>{}",mvo);
		return new AuthMember(mvo);
	}

}
