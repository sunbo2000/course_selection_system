package org.snbo.dbservice.controller;

import org.snbo.commonutils.R;
import org.snbo.dbservice.bean.vo.OptionVo;
import org.snbo.dbservice.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author sunbo
 * @date 2022-06-17-22:40
 */
@RestController
@RequestMapping("/db/option")
public class OptionController {
    private final OptionService optionService;

    @Autowired
    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @PostMapping
    public R addScheme(@RequestBody OptionVo option) {
        optionService.startOption(option);
        return R.ok();
    }

    @DeleteMapping({"/{id}"})
    public R endTask(@PathVariable String id) {
        optionService.endTask(id);
        return R.ok();
    }

    @PostMapping("/{current}/{size}")
    public R getRecordPage(@PathVariable Long current, @PathVariable Long size) {
        Map<String, Object> map = optionService.getPage(current, size);
        return R.ok().data(map);
    }
}
