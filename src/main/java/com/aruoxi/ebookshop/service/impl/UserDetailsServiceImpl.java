package com.aruoxi.ebookshop.service.impl;

import com.aruoxi.ebookshop.common.RegexUtil;
import com.aruoxi.ebookshop.domain.User;
import com.aruoxi.ebookshop.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Resource
	UserRepository userRepository;

	@Resource
	RegexUtil regexUtil;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = regexUtil.isEmail(username) ?
				userRepository.findByEmail(username)
						.orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + username)) :
				userRepository.findByUsername(username)
						.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}

}
