package com.example.pet_hospital.Controller;

import cn.hutool.json.JSONUtil;
import com.example.pet_hospital.Entity.*;
import com.example.pet_hospital.Service.DiseaseService;
import com.example.pet_hospital.Util.JWTUtils;
import com.example.pet_hospital.Entity.result;
import com.github.pagehelper.PageInfo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class diseaseController {
    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    String KIND_KEY="kind:";
    String CASE_KEY ="case:";
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
    @PostMapping("/disease/addDepartment")
    public result addDepartment(@RequestBody department k, @RequestHeader String Authorization) {
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (k.getDepartment_name().equals("")){
            return result.error("科室名不能为空！");
        }
        if(diseaseService.getDepartmentbyName(k)!=null){
            return result.error("该科室已存在！");
        }
        diseaseService.addDepartment(k);
        return result.success(newToken(Authorization));
    }

    //删除科室
    @PostMapping("/disease/deleteDepartment")
    public result deleteDepartment(@RequestBody department k, @RequestHeader String Authorization) {
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        //删除缓存
        if (stringRedisTemplate.opsForValue().get(KIND_KEY+k.getDepartment_id())!=null){
            stringRedisTemplate.delete(KIND_KEY+k.getDepartment_name());
        }
        if(diseaseService.getDepartmentbyId(k.getDepartment_id())==null){
            return result.error("该科室不存在！");
        }
        diseaseService.deleteDepartment(k);
        return result.success(newToken(Authorization));
    }

    //修改科室
    @PostMapping("/disease/changeDepartment")
    public result changeDepartment(@RequestBody department k, @RequestHeader String Authorization) {
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (stringRedisTemplate.opsForValue().get(KIND_KEY+k.getDepartment_id())!=null){
            stringRedisTemplate.opsForValue().
                    set(KIND_KEY+k.getDepartment_id(), JSONUtil.toJsonStr(k),
                            30, TimeUnit.MINUTES);
            diseaseService.changeDepartment(k);
            return result.success(newToken(Authorization));
        }
        if(diseaseService.getDepartmentbyId(k.getDepartment_id())==null){
            return result.error("该科室不存在！");
        }
        stringRedisTemplate.opsForValue().
                set(KIND_KEY+k.getDepartment_id(), JSONUtil.toJsonStr(k),
                        30, TimeUnit.MINUTES);
        diseaseService.changeDepartment(k);
        return result.success(newToken(Authorization));
    }

    //获取所有科室
    @PostMapping("/disease/getAllDepartment")
    public result getAllDepartment() {
        return result.success(diseaseService.getAllDepartment());
    }

    //添加疾病
    @PostMapping("/disease/addDisease")
    public result addDis(@RequestBody disease d, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (d.getDisease_name().equals("")){
            return result.error("疾病名不能为空！");
        }
        if(diseaseService.getDepartmentbyId(d.getDepartment_id())==null){
            return result.error("该科室不存在！");
        }
        if(diseaseService.getDiseasebyName(d)!=null){
            return result.error("该疾病已存在！");
        }
        diseaseService.addDisease(d);
        return result.success(newToken(Authorization));
    }

    //删除疾病
    @PostMapping("/disease/deleteDisease")
    public result deleteDis(@RequestBody disease d, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (stringRedisTemplate.opsForValue().get(DISEASE_KEY+d.getDisease_id())!=null){
            stringRedisTemplate.delete(DISEASE_KEY+d.getDisease_id());
        }
        if(diseaseService.getDiseasebyId(d.getDisease_id())==null){
            return result.error("该疾病不存在！");
        }
        diseaseService.deleteDisease(d);
        return result.success(newToken(Authorization));
    }

    //修改疾病
    @PostMapping("/disease/changeDiseaseName")
    public result changeDis(@RequestBody disease d, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (stringRedisTemplate.opsForValue().get(DISEASE_KEY+d.getDisease_id())!=null){
            stringRedisTemplate.opsForValue().
                    set(DISEASE_KEY+d.getDisease_id(), JSONUtil.toJsonStr(d),
                            30, TimeUnit.MINUTES);
            diseaseService.changeDiseaseName(d);
            return result.success(newToken(Authorization));
        }
        if(diseaseService.getDiseasebyId(d.getDisease_id())==null){
            return result.error("该疾病不存在！");
        }
        diseaseService.changeDiseaseName(d);
        return result.success(newToken(Authorization));
    }

    //查找某个科室下的所有疾病
    @PostMapping("/disease/getDiseasebyDepartment")
    public result getDiseasebyDepartment(@RequestBody department d) {
        if(diseaseService.getDepartmentbyId(d.getDepartment_id())==null){
            return result.error("该科室不存在！");
        }
        return result.success(diseaseService.getDiseasebyDepartment(d.getDepartment_id()));
    }

    //查找某个疾病下的所有病例
    //分页查找
//    @GetMapping("/disease/getCasebyDisease")
//    public result searchCasebyDis(@RequestParam(name = "disease_id") String disease_id,
//                                  @RequestParam(name = "page") int page,
//                                  @RequestParam(name = "pageSize") int size) {
//        if(diseaseService.getDiseasebyId(disease_id)==null){
//            return result.error("该疾病不存在！");
//        }
//        PageInfo<case_base> pageResult = diseaseService.findPaginatedbyDis(disease_id, page, size);
//        return result.success(pageResult);
//    }

    //查找某个疾病下的所有病例
    @GetMapping("/disease/getCasebyDisease")
    public result searchCasebyDis(@RequestParam(name = "disease_id") String disease_id) {
        if(diseaseService.getDiseasebyId(disease_id)==null){
            return result.error("该疾病不存在！");
        }
        return result.success(diseaseService.getCasebyDis(disease_id));
    }

    //添加病例
    @PostMapping("/disease/addCase")
    public result addCase(@RequestBody cases i, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (diseaseService.getCasebyName(i.getCase_name())!=null){
            return result.error("该病例已存在！");
        }
        /*
        if (i.getIntro().equals("")){
            return result.error("病例介绍不能为空！");
        }
        */
        if (i.getCase_name().equals("")){
            return result.error("病例名不能为空！");
        }
        if(diseaseService.getDiseasebyId(i.getDisease_id())==null){
            return result.error("该疾病不存在！");
        }
        if(diseaseService.getDepartmentbyId(i.getDepartment_id())==null){
            return result.error("该科室不存在！");
        }
        diseaseService.addCase(i);
        return result.success(newToken(Authorization));
    }

    //删除病例
    @PostMapping("/disease/deleteCase")
    public result deleteCase(@RequestBody cases i, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (stringRedisTemplate.opsForValue().
                get(CASE_KEY +i.getCase_id())!=null){
            stringRedisTemplate.delete(CASE_KEY +i.getCase_id());
        }
        if(diseaseService.getCasebyId(i.getCase_id())==null){
            return result.error("该病例不存在！");
        }
        diseaseService.deleteCase(i);
        return result.success(newToken(Authorization));
    }

    //修改病例文字信息
    @PostMapping("/disease/changeCaseTextbyId")
    public result changeCase(@RequestBody cases i, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (stringRedisTemplate.opsForValue().get(CASE_KEY +i.getCase_id())!=null){
            stringRedisTemplate.opsForValue().
                    set(CASE_KEY +i.getCase_id(),
                            JSONUtil.toJsonStr(i),30, TimeUnit.MINUTES);
            diseaseService.changeCase(i);
            return result.success(newToken(Authorization));
        }
        if(diseaseService.getCasebyId(i.getCase_id())==null){
            return result.error("该病例不存在！");
        }
        if(diseaseService.getDiseasebyId(i.getDisease_id())==null){
            return result.error("不能修改到不存在的疾病之下！");
        }
        if(diseaseService.getDepartmentbyId(i.getDepartment_id())==null){
            return result.error("不能修改到不存在的科室之下！");
        }
        diseaseService.changeCase(i);
        return result.success(newToken(Authorization));
    }

    //获取病例文字信息
    @GetMapping("/disease/getCaseTextbyId")
    public result getCasebyId(@RequestParam(name = "case_id") String case_id){
        if (stringRedisTemplate.opsForValue().
                get(CASE_KEY +case_id)!=null){//缓存命中
            return result.success(JSONUtil.toBean(stringRedisTemplate.
                    opsForValue().get(CASE_KEY +case_id), cases.class));
        }
        else {
            if(diseaseService.getCasebyId(case_id)==null){
                return result.error("该病例不存在！");
            }else{
                stringRedisTemplate.opsForValue().
                        set(CASE_KEY +case_id,
                                JSONUtil.toJsonStr(diseaseService.getCasebyId(case_id)),30, TimeUnit.MINUTES);
                return result.success(JSONUtil.
                        toBean(stringRedisTemplate.opsForValue().
                                get(CASE_KEY +case_id), cases.class));
            }
        }
    }

    //根据输入的病例名称模糊搜索病例
    @PostMapping("/disease/searchCase")
    public result searchCasebyName(@RequestBody cases i){
        return result.success(diseaseService.searchCase(i.getCase_name()));
    }

    @GetMapping("/disease/getCatalog")
    public result getCatalog() {
        List<department> departments = diseaseService.findAllDepartments();

        return result.success(departments);
    }

//    @GetMapping("/disease/getCaseList")
//    public result CaseList(){
//        return result.success(diseaseService.CaseList());
//    }

    //分页查询病例列表
    @GetMapping("/disease/getCaseList")
    public result getCaseList(@RequestParam(name = "page") int page,
                              @RequestParam(name = "pageSize") int size){
        PageInfo<cases> pageResult = diseaseService.findPaginated(page, size);
        return result.success(pageResult);
    }


    //增加病例症状图片
    @PostMapping("/disease/addCaseImg")
    public result addCaseImg(@RequestBody case_img i, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (i.getCase_img_name().equals("")){
            return result.error("图片名不能为空！");
        }
        if(diseaseService.getCasebyId(i.getCase_id())==null){
            return result.error("该病例不存在！");
        }
        diseaseService.addCaseImg(i);
        return result.success(newToken(Authorization));



    }

    //删除病例症状图片
    @PostMapping("/disease/deleteCaseImg")
    public result deleteCaseImg(@RequestBody case_img i, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if(diseaseService.getCaseImgbyId(i.getCase_img_id())==null){
            return result.error("该图片不存在！");
        }
        if (stringRedisTemplate.opsForValue().get(CASE_KEY +i.getCase_id())!=null){
            stringRedisTemplate.delete(CASE_KEY +i.getCase_id());
        }
        diseaseService.deleteCaseImg(i);
        return result.success(newToken(Authorization));
    }

    //根据病例获取病例症状所有图片
    @GetMapping("/disease/getCaseImgbyCase")
    public result getCaseImgbyCase(@RequestParam(name = "case_id") String case_id){
        return result.success(diseaseService.getCaseImgbyCase(case_id));
    }

    //增加病例介绍视频
    @PostMapping("/disease/addCaseVideo")
    public result addCaseVideo(@RequestBody case_video i, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (i.getCase_video_name().equals("")){
            return result.error("视频名不能为空！");
        }
        if(diseaseService.getCasebyId(i.getCase_id())==null){
            return result.error("该病例不存在！");
        }
        diseaseService.addCaseVideo(i);
        return result.success(newToken(Authorization));
    }

    //删除病例介绍视频
    @PostMapping("/disease/deleteCaseVideo")
    public result deleteCaseVideo(@RequestBody case_video i, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if(diseaseService.getCaseVideobyId(i.getCase_video_id())==null){
            return result.error("该视频不存在！");
        }
        if (stringRedisTemplate.opsForValue().get(CASE_KEY +i.getCase_id())!=null){
            stringRedisTemplate.delete(CASE_KEY +i.getCase_id());
        }
        diseaseService.deleteCaseVideo(i);
        return result.success(newToken(Authorization));
    }

    //根据病例获取病例症状所有视频
    @GetMapping("/disease/getCaseVideobyCase")
    public result getCaseVideobyCase(@RequestParam(name = "case_id") String case_id){
        return result.success(diseaseService.getCaseVideobyCase(case_id));
    }

    //增加手术视频
    @PostMapping("/disease/addOperationVideo")
    public result addOperationVideo(@RequestBody operation_video o, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (o.getCase_operation_name().equals("")){
            return result.error("视频名不能为空！");
        }
        if(diseaseService.getCasebyId(o.getCase_id())==null){
            return result.error("该病例不存在！");
        }
        diseaseService.addOperationVideo(o);
        return result.success(newToken(Authorization));
    }

    //删除手术视频
    @PostMapping("/disease/deleteOperationVideo")
    public result deleteOperationVideo(@RequestBody operation_video o, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if(diseaseService.getOperationVideobyId(o.getCase_operation_id())==null){
            return result.error("该视频不存在！");
        }
        if (stringRedisTemplate.opsForValue().get(CASE_KEY +o.getCase_id())!=null){
            stringRedisTemplate.delete(CASE_KEY +o.getCase_id());
        }

        diseaseService.deleteCaseOperationVideo(o);
        return result.success(newToken(Authorization));
    }

    //根据病例获取手术视频
    @GetMapping("/disease/getOperationVideobyCase")
    public result getOperationVideobyCase(@RequestParam(name = "case_id") String case_id){
        return result.success(diseaseService.getOperationVideobyCase(case_id));
    }

    //增加病例诊断结果图片
    @PostMapping("/disease/addResultImg")
    public result addResultImg(@RequestBody result_img r, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (r.getCase_resultimg_name().equals("")){
            return result.error("图片名不能为空！");
        }
        if(diseaseService.getCasebyId(r.getCase_id())==null){
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
        if(diseaseService.getResultImgbyId(r.getCase_resultimg_id())==null){
            return result.error("该图片不存在！");
        }
        if (stringRedisTemplate.opsForValue().get(CASE_KEY +r.getCase_id())!=null){
            stringRedisTemplate.delete(CASE_KEY +r.getCase_id());
        }
        diseaseService.deleteResultImg(r);
        return result.success(newToken(Authorization));
    }

    //根据病例获取诊断结果图片
    @GetMapping("/disease/getResultImgbyCase")
    public result getResultImgbyCase(@RequestParam(name = "case_id") String case_id){
        return result.success(diseaseService.getCaseResultImgbyCase(case_id));
    }

}
