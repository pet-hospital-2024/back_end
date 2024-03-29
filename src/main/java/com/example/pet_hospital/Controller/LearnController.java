package com.example.pet_hospital.Controller;

import com.example.pet_hospital.Entity.result;
import com.example.pet_hospital.Service.LearnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController//前台角色扮演接口
public class LearnController {

    @Autowired
    private LearnService learnService;

    //根据角色返回学习顺序和每个地方的职责和每个地方的学习材料
    @GetMapping("/learn/getOrder")
    public result grtOrder(@RequestParam("learn_role") String role){
        if (role == null || role.isEmpty()){
            return result.error("角色不能为空");
        }
        if (learnService.getLearnOrder(role).length == 0){
            return result.error("角色不存在");
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
    public result getLocation(){
        return result.success(learnService.getLocations());
    }


}
