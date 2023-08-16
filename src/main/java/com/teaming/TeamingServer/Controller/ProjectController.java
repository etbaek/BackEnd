package com.teaming.TeamingServer.Controller;


import com.teaming.TeamingServer.Domain.Dto.*;
import com.teaming.TeamingServer.Domain.entity.Project;
import com.teaming.TeamingServer.Exception.BaseException;
import com.teaming.TeamingServer.Service.*;
import com.teaming.TeamingServer.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.teaming.TeamingServer.Service.FileService;
import com.teaming.TeamingServer.common.BaseErrorResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final ScheduleService scheduleService;
    private final ProjectService projectService;
    private final FileService fileService;

    // 스케줄 추가
    @PostMapping("/{memberId}/{projectId}/schedule")
    public ResponseEntity<BaseResponse> makeSchedule(
            @RequestBody ScheduleEnrollRequestDto scheduleEnrollRequestDto,
            @PathVariable("memberId") Long memberId,
            @PathVariable("projectId") Long projectId) {
        try {
            scheduleService.generateSchedule(memberId, projectId, scheduleEnrollRequestDto);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "일정이 추가되었습니다.:)", null));
        } catch (BaseException e) {
            BaseErrorResponse errorResponse = new BaseErrorResponse(e.getCode(), e.getMessage());

            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseResponse<>(e.getCode(), e.getMessage(), null));
        }
    }

    // 프로젝트의 스케줄 확인
    @GetMapping("/{memberId}/{projectId}/schedule")
    public ResponseEntity<BaseResponse<List<ScheduleResponseDto>>> searchSchedules(
            @PathVariable("memberId") Long memberId, @PathVariable("projectId") Long projectId) {
        try {
            List<ScheduleResponseDto> list = projectService.searchSchedule(memberId, projectId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "프로젝트의 스케줄", list));
        } catch (BaseException e) {
            BaseErrorResponse errorResponse = new BaseErrorResponse(e.getCode(), e.getMessage());

            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseResponse<>(e.getCode(), e.getMessage(), null));
        }
    }


    // 프로젝트의 스케줄 삭제
    @DeleteMapping("/{memberId}/{projectId}/{scheduleId}")
    public ResponseEntity<BaseResponse> deleteSchedule(@PathVariable("memberId") Long memberId,
                                                       @PathVariable("projectId") Long projectId,
                                                       @PathVariable("scheduleId") Long scheduleId) {
        try {
            scheduleService.deleteSchedule(memberId, projectId, scheduleId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse(HttpStatus.OK.value(), "스케줄 삭제 성공", null));
        } catch (BaseException e) {
            BaseErrorResponse errorResponse = new BaseErrorResponse(e.getCode(), e.getMessage());

            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseResponse<>(e.getCode(), e.getMessage(), null));
        }
    }


    // 파일 업로드
    @PostMapping("/{memberId}/{projectId}/files-upload")
    public ResponseEntity<BaseResponse<FileUploadResponseDto>> uploadFile(@PathVariable Long projectId,
                                                   @PathVariable Long memberId,
                                                   @RequestPart MultipartFile file) {
        try {


           FileUploadResponseDto fileUploadResponseDto =  fileService.generateFile(projectId, memberId, file, false);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "파일을 업로드하였습니다", fileUploadResponseDto));
        } catch (BaseException e) {
            BaseErrorResponse errorResponse = new BaseErrorResponse(e.getCode(), e.getMessage());

            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseResponse<>(e.getCode(), e.getMessage(), null));
        }
    }


    // 프로젝트의 각 스케줄 확인
    @GetMapping("/{memberId}/{projectId}/{scheduleId}")
    public ResponseEntity<BaseResponse<List<ScheduleConfirmDto>>> readSchedule(
            @PathVariable("memberId") Long memberId, @PathVariable("projectId") Long projectId,
            @PathVariable("scheduleId") Long scheduleId) {
        try {
            List<ScheduleConfirmDto> list = projectService.readSchedule(memberId, projectId, scheduleId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "프로젝트의 스케줄", list));
        } catch (BaseException e) {
            BaseErrorResponse errorResponse = new BaseErrorResponse(e.getCode(), e.getMessage());

            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseResponse<>(e.getCode(), e.getMessage(), null));
        }
    }

    // 파일 삭제
    @DeleteMapping("/{memberId}/{projectId}/files/{fileId}")
    public ResponseEntity<BaseResponse> deleteFile(@PathVariable Long projectId, @PathVariable Long memberId, @PathVariable Long fileId) {

        try {
            fileService.deleteFile(projectId, memberId, fileId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "파일을 삭제하였습니다", null));
        } catch (BaseException e) {
            BaseErrorResponse errorResponse = new BaseErrorResponse(e.getCode(), e.getMessage());

            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseResponse<>(e.getCode(), e.getMessage(), null));
        }
    }


    // 최종 파일 업로드
    @PostMapping("/{memberId}/{projectId}/final-file")
    public ResponseEntity<BaseResponse<FileUploadResponseDto>> uploadFinalFile(@PathVariable Long projectId,
                                                        @PathVariable Long memberId,
                                                        @RequestPart MultipartFile file) {
        try {
           FileUploadResponseDto fileUploadResponseDto =  fileService.generateFile(projectId, memberId, file, true);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "최종 파일을 업로드하였습니다", fileUploadResponseDto));
        } catch (BaseException e) {
            BaseErrorResponse errorResponse = new BaseErrorResponse(e.getCode(), e.getMessage());

            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseResponse<>(e.getCode(), e.getMessage(), null));
        }
    }


    // 프로젝트 파일들 조회

    @GetMapping("/{memberId}/{projectId}/files")
    public ResponseEntity<BaseResponse<List<FileListResponseDto>>> searchFiles(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId) {
        try {
            List<FileListResponseDto> fileInfoList = fileService.searchFile(memberId, projectId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "프로젝트 파일들을 불러왔습니다", fileInfoList));
        } catch (BaseException e) {
            BaseErrorResponse errorResponse = new BaseErrorResponse(e.getCode(), e.getMessage());

            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseResponse<>(e.getCode(), e.getMessage(), null));
        }
    }

    // 프로젝트 최종 파일들 조회
    @GetMapping("/{memberId}/{projectId}/final-files")
    public ResponseEntity<BaseResponse<List<FileListResponseDto>>> searchFinalFiles(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId) {

        try {
            List<FileListResponseDto> finalInfoList = fileService.searchFinalFile(memberId, projectId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "프로젝트 최종 파일들을 불러왔습니다", finalInfoList));

        } catch (BaseException e) {
            BaseErrorResponse errorResponse = new BaseErrorResponse(e.getCode(), e.getMessage());

            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseResponse<>(e.getCode(), e.getMessage(), null));
        }
    }

    // 멤버 초대하기
    @PostMapping("/{memberId}/{projectId}/invitations")
    public ResponseEntity inviteMember(@RequestBody ProjectInviteRequestDto projectInviteRequestDto
            , @PathVariable("projectId") Long projectId) {
        return projectService.inviteMember(projectInviteRequestDto, projectId);
    }

    // 프로젝트 상태 바꾸기
    @PatchMapping("/{memberId}/{projectId}/status")
    public ResponseEntity projectChangeStatus(@RequestBody ProjectStatusRequestDto projectStatusRequestDto
            , @PathVariable("projectId") Long projectId) {
        return projectService.projectChangeStatus(projectStatusRequestDto, projectId);
    }

    // 하나의 파일 정보 조회
    @GetMapping("/{memberId}/{projectId}/files/{fileId}")
    public ResponseEntity<BaseResponse<SingleFileResponseDto>> searchOneFile(@PathVariable("memberId") Long memberId, @PathVariable("projectId") Long projectId, @PathVariable("fileId") Long fileId) {

        try {
            SingleFileResponseDto information = fileService.searchOneFile(memberId, projectId, fileId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "파일 상세 정보를 불러왔습니다", information));
        } catch (BaseException e) {
            BaseErrorResponse errorResponse = new BaseErrorResponse(e.getCode(), e.getMessage());

            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseResponse<>(e.getCode(), e.getMessage(), null));
        }

    }

    //프로젝트 생성
    @PostMapping("/{memberId}/create")
    public ResponseEntity<BaseResponse<ProjectCreateResponseDto>> createProject(@PathVariable("memberId") Long memberId, @RequestBody ProjectCreateRequestDto projectCreateRequestDto) {
        try {

            ProjectCreateResponseDto projectCreateResponseDto = projectService.createProject(memberId,projectCreateRequestDto);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "프로젝트를 생성하였습니다", projectCreateResponseDto));
        }   catch (BaseException e) {
            BaseErrorResponse errorResponse = new BaseErrorResponse(e.getCode(), e.getMessage());

            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseResponse<>(e.getCode(), e.getMessage(), null));
        }
    }

    // 프로젝트 조회
    @GetMapping("/{memberId}/{projectId}")
    public ResponseEntity<BaseResponse<ProjectResponseDto>> getProject(@PathVariable("memberId") Long memberId, @PathVariable("projectId") Long projectId) {
        try {
            ProjectResponseDto projectDetail = projectService.getProject(memberId,projectId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "프로젝트 정보를 불러왔습니다", projectDetail));
        } catch (BaseException e) {
            BaseErrorResponse errorResponse = new BaseErrorResponse(e.getCode(), e.getMessage());
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseResponse<>(e.getCode(), e.getMessage(), null));
        }
    }
}

