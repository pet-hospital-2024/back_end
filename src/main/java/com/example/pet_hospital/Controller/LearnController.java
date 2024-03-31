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
    public result grtOrder(@RequestParam("learn_role_id") String role_id){
        if (role_id == null || role_id.isEmpty()){
            return result.error("角色不能为空");
        }
        String role ="";
        if (role_id.equals("1")){
            role = "兽医";

        }else if (role_id.equals("2")){
            role = "助理";
        }else if (role_id.equals("3")){
            role = "前台";
        }else{
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

    //根据地点和角色返回学习视频和文字
    @GetMapping("/learn/getItem")
    public result getItem(@RequestParam("learn_location_id") String location_id, @RequestParam("learn_role_id") String role_id){


        if (role_id == null || role_id.isEmpty()){
            return result.error("角色不能为空");
        }
        if (location_id == null || location_id.isEmpty()){
            return result.error("地点id不能为空");
        }
        if (role_id == null || role_id.isEmpty()){
            return result.error("角色不能为空");
        }
        String role ="";
        if (role_id.equals("1")){
            role = "兽医";

        }else if (role_id.equals("2")){
            role = "助理";
        }else if (role_id.equals("3")){
            role = "前台";
        }else{
            return result.error("角色不存在");
        }

        String location_name = learnService.getLocationName(location_id);
        if(learnService.getLearnItem(location_name, role) == null){
            return result.error("地点不存在");
        }

        return result.success(learnService.getLearnItem(location_name, role));
    }

    //根据角色名称返回工作流程
    @GetMapping("/learn/getProcess")
    public result getProcess(@RequestParam("learn_role_id") String role_id){

        if (role_id == null || role_id.isEmpty()){
            return result.error("角色不能为空");
        }
        String role ="";
        if (role_id.equals("1")){
            role = "兽医";

        }else if (role_id.equals("2")){
            role = "助理";
        }else if (role_id.equals("3")){
            role = "前台";
        }else{
            return result.error("角色不存在");
        }




        return result.success(learnService.getLearnProcess(role));
    }


}
