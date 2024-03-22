package com.example.pet_hospital.Controller;
import com.example.pet_hospital.Entity.disease;
import com.example.pet_hospital.Entity.instance;
import com.example.pet_hospital.Entity.kind;
import com.example.pet_hospital.Entity.result;
import com.example.pet_hospital.Service.DiseaseService;
import com.example.pet_hospital.Util.JWTUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;

@RestController
public class diseaseController {
    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Boolean identitySecure(String target, String Authorization){
        Claims claims = JWTUtils.jwtParser(Authorization);
        String identity=(String) claims.get("identity");
        if (target.equals(identity)){
            return true;
        }else {
            return false;
        }
    }

    public String newToken(String Authorization){
        Claims claims=JWTUtils.jwtParser(Authorization);
        String username=(String) claims.get("username");
        String user_id=(String) claims.get("user_id");
        String identity=(String) claims.get("identity");

        HashMap<String,Object> newclaim=new HashMap<>();
        newclaim.put("username",username);
        newclaim.put("user_id",user_id);
        newclaim.put("identity",identity);
        String token =JWTUtils.jwtGenerater(newclaim);
        return token;
    }

    //添加科室
    @PostMapping("/disease/addKind")
    public result addKind(@RequestBody kind k, @RequestHeader String Authorization) {
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (k.getName().equals("")){
            return result.error("科室名不能为空！");
        }
        if(diseaseService.getKindbyName(k)!=null){
            return result.error("该科室已存在！");
        }
        diseaseService.addKind(k);
        return result.success(newToken(Authorization));
    }

    //删除科室
    @PostMapping("/disease/deleteKind")
    public result deleteKind(@RequestBody kind k, @RequestHeader String Authorization) {
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if(diseaseService.getKindbyId(k.getKind_id())==null){
            return result.error("该科室不存在！");
        }
        diseaseService.deleteKind(k);
        return result.success(newToken(Authorization));
    }

    //修改科室
    @PostMapping("/disease/changeKind")
    public result changeKind(@RequestBody kind k, @RequestHeader String Authorization) {
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if(diseaseService.getKindbyId(k.getKind_id())==null){
            return result.error("该科室不存在！");
        }
        diseaseService.changeKind(k);
        return result.success(newToken(Authorization));
    }

    //获取所有科室
    @PostMapping("/disease/getAllKind")
    public result getAllKind() {
        return result.success(diseaseService.getAllKind());
    }

    //添加疾病
    @PostMapping("/disease/addDis")
    public result addDis(@RequestBody disease d, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (d.getName().equals("")){
            return result.error("疾病名不能为空！");
        }
        if(diseaseService.getKindbyId(d.getKind_id())==null){
            return result.error("该科室不存在！");
        }
        if(diseaseService.getDisbyName(d)!=null){
            return result.error("该疾病已存在！");
        }
        diseaseService.addDis(d);
        return result.success(newToken(Authorization));
    }

    //删除疾病
    @PostMapping("/disease/deleteDis")
    public result deleteDis(@RequestBody disease d, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if(diseaseService.getDisbyId(d.getUuid())==null){
            return result.error("该疾病不存在！");
        }
        diseaseService.deleteDis(d);
        return result.success(newToken(Authorization));
    }

    //修改疾病
    @PostMapping("/disease/changeDis")
    public result changeDis(@RequestBody disease d, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if(diseaseService.getDisbyId(d.getUuid())==null){
            return result.error("该疾病不存在！");
        }
        diseaseService.changeDis(d);
        return result.success(newToken(Authorization));
    }

    //查找某个科室下的所有疾病
    @PostMapping("/disease/searchbyKind")
    public result searchbyKind(@RequestBody disease d) {
        if(diseaseService.getKindbyId(d.getKind_id())==null){
            return result.error("该科室不存在！");
        }
        return result.success(diseaseService.searchbyKind(d.getKind_id()));
    }

    //查找某个疾病下的所有病例
    @PostMapping("/disease/getInstancebyDis")
    public result searchInstancebyDis(@RequestBody disease d) {
        if(diseaseService.getDisbyId(d.getUuid())==null){
            return result.error("该疾病不存在！");
        }
        return result.success(diseaseService.getInstancebyDis(d.getUuid()));
    }

    //添加病例
    @PostMapping("/disease/addInstance")
    public result addInstance(@RequestBody instance i, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (diseaseService.getInstancebyName(i.getName())!=null){
            return result.error("该病例已存在！");
        }
        /*
        if (i.getIntro().equals("")){
            return result.error("病例介绍不能为空！");
        }
        */
        if (i.getName().equals("")){
            return result.error("病例名不能为空！");
        }
        if(diseaseService.getDisbyId(i.getDis_id())==null){
            return result.error("该疾病不存在！");
        }
        diseaseService.addInstance(i);
        return result.success(newToken(Authorization));
    }

    //删除病例
    @PostMapping("/disease/deleteInstance")
    public result deleteInstance(@RequestBody instance i, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if(diseaseService.getInstancebyName(i.getName())==null){
            return result.error("该病例不存在！");
        }
        diseaseService.deleteInstance(i);
        return result.success(newToken(Authorization));
    }

    //修改病例文字信息
    @PostMapping("/disease/changeInstance")
    public result changeInstance(@RequestBody instance i, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if(diseaseService.getInstancebyId(i.getUuid())==null){
            return result.error("该病例不存在！");
        }
        diseaseService.changeInstance(i);
        return result.success(newToken(Authorization));
    }

    //获取病例文字信息
    @PostMapping("/disease/getInstancebyId")
    public result getInstancebyId(@RequestBody instance i){
        if(diseaseService.getInstancebyId(i.getUuid())==null){
            return result.error("该病例不存在！");
        }
        return result.success(diseaseService.getInstancebyId(i.getUuid()));
    }

    //根据输入的病例名称模糊搜索病例
    @PostMapping("/disease/searchInstance")
    public result searchInstancebyName(@RequestBody instance i){
        return result.success(diseaseService.searchInstance(i.getName()));
    }
}
