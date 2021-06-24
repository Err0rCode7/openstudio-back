package com.codetogether.openstudio.controller.apis.v1;

import com.codetogether.openstudio.service.PoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Pool API"})
@RequiredArgsConstructor
@RequestMapping("api/v1/pools/")
@RestController
public class PoolsController {

    private final PoolService poolService;

    @ApiOperation(value = "Pool 제거", notes = "요청한 id의 pool을 제거하는 API입니다.")
    @DeleteMapping("{id}")
    public Long delete(
            @ApiParam(name = "삭제할 pool id", value = "Pool id", required = true)
            @PathVariable Long id) {
        poolService.deleteById(id);
        return id;
    }
}
