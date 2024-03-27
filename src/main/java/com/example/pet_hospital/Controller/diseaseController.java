package com.example.pet_hospital.Controller;
import cn.hutool.json.JSONUtil;
import com.example.pet_hospital.Entity.*;
import com.example.pet_hospital.Service.DiseaseService;
import com.example.pet_hospital.Util.JWTUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
public class diseaseController {
    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    String KIND_KEY="kind:";
    String INSTANCE_KEY="instance:";
    String DISEASE_KEY="disease:";

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
        //删除缓存
        if (stringRedisTemplate.opsForValue().get(KIND_KEY+k.getKind_id())!=null){
            stringRedisTemplate.delete(KIND_KEY+k.getKind_id());
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
        if (stringRedisTemplate.opsForValue().get(KIND_KEY+k.getKind_id())!=null){
            stringRedisTemplate.opsForValue().
                    set(KIND_KEY+k.getKind_id(), JSONUtil.toJsonStr(k),
                            30, TimeUnit.MINUTES);
            diseaseService.changeKind(k);
            return result.success(newToken(Authorization));
        }
        if(diseaseService.getKindbyId(k.getKind_id())==null){
            return result.error("该科室不存在！");
        }
        stringRedisTemplate.opsForValue().
                set(KIND_KEY+k.getKind_id(), JSONUtil.toJsonStr(k),
                        30, TimeUnit.MINUTES);
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
        if (stringRedisTemplate.opsForValue().get(DISEASE_KEY+d.getDis_id())!=null){
            stringRedisTemplate.delete(DISEASE_KEY+d.getDis_id());
        }
        if(diseaseService.getDisbyId(d.getDis_id())==null){
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
        if (stringRedisTemplate.opsForValue().get(DISEASE_KEY+d.getDis_id())!=null){
            stringRedisTemplate.opsForValue().
                    set(DISEASE_KEY+d.getDis_id(), JSONUtil.toJsonStr(d),
                            30, TimeUnit.MINUTES);
            diseaseService.changeDis(d);
            return result.success(newToken(Authorization));
        }
        if(diseaseService.getDisbyId(d.getDis_id())==null){
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
        if(diseaseService.getDisbyId(d.getDis_id())==null){
            return result.error("该疾病不存在！");
        }
        return result.success(diseaseService.getInstancebyDis(d.getDis_id()));
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
        if (stringRedisTemplate.opsForValue().
                get(INSTANCE_KEY+i.getInstance_id())!=null){
            stringRedisTemplate.delete(INSTANCE_KEY+i.getInstance_id());
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
        if (stringRedisTemplate.opsForValue().get(INSTANCE_KEY+i.getInstance_id())!=null){
            stringRedisTemplate.opsForValue().
                    set(INSTANCE_KEY+i.getInstance_id(),
                            JSONUtil.toJsonStr(i),30, TimeUnit.MINUTES);
            diseaseService.changeInstance(i);
            return result.success(newToken(Authorization));
        }
        if(diseaseService.getInstancebyId(i.getInstance_id())==null){
            return result.error("该病例不存在！");
        }
        diseaseService.changeInstance(i);
        return result.success(newToken(Authorization));
    }

    //获取病例文字信息
    @PostMapping("/disease/getInstancebyId")
    public result getInstancebyId(@RequestBody instance i){
        if (stringRedisTemplate.opsForValue().
                get(INSTANCE_KEY+i.getInstance_id())!=null){//缓存命中
            return result.success(JSONUtil.toBean(stringRedisTemplate.
                    opsForValue().get(INSTANCE_KEY+i.getInstance_id()), question.class));
        }
        else {
            if(diseaseService.getInstancebyId(i.getInstance_id())==null){
                return result.error("该病例不存在！");
            }else{
                stringRedisTemplate.opsForValue().
                        set(INSTANCE_KEY+i.getInstance_id(),
                                JSONUtil.toJsonStr(diseaseService.getInstancebyId(i.getInstance_id())),30, TimeUnit.MINUTES);
                return result.success(JSONUtil.
                        toBean(stringRedisTemplate.opsForValue().
                                get(INSTANCE_KEY+i.getInstance_id()), question.class));
            }
        }
    }

    //根据输入的病例名称模糊搜索病例
    @PostMapping("/disease/searchInstance")
    public result searchInstancebyName(@RequestBody instance i){
        return result.success(diseaseService.searchInstance(i.getName()));
    }

    //增加病例症状图片
    @PostMapping("/disease/addInstanceImg")
    public result addInstanceImg(@RequestBody instance_img i, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (i.getInstance_img_name().equals("")){
            return result.error("图片名不能为空！");
        }
        if(diseaseService.getInstancebyId(i.getInstance_id())==null){
            return result.error("该病例不存在！");
        }
        diseaseService.addInstanceImg(i);
        return result.success(newToken(Authorization));



    }

    //删除病例症状图片
    @PostMapping("/disease/deleteInstanceImg")
    public result deleteInstanceImg(@RequestBody instance_img i, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if(diseaseService.getInstanceImgbyId(i.getInstance_img_id())==null){
            return result.error("该图片不存在！");
        }
        if (stringRedisTemplate.opsForValue().get(INSTANCE_KEY+i.getInstance_id())!=null){
            stringRedisTemplate.delete(INSTANCE_KEY+i.getInstance_id());
        }
        diseaseService.deleteInstanceImg(i);
        return result.success(newToken(Authorization));
    }

    //根据病例获取病例症状所有图片
    @GetMapping("/disease/getInstanceImgbyInstance")
    public result getInstanceImgbyInstance(@RequestBody instance i){
        return result.success(diseaseService.getInstanceImgbyInstance(i.getInstance_id()));
    }

    //增加病例介绍视频
    @PostMapping("/disease/addInstanceVideo")
    public result addInstanceVideo(@RequestBody instance_video i, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (i.getInstance_video_name().equals("")){
            return result.error("视频名不能为空！");
        }
        if(diseaseService.getInstancebyId(i.getInstance_id())==null){
            return result.error("该病例不存在！");
        }
        diseaseService.addInstanceVideo(i);
        return result.success(newToken(Authorization));
    }

    //删除病例介绍视频
    @PostMapping("/disease/deleteInstanceVideo")
    public result deleteInstanceVideo(@RequestBody instance_video i, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if(diseaseService.getInstanceVideobyId(i.getInstance_video_id())==null){
            return result.error("该视频不存在！");
        }
        if (stringRedisTemplate.opsForValue().get(INSTANCE_KEY+i.getInstance_id())!=null){
            stringRedisTemplate.delete(INSTANCE_KEY+i.getInstance_id());
        }
        diseaseService.deleteInstanceVideo(i);
        return result.success(newToken(Authorization));
    }

    //根据病例获取病例症状所有视频
    @GetMapping("/disease/getInstanceVideobyInstance")
    public result getInstanceVideobyInstance(@RequestBody instance i){
        return result.success(diseaseService.getInstanceVideobyInstance(i.getInstance_id()));
    }

    //增加手术视频
    @PostMapping("/disease/addOperationVideo")
    public result addOperationVideo(@RequestBody operation_video o, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (o.getInstance_operation_name().equals("")){
            return result.error("视频名不能为空！");
        }
        if(diseaseService.getInstancebyId(o.getInstance_id())==null){
            return result.error("该病例不存在！");
        }
        diseaseService.addIntanceOperationVideo(o);
        return result.success(newToken(Authorization));
    }

    //删除手术视频
    @PostMapping("/disease/deleteOperationVideo")
    public result deleteOperationVideo(@RequestBody operation_video o, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if(diseaseService.getOperationVideobyId(o.getInstance_operation_id())==null){
            return result.error("该视频不存在！");
        }
        if (stringRedisTemplate.opsForValue().get(INSTANCE_KEY+o.getInstance_id())!=null){
            stringRedisTemplate.delete(INSTANCE_KEY+o.getInstance_id());
        }

        diseaseService.deleteInstanceOperationVideo(o);
        return result.success(newToken(Authorization));
    }

    //根据病例获取手术视频
    @GetMapping("/disease/getOperationVideobyInstance")
    public result getOperationVideobyInstance(@RequestBody operation_video o){
        return result.success(diseaseService.getOperationVideobyInstance(o.getInstance_id()));
    }

    //增加病例诊断结果图片
    @PostMapping("/disease/addResultImg")
    public result addResultImg(@RequestBody result_img r, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (r.getInstance_resultimg_name().equals("")){
            return result.error("图片名不能为空！");
        }
        if(diseaseService.getInstancebyId(r.getInstance_id())==null){
            return result.error("该病例不存在！");
        }
        diseaseService.addResultImg(r);
        return result.success(newToken(Authorization));
    }

    //删除病例诊断结果图片
    @PostMapping("/disease/deleteResultImg")
    public result deleteResultImg(@RequestBody result_img r, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if(diseaseService.getResultImgbyId(r.getInstance_resultimg_id())==null){
            return result.error("该图片不存在！");
        }
        if (stringRedisTemplate.opsForValue().get(INSTANCE_KEY+r.getInstance_id())!=null){
            stringRedisTemplate.delete(INSTANCE_KEY+r.getInstance_id());
        }
        diseaseService.deleteResultImg(r);
        return result.success(newToken(Authorization));
    }

    //根据病例获取诊断结果图片
    @GetMapping("/disease/getResultImgbyInstance")
    public result getResultImgbyInstance(@RequestBody result_img r){
        return result.success(diseaseService.getInstanceResultImgbyInstance(r.getInstance_id()));
    }

}
