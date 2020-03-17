package com.spider.controller;
import com.spider.entity.Area;
import com.spider.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhuliyang
 * @since 2020-03-05
 */
@RestController
@RequestMapping("/area")
public class AreaController {
    @Autowired
    private IAreaService areaService;
    @GetMapping("/searchId")
    public Area searchId(@RequestParam("id") int id){
        return areaService.getById(id);
    }
}

