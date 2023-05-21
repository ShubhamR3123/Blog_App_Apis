package com.shubhamit.blog_app_api.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shubhamit.blog_app_api.helper.AppConstants;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userdetailsService;
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		//get Token
		String requestToken=request.getHeader("Autorization");
		//Bearer 223535555525sdgsd
		
		//System.out.println(requestToken);
		
		String username=null;
		String token =null;
		
		if(requestToken!=null && requestToken.startsWith("Bearer")) {
		token= requestToken.substring(7);
		
		try {
			 username = this.jwtTokenHelper.getUsernameFromToken(token);
			
		} catch (IllegalArgumentException e) {
			System.out.println(AppConstants.UNABLED);
		}catch (ExpiredJwtException ex) {
			System.out.println(AppConstants.IS_EXPIRED);
		}catch (MalformedJwtException e) {
			System.out.println(AppConstants.INVALID_JWT);
		}
	}else {
			System.out.println("Jwt token not starts with Bearer");
	}
	
	//once we get the Token ,now validate
	if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
		UserDetails userDetails = this.userdetailsService.loadUserByUsername(username);
		
		if(this.jwtTokenHelper.validateToken(token,userDetails)) {
	
		UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
				userDetails,null,userDetails.getAuthorities());
			
		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		
		} else {
			System.out.println(AppConstants.INVALID_JWT);
		}

	} else {
		System.out.println(AppConstants.IS_NULL);
	}
	filterChain.doFilter(request, response);
}
	}