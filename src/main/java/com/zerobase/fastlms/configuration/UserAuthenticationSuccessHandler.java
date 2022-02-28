package com.zerobase.fastlms.configuration;

import com.zerobase.fastlms.member.entity.LoginHistory;
import com.zerobase.fastlms.member.repository.LoginHistoryRepository;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.util.RequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final LoginHistoryRepository loginHistoryRepository;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws  IOException {

        String userAgent = RequestUtils.getUserAgent(request);
        String clinetIp = RequestUtils.getClientIP(request);

        LoginHistory loginHistory = LoginHistory.builder()
                .userId(UUID.randomUUID().toString())
                .userEmail(authentication.getName())
                .loginDt(LocalDateTime.now())
                .userIp(clinetIp)
                .userAgent(userAgent)
                .build();

        loginHistoryRepository.save(loginHistory);

        Optional<Member> optionalMember = memberRepository.findByUserId(authentication.getName());

        if(optionalMember.isPresent()){
            Member member = optionalMember.get();

            member.setLastLoginDt(LocalDateTime.now());

            memberRepository.save(member);
        }
        response.sendRedirect("/");
    }
}
