package com.teaming.TeamingServer.Service;

import com.teaming.TeamingServer.Domain.Dto.CheckCurrentPasswordRequestDto;
import com.teaming.TeamingServer.Domain.Dto.MemberChangePasswordRequestDto;
import com.teaming.TeamingServer.Domain.Dto.MemberChangeProfileImageRequestDto;
import com.teaming.TeamingServer.Domain.Dto.MemberNicknameChangeRequestDto;
import org.springframework.http.ResponseEntity;

public interface MemberService {
    ResponseEntity changePassword(Long memberId, MemberChangePasswordRequestDto memberChangePasswordRequestDto); // PasswordChange Request 매개변수로 들어갈 예정

    ResponseEntity checkCurrentPassword(Long memberId, CheckCurrentPasswordRequestDto checkCurrentPasswordRequestDto); // 비밀번호 변경을 위한 현재 비밀번호 체크 : CheckCurrentPasswordRequest 가 매개변수로 들어갈 예정

    ResponseEntity MemberMyPage(Long memberId); // 액세스 토큰을 확인한 후, 사용자 정보 페이지 반환 : MemberMyPageRequest 가 매개변수로 들어갈 예정

    ResponseEntity changeNickName(Long memberId, MemberNicknameChangeRequestDto memberNicknameChangeRequestDto); // 닉네임 바꾸기

    ResponseEntity changeProfileImage(Long memberId, MemberChangeProfileImageRequestDto memberChangeProfileImageRequestDto);

    ResponseEntity mainPage(Long memberId); // main 페이지 Response 를 따로 만들어서 매개변수로 넣을 예정 : 최근 프로젝트, 진행 중인 프로젝트, 포트폴리오

}
