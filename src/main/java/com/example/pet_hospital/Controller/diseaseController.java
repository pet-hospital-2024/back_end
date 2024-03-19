package com.example.pet_hospital.Controller;
import com.example.pet_hospital.Entity.disease;
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

import java.util.HashMap;

@RestController
public class diseaseController {
    @Autowired
    private DiseaseService diseaseService;

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

    @PostMapping("/disease/addKind")
    public result addKind(@RequestBody kind k, @RequestHeader String Authorization) {
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if(diseaseService.getKindbyName(k)!=null){
            return result.error("该科室已存在！");
        }
        diseaseService.addKind(k);
        return result.success(newToken(Authorization));
    }

    @PostMapping("/disease/deleteKind")
    public result deleteKind(@RequestBody kind k, @RequestHeader String Authorization) {
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if(diseaseService.getKindbyId(k.getId())==null){
            return result.error("该科室不存在！");
        }
        diseaseService.deleteKind(k);
        return result.success(newToken(Authorization));
    }

    @PostMapping("/disease/changeKind")
    public result changeKind(@RequestBody kind k, @RequestHeader String Authorization) {
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if(diseaseService.getKindbyId(k.getId())==null){
            return result.error("该科室不存在！");
        }
        diseaseService.changeKind(k);
        return result.success(newToken(Authorization));
    }

    @PostMapping("/disease/getAllKind")
    public result getAllKind() {
        return result.success(diseaseService.getAllKind());
    }

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

    @PostMapping("/disease/deleteDis")
    public result deleteDis(@RequestBody disease d, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if(diseaseService.getDisbyId(d)==null){
            return result.error("该疾病不存在！");
        }
        diseaseService.deleteDis(d);
        return result.success(newToken(Authorization));
    }

    @PostMapping("/disease/changeDis")
    public result changeDis(@RequestBody disease d, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if(diseaseService.getDisbyId(d)==null){
            return result.error("该疾病不存在！");
        }
        return result.success(newToken(Authorization));
    }

    @PostMapping("/disease/searchbyKind")
    public result searchbyKind(@RequestBody disease d) {
        if(diseaseService.getKindbyId(d.getKind_id())==null){
            return result.error("该科室不存在！");
        }
        return result.success(diseaseService.searchbyKind(d.getKind_id()));
    }

}
