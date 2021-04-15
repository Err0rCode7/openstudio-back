package com.codetogether.openstudio.contorller;

import com.codetogether.openstudio.service.PoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/v1/pools")
@RestController
public class PoolsController {

    private final PoolService poolService;

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) {
        poolService.deleteById(id);
        return id;
    }
}
