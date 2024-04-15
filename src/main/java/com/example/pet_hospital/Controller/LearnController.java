package com.example.pet_hospital.Controller;

import com.example.pet_hospital.Entity.result;
import com.example.pet_hospital.Service.LearnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController//前台角色扮演接口
public class LearnController {

    @Autowired
    private LearnService learnService;

    //根据角色返回岗位职责
    @GetMapping("/learn/getDuty")
    public result grtOrder(@RequestParam("role_id") String role_id){
        if (role_id == null || role_id.isEmpty()){
            return result.error("角色不能为空");
        }
        String role;
        switch (role_id) {
            case "1" -> role = "兽医";
            case "2" -> role = "助理";
            case "3" -> role = "前台";
            default -> {
                return result.error("角色不存在");
            }
        }


        return result.success(learnService.getLearnOrder(role));
    }

    //根据位置返回材料
//    @GetMapping("/learn/getItems")
//    public result getItems(@RequestParam("location_id") String location_id){
//        return result.success(learnService.getLearnItems(location_id));
//    }


    //返回所有地点和简介
    @GetMapping("/learn/getLocation")
    public result getLocation(@RequestHeader String Authorization){
        return result.success(learnService.getLocations());
    }

    //根据地点和角色返回学习视频和文字
    @GetMapping("/learn/getItem")
    public result getItem(@RequestParam("location_id") String location_id, @RequestParam("role_id") String role_id,
                          @RequestHeader String Authorization){


        if (role_id == null || role_id.isEmpty()){
            return result.error("角色不能为空");
        }
        if (location_id == null || location_id.isEmpty()){
            return result.error("地点id不能为空");
        }
        String role;
        switch (role_id) {
            case "1" -> role = "兽医";
            case "2" -> role = "助理";
            case "3" -> role = "前台";
            case "0" -> role = "游客";
            default -> {
                return result.error("角色不存在");
            }
        }

        String location_name = learnService.getLocationName(location_id);
        if(learnService.getLearnItem(location_name, role) == null){
            return result.error("地点不存在");
        }

        return result.success(learnService.getLearnItem(location_name, role));
    }

    //根据角色名称返回工作流程
    @GetMapping("/learn/getProcess")
    public result getProcess(@RequestParam("role_id") String role_id, @RequestHeader String Authorization){

        if (role_id == null || role_id.isEmpty()){
            return result.error("角色不能为空");
        }
        String role;
        switch (role_id) {
            case "1" -> role = "兽医";
            case "2" -> role = "助理";
            case "3" -> role = "前台";
            default -> {
                return result.error("角色不存在");
            }
        }




        return result.success(learnService.getLearnProcess(role));
    }


}
