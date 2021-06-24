package com.codetogether.openstudio.controller.apis.v1;

import com.codetogether.openstudio.dto.subject.SubjectSaveRequestDto;
import com.codetogether.openstudio.dto.subject.SubjectUpdateRequestDto;
import com.codetogether.openstudio.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Subject API"})
@RequiredArgsConstructor
@RequestMapping("api/v1/subjects/")
@RestController
public class SubjectsController {

    private final SubjectService subjectService;

    @ApiOperation(value = "서브젝트 등록", notes = "서브젝트를 등록하는 API입니다.")
    @PostMapping("")
    public Long save(
            @ApiParam(name = "서브젝트 등록 정보", value = "Subject 이름, Pdf 주소, 설명, Circle 번호", required = true)
            @RequestBody SubjectSaveRequestDto requestDto) {
        return subjectService.save(requestDto);
    }

    @ApiOperation(value = "서브젝트 내용 수정", notes = "서브젝트의 내용을 수정하는 API입니다.")
    @PutMapping("{id}")
    public Long save(
            @ApiParam(name = "수정할 서브젝트 id", value = "서브젝트 id", required = true)
            @PathVariable Long id,
            @ApiParam(name = "서브젝트 수정 정보", value = "Subject 이름, Pdf 주소, 설명, Circle 번호", required = true)
            @RequestBody SubjectUpdateRequestDto requestDto) {
        return subjectService.update(id, requestDto);
    }

    @ApiOperation(value = "서브젝트 삭제", notes = "서브젝트를 삭제하는 API입니다.")
    @DeleteMapping("{id}")
    public Long delete(
            @ApiParam(name = "삭제할 서브젝트 id", value = "서브젝트 id", required = true)
            @PathVariable Long id) {
        subjectService.delete(id);
        return id;
    }
}
