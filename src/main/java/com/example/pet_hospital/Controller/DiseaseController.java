package com.example.pet_hospital.Controller;

import cn.hutool.json.JSONUtil;
import com.example.pet_hospital.Entity.*;
import com.example.pet_hospital.Service.DiseaseService;
import com.example.pet_hospital.Util.JWTUtils;
import com.github.pagehelper.PageInfo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class DiseaseController {
    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    String KIND_KEY = "kind:";
    String CASE_KEY = "case:";
    String DISEASE_KEY = "disease:";

    public Boolean identitySecure(String target, String Authorization) {
        Claims claims = JWTUtils.jwtParser(Authorization);
        String identity = (String) claims.get("identity");
        return target.equals(identity);
    }

    public String newToken(String Authorization) {
        Claims claims = JWTUtils.jwtParser(Authorization);

        String username = (String) claims.get("username");
        String user_id = (String) claims.get("user_id");
        String identity = (String) claims.get("identity");

        HashMap<String, Object> newclaim = new HashMap<>();
        newclaim.put("username", username);
        newclaim.put("user_id", user_id);
        newclaim.put("identity", identity);
        if (JWTUtils.refreshTokenNeeded(Authorization)){
            return JWTUtils.jwtGenerater(newclaim);
        }
        else {
            return Authorization;
        }
    }

    //添加科室
    @PostMapping("/disease/addDepartment")
    public result addDepartment(@RequestBody department k, @RequestHeader String Authorization) {
        if (identitySecure("user", Authorization)) {
            return result.error("无操作权限！");
        }
        if (k.getDepartment_name().isEmpty()) {
            return result.error("科室名不能为空！");
        }
        if (diseaseService.getDepartmentbyName(k) != null) {
            return result.error("该科室已存在！");
        }
        diseaseService.addDepartment(k);
        return result.success(newToken(Authorization));
    }

    //删除科室
    @PostMapping("/disease/deleteDepartment")
    public result deleteDepartment(@RequestBody department k, @RequestHeader String Authorization) {
        if (identitySecure("user", Authorization)) {
            return result.error("无操作权限！");
        }
        //设置基础科室不可被删除
        //基础科室的id：100759049863168001，100776782742945792，100776782742945798，100776782742945799
        //100776782742945800,100776782742945801,100776782742945802,123123
        if (k.getDepartment_id().equals("100759049863168001") || k.getDepartment_id().equals("100776782742945792")
                || k.getDepartment_id().equals("100776782742945798") || k.getDepartment_id().equals("100776782742945799")
                || k.getDepartment_id().equals("100776782742945800") || k.getDepartment_id().equals("100776782742945801")
                || k.getDepartment_id().equals("100776782742945802") || k.getDepartment_id().equals("123123")) {
            return result.error("基础科室不可被删除！");
        }


        //删除缓存
        if (stringRedisTemplate.opsForValue().get(KIND_KEY + k.getDepartment_id()) != null) {
            stringRedisTemplate.delete(KIND_KEY + k.getDepartment_name());
        }
        if (diseaseService.getDepartmentbyId(k.getDepartment_id()) == null) {
            return result.error("该科室不存在！");
        }
        diseaseService.deleteDepartment(k);
        return result.success(newToken(Authorization));
    }

    //修改科室
    @PostMapping("/disease/changeDepartment")
    public result changeDepartment(@RequestBody department k, @RequestHeader String Authorization) {
        if (identitySecure("user", Authorization)) {
            return result.error("无操作权限！");
        }
        if (stringRedisTemplate.opsForValue().get(KIND_KEY + k.getDepartment_id()) != null) {
            stringRedisTemplate.opsForValue().
                    set(KIND_KEY + k.getDepartment_id(), JSONUtil.toJsonStr(k),
                            30, TimeUnit.MINUTES);
            diseaseService.changeDepartment(k);
            return result.success(newToken(Authorization));
        }
        if (diseaseService.getDepartmentbyId(k.getDepartment_id()) == null) {
            return result.error("该科室不存在！");
        }
        stringRedisTemplate.opsForValue().
                set(KIND_KEY + k.getDepartment_id(), JSONUtil.toJsonStr(k),
                        30, TimeUnit.MINUTES);
        diseaseService.changeDepartment(k);
        return result.success(newToken(Authorization));
    }

    //获取所有科室
    @GetMapping("/disease/getAllDepartment")
    public result getAllDepartment(@RequestHeader String Authorization,
                                   @RequestParam(name = "page", defaultValue = "1") int page,
                                   @RequestParam(name = "size", defaultValue = "10") int size) {
        if (identitySecure("user", Authorization)) {
            return result.error("无操作权限！");
        }
        if (page <= 0 || size <= 0) {
            return result.error("参数错误！");
        }
        PageInfo<department> pageResult = diseaseService.getAllDepartment(page, size);
        return result.success(pageResult);
    }

    //添加疾病
    @PostMapping("/disease/addDisease")
    public result addDis(@RequestBody disease d, @RequestHeader String Authorization) {
        if (identitySecure("user", Authorization)) {
            return result.error("无操作权限！");
        }
        if (d.getDisease_name().isEmpty()) {
            return result.error("疾病名不能为空！");
        }
        if (diseaseService.getDepartmentbyId(d.getDepartment_id()) == null) {
            return result.error("该科室不存在！");
        }
        if (diseaseService.getDiseasebyName(d) != null) {
            return result.error("该疾病已存在！");
        }
        diseaseService.addDisease(d);
        return result.success(newToken(Authorization));
    }

    //删除疾病
    @PostMapping("/disease/deleteDisease")
    public result deleteDis(@RequestBody disease d, @RequestHeader String Authorization) {
        if (identitySecure("user", Authorization)) {
            return result.error("无操作权限！");
        }
        if (stringRedisTemplate.opsForValue().get(DISEASE_KEY + d.getDisease_id()) != null) {
            stringRedisTemplate.delete(DISEASE_KEY + d.getDisease_id());
        }
        if (diseaseService.getDiseasebyId(d.getDisease_id()) == null) {
            return result.error("该疾病不存在！");
        }
        diseaseService.deleteDisease(d);
        return result.success(newToken(Authorization));
    }

    //修改疾病
    @PostMapping("/disease/changeDiseaseName")
    public result changeDis(@RequestBody disease d, @RequestHeader String Authorization) {
        if (identitySecure("user", Authorization)) {
            return result.error("无操作权限！");
        }
        if (stringRedisTemplate.opsForValue().get(DISEASE_KEY + d.getDisease_id()) != null) {
            stringRedisTemplate.opsForValue().
                    set(DISEASE_KEY + d.getDisease_id(), JSONUtil.toJsonStr(d),
                            30, TimeUnit.MINUTES);
            diseaseService.changeDiseaseName(d);
            return result.success(newToken(Authorization));
        }
        if (diseaseService.getDiseasebyId(d.getDisease_id()) == null) {
            return result.error("该疾病不存在！");
        }
        diseaseService.changeDiseaseName(d);
        return result.success(newToken(Authorization));
    }

    //查找某个科室下的所有疾病
    @GetMapping("/disease/getDiseasebyDepartment")
    public result getDiseasebyDepartment(@RequestParam(name = "department_id") String department_id,
                                         @RequestHeader String Authorization,
                                         @RequestParam(name = "page", defaultValue = "1") int page,
                                         @RequestParam(name = "size", defaultValue = "10") int size) {

        department d = new department();
        d.setDepartment_id(department_id);

        if (diseaseService.getDepartmentbyId(d.getDepartment_id()) == null) {
            return result.error("该科室不存在！");
        }
        PageInfo<disease> pageResult = diseaseService.getDiseasebyDepartment(d.getDepartment_id(), page, size);
        return result.success(pageResult);
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
    public result searchCasebyDis(@RequestParam(name = "disease_id") String disease_id, @RequestHeader String Authorization) {
        if (diseaseService.getDiseasebyId(disease_id) == null) {
            return result.error("该疾病不存在！");
        }
        return result.success(diseaseService.getCasebyDis(disease_id));
    }

    //添加病例
    @PostMapping("/disease/addCase")
    public result addCase(@RequestBody cases i, @RequestHeader String Authorization) {
        if (identitySecure("user", Authorization)) {
            return result.error("无操作权限！");
        }
        if (diseaseService.getCasebyName(i.getCase_name()) != null) {
            return result.error("该病例已存在！");
        }
        /*
        if (i.getIntro().equals("")){
            return result.error("病例介绍不能为空！");
        }
        */
        if (i.getCase_name().isEmpty()) {
            return result.error("病例名不能为空！");
        }
        if (diseaseService.getDiseasebyId(i.getDisease_id()) == null) {
            return result.error("该疾病不存在！");
        }
        if (diseaseService.getDepartmentbyId(i.getDepartment_id()) == null) {
            return result.error("该科室不存在！");
        }
        diseaseService.addCase(i);
        return result.success(newToken(Authorization));
    }

    //删除病例
    @PostMapping("/disease/deleteCase")
    public result deleteCase(@RequestBody cases i, @RequestHeader String Authorization) {
        if (identitySecure("user", Authorization)) {
            return result.error("无操作权限！");
        }
        if (stringRedisTemplate.opsForValue().
                get(CASE_KEY + i.getCase_id()) != null) {
            stringRedisTemplate.delete(CASE_KEY + i.getCase_id());
        }
        if (diseaseService.getCasebyId(i.getCase_id()) == null) {
            return result.error("该病例不存在！");
        }
        diseaseService.deleteCase(i);
        return result.success(newToken(Authorization));
    }

    //修改病例文字信息
    @PostMapping("/disease/changeCaseTextbyId")
    public result changeCase(@RequestBody cases i, @RequestHeader String Authorization) {
        if (identitySecure("user", Authorization)) {
            return result.error("无操作权限！");
        }
        if (stringRedisTemplate.opsForValue().get(CASE_KEY + i.getCase_id()) != null) {
            stringRedisTemplate.opsForValue().
                    set(CASE_KEY + i.getCase_id(),
                            JSONUtil.toJsonStr(i), 30, TimeUnit.MINUTES);
            diseaseService.changeCase(i);
            return result.success(newToken(Authorization));
        }
        if (diseaseService.getCasebyId(i.getCase_id()) == null) {
            return result.error("该病例不存在！");
        }
        if (diseaseService.getDiseasebyId(i.getDisease_id()) == null) {
            return result.error("不能修改到不存在的疾病之下！");
        }
        if (diseaseService.getDepartmentbyId(i.getDepartment_id()) == null) {
            return result.error("不能修改到不存在的科室之下！");
        }
        diseaseService.changeCase(i);
        return result.success(newToken(Authorization));
    }

    //获取病例文字信息
    @GetMapping("/disease/getCaseTextbyId")
    public result getCasebyId(@RequestParam(name = "case_id") String case_id, @RequestHeader String Authorization) {
        if (stringRedisTemplate.opsForValue().
                get(CASE_KEY + case_id) != null) {//缓存命中
            return result.success(JSONUtil.toBean(stringRedisTemplate.
                    opsForValue().get(CASE_KEY + case_id), cases.class));
        } else {
            if (diseaseService.getCasebyId(case_id) == null) {
                return result.error("该病例不存在！");
            } else {
                stringRedisTemplate.opsForValue().
                        set(CASE_KEY + case_id,
                                JSONUtil.toJsonStr(diseaseService.getCasebyId(case_id)), 30, TimeUnit.MINUTES);
                return result.success(JSONUtil.
                        toBean(stringRedisTemplate.opsForValue().
                                get(CASE_KEY + case_id), cases.class));
            }
        }
    }

    //根据输入的病例名称模糊搜索病例
    @PostMapping("/disease/searchCase")
    public result searchCasebyName(@RequestBody cases i) {
        return result.success(diseaseService.searchCase(i.getCase_name()));
    }

    @GetMapping("/disease/getCatalog")
    public result getCatalog(@RequestHeader String Authorization) {
        List<department> departments = diseaseService.findAllDepartments();

        return result.success(departments);
    }

//    @GetMapping("/disease/getCaseList")
//    public result CaseList(){
//        return result.success(diseaseService.CaseList());
//    }

    //分页查询病例列表
    @GetMapping("/disease/getCaseList")
    public result getCaseList(@RequestParam(name = "page", defaultValue = "1") int page,
                              @RequestParam(name = "pageSize", defaultValue = "10") int size,
                              @RequestHeader String Authorization) {
        if (page <= 0 || size <= 0) {
            return result.error("参数错误！");
        }
        PageInfo<cases> pageResult = diseaseService.findPaginated(page, size);
        return result.success(pageResult);
    }


    //添加病例多媒体
    //media_type只能是image或者video，对应 图片 或者 视频 ；
    //
    //category:
    //用于指明多媒体文件属于四个类别中的哪一个。
    //接诊 病例检查 诊断结果 治疗方案
    //只能是以下四个之一
    //Consultation Examination Result Treatment
    @PostMapping("/disease/addMedia")
    public result addMedia(@RequestParam("case_id") String caseId,

                           @RequestParam("category") String category,
                           @RequestParam("file") MultipartFile file,
                           @RequestHeader String Authorization) throws Exception {

        cases m = new cases();
        m.setCase_id(caseId);

        m.setCategory(category);
        m.setFile(file);
        if (identitySecure("user", Authorization)) {
            return result.error("无操作权限！");
        }
        if (m.getCase_id().isEmpty()) {
            return result.error("病例id不能为空！");
        }

        String contentType = file.getContentType();

        // 检查文件类型是否为图片或视频
        if (!(contentType != null && (contentType.startsWith("image/") || contentType.startsWith("video/"))))
            return result.error("文件类型只能是图片或者视频！");

       

        if (m.getCategory().isEmpty()) {
            return result.error("媒体类别不能为空！");
        }
        if (diseaseService.getCasebyId(m.getCase_id()) == null) {
            return result.error("该病例不存在！");
        }
        


        if (!(m.getCategory().equals("Consultation") || m.getCategory().equals("Examination")
                || m.getCategory().equals("Result") || m.getCategory().equals("Treatment"))) {
            return result.error("媒体类别只能是Consultation,Examination,Result,Treatment之一！");
        }
        diseaseService.uploadMedia(m);
        return result.success(newToken(Authorization));
    }

    //编辑后的图片替换掉原来的(通过media_id和file)
    @PostMapping("/disease/changeMedia")
    public result changeMedia(@RequestParam("media_id") String mediaId,
                              @RequestParam("file") MultipartFile file,
                              @RequestHeader String Authorization) throws Exception {
        cases m = new cases();
        m.setMedia_id(mediaId);
        m.setFile(file);
        if (identitySecure("user", Authorization)) {
            return result.error("无操作权限！");
        }
        if (m.getMedia_id().isEmpty()) {
            return result.error("媒体id不能为空！");
        }
        if (diseaseService.getMediabyId(m.getMedia_id()) == null) {
            return result.error("该媒体不存在！");
        }
        String contentType = file.getContentType();

        // 检查文件类型是否为图片或视频
        if (!(contentType != null && (contentType.startsWith("image/") || contentType.startsWith("video/"))))
            return result.error("文件类型只能是图片或者视频！");

        diseaseService.changeMedia(m);
        return result.success(newToken(Authorization));
    }







    //删除病例多媒体
    @PostMapping("/disease/deleteMedia")
    public result deleteMedia(@RequestBody cases m, @RequestHeader String Authorization) throws Exception {
        if (identitySecure("user", Authorization)) {
            return result.error("无操作权限！");
        }
        if (diseaseService.getMediabyId(m.getMedia_id()) == null) {
            return result.error("该媒体不存在！");
        }
        String id = m.getMedia_id();
        diseaseService.deleteMedia(id);
        return result.success(newToken(Authorization));
    }


    //获取病例多媒体
    //可以选择type和category进行筛选
    //type和category都为空则返回所有多媒体
    //getMedia?case_id=212&media_type=image&category=Consultation
    @GetMapping("/disease/getMedia")
    public result getMedia(@RequestParam(name = "case_id", required = false) String case_id,
                           @RequestParam(name = "media_type", required = false) String media_type,
                           @RequestParam(name = "category", required = false) String category,
                           @RequestHeader String Authorization) {

        //media_type只能是image或者video，对应 图片 或者 视频 ；
        //
        //category:
        //用于指明多媒体文件属于四个类别中的哪一个。
        //接诊 病例检查 诊断结果 治疗方案
        //只能是以下四个之一
        //Consultation Examination Result Treatment
        if (media_type != null && !media_type.equals("image") && !media_type.equals("video")) {
            return result.error("媒体类型只能是image或者video！");
        }
        if (category != null && !(category.equals("Consultation") || category.equals("Examination")
                || category.equals("Result") || category.equals("Treatment"))) {
            return result.error("媒体类别只能是Consultation,Examination,Result,Treatment之一！");
        }

        if (case_id == null && media_type == null && category == null) {
            return result.success(diseaseService.findAllMedia());
        }
        if (case_id != null && media_type == null && category == null) {
            return result.success(diseaseService.getMediaByCaseId(case_id));
        }
        if (case_id == null && media_type != null && category == null) {
            return result.success(diseaseService.getMediaByType(media_type));
        }
        if (case_id == null && media_type == null && category != null) {
            return result.success(diseaseService.getMediaByCategory(category));
        }
        if (case_id != null && media_type != null && category == null) {
            return result.success(diseaseService.getMediaByCaseIdAndType(case_id, media_type));
        }
        if (case_id != null && media_type == null && category != null) {
            return result.success(diseaseService.getMediaByCaseIdAndCategory(case_id, category));
        }
        if (case_id == null && media_type != null && category != null) {
            return result.success(diseaseService.getMediaByTypeAndCategory(media_type, category));
        }
        if (case_id != null && media_type != null && category != null) {
            return result.success(diseaseService.getMediaByCaseIdAndTypeAndCategory(case_id, media_type, category));
        }
        return result.error("参数错误！");
    }

    //获取病例多媒体
    //只返回一个URL列表，其他信息不返回
    //可以选择type和category进行筛选
    //type和category都为空则返回所有多媒体
    //getMedia?case_id=212&media_type=image&category=Consultation
    @GetMapping("/disease/getMediaURL")
    public result getMediaURL(@RequestParam(name = "case_id", required = false) String case_id,
                              @RequestParam(name = "media_type", required = false) String media_type,
                              @RequestParam(name = "category", required = false) String category,
                              @RequestHeader String Authorization) {

        //media_type只能是image或者video，对应 图片 或者 视频 ；
        //
        //category:
        //用于指明多媒体文件属于四个类别中的哪一个。
        //接诊 病例检查 诊断结果 治疗方案
        //只能是以下四个之一
        //Consultation Examination Result Treatment
        if (media_type != null && !media_type.equals("image") && !media_type.equals("video")) {
            return result.error("媒体类型只能是image或者video！");
        }
        if (category != null && !(category.equals("Consultation") || category.equals("Examination")
                || category.equals("Result") || category.equals("Treatment"))) {
            return result.error("媒体类别只能是Consultation,Examination,Result,Treatment之一！");
        }

        if (case_id == null && media_type == null && category == null) {
            cases[] media = diseaseService.findAllMedia();
            String[] urls = new String[media.length];
            for (int i = 0; i < media.length; i++) {
                urls[i] = media[i].getMedia_url();
            }
            return result.success(urls);
        }
        if (case_id != null && media_type == null && category == null) {
            cases[] media = diseaseService.getMediaByCaseId(case_id);
            String[] urls = new String[media.length];
            for (int i = 0; i < media.length; i++) {
                urls[i] = media[i].getMedia_url();
            }
            return result.success(urls);
        }
        if (case_id == null && media_type != null && category == null) {
            cases[] media = diseaseService.getMediaByType(media_type);
            String[] urls = new String[media.length];
            for (int i = 0; i < media.length; i++) {
                urls[i] = media[i].getMedia_url();
            }
            return result.success(urls);
        }
        if (case_id == null && media_type == null && category != null) {
            cases[] media = diseaseService.getMediaByCategory(category);
            String[] urls = new String[media.length];
            for (int i = 0; i < media.length; i++) {
                urls[i] = media[i].getMedia_url();
            }
            return result.success(urls);
        }
        if (case_id != null && media_type != null && category == null) {
            cases[] media = diseaseService.getMediaByCaseIdAndType(case_id, media_type);
            String[] urls = new String[media.length];
            for (int i = 0; i < media.length; i++) {
                urls[i] = media[i].getMedia_url();
            }
            return result.success(urls);
        }
        if (case_id != null && media_type == null && category != null) {
            cases[] media = diseaseService.getMediaByCaseIdAndCategory(case_id, category);
            String[] urls = new String[media.length];
            for (int i = 0; i < media.length; i++) {
                urls[i] = media[i].getMedia_url();
            }
            return result.success(urls);
        }
        if (case_id == null && media_type != null && category != null) {
            cases[] media = diseaseService.getMediaByTypeAndCategory(media_type, category);
            String[] urls = new String[media.length];
            for (int i = 0; i < media.length; i++) {
                urls[i] = media[i].getMedia_url();
            }
            return result.success(urls);
        }
        if (case_id != null && media_type != null && category != null) {
            cases[] media = diseaseService.getMediaByCaseIdAndTypeAndCategory(case_id, media_type, category);
            String[] urls = new String[media.length];
            for (int i = 0; i < media.length; i++) {
                urls[i] = media[i].getMedia_url();
            }
            return result.success(urls);
        }
        return result.error("参数错误！");


    }


    /*

    //修改病例（文字和多媒体一起）
    @PostMapping("/disease/changeCase")
    public result changeCase(@RequestParam("media_name") String mediaName,
                             @RequestParam("category") String category,
                             @RequestParam("file") MultipartFile file,
                             @RequestParam("case_id") String caseId,
                             @RequestParam("case_name") String caseName,
                             @RequestParam("case_examination") String caseExamination,
                             @RequestParam("case_result") String caseResult,
                             @RequestParam("case_treatment") String caseTreatment,
                             @RequestParam("case_medicine") String caseMedicine,
                             @RequestParam("case_cost") String caseCost,
                             @RequestParam("case_introduction") String caseIntroduction,
                             @RequestParam("disease_name") String diseaseName,
                             @RequestParam("department_name") String departmentName,
                             @RequestHeader String Authorization) throws Exception {

        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (caseId.isEmpty()){
            return result.error("病例id不能为空！");
        }
        if (caseName.isEmpty()){
            return result.error("病例名不能为空！");
        }



        cases i = new cases();
        i.setCase_id(caseId);
        i.setCase_name(caseName);
        i.setCase_examination(caseExamination);
        i.setCase_result(caseResult);
        i.setCase_treatment(caseTreatment);
        i.setCase_medicine(caseMedicine);
        i.setCase_cost(caseCost);
        i.setCase_introduction(caseIntroduction);
        //根据disease和department的name返回id
        disease d = new disease();
        d.setDisease_name(diseaseName);
        i.setDisease_id(diseaseService.getDiseasebyName(d).getDisease_id());
        department k = new department();
        k.setDepartment_name(departmentName);
        i.setDepartment_id(diseaseService.getDepartmentbyName(k).getDepartment_id());
        i.setFile(file);
        i.setMedia_name(mediaName);
        i.setCategory(category);

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

        //修改多媒体
        if (file!=null){
            cases m = new cases();
            m.setCase_id(caseId);
            m.setMedia_name(mediaName);
            m.setCategory(category);
            m.setFile(file);

            String contentType = file.getContentType();

            // 检查文件类型是否为图片或视频
            if(!(contentType != null && (contentType.startsWith("image/") || contentType.startsWith("video/"))))
                return result.error("文件类型只能是图片或者视频！");

            if (m.getMedia_name().isEmpty()){
                return result.error("媒体名不能为空！");
            }
            if (m.getMedia_type().isEmpty()){
                return result.error("媒体类型不能为空！");
            }
            if (m.getCategory().isEmpty()){
                return result.error("媒体类别不能为空！");
            }
            if(diseaseService.getCasebyId(m.getCase_id())==null){
                return result.error("该病例不存在！");
            }
            if(diseaseService.getMediabyUrl(m.getMedia_url())!=null){
                return result.error("该媒体已存在！");
            }
            if(diseaseService.getMediabyName(m.getMedia_name())!=null){
                return result.error("该媒体名已存在！");
            }

            if(!(m.getCategory().equals("Consultation")||m.getCategory().equals("Examination")
                    ||m.getCategory().equals("Result")||m.getCategory().equals("Treatment"))){
                return result.error("媒体类别只能是Consultation,Examination,Result,Treatment之一！");
            }
            diseaseService.uploadMedia(m);
        }
        return result.success(newToken(Authorization));
    }

    //添加病例（文字和多媒体一起）
    @PostMapping("/disease/addCaseWithMedia")
    public result addCaseWithMedia(
                                    @RequestParam("media_name") String mediaName,
                                    @RequestParam("category") String category,
                                    @RequestParam("file") MultipartFile file,
                                    @RequestParam("case_name") String caseName,
                                    @RequestParam("case_examination") String caseExamination,
                                    @RequestParam("case_result") String caseResult,
                                    @RequestParam("case_treatment") String caseTreatment,
                                    @RequestParam("case_medicine") String caseMedicine,
                                    @RequestParam("case_cost") String caseCost,
                                    @RequestParam("case_introduction") String caseIntroduction,
                                    @RequestParam("disease_name") String diseaseName,
                                    @RequestParam("department_name") String departmentName,

                                    @RequestHeader String Authorization
    ) throws Exception {

        if (identitySecure("user", Authorization)) {
            return result.error("无操作权限！");
        }
        if (caseName.isEmpty()) {
            return result.error("病例名不能为空！");
        }

        cases i = new cases();
        i.setCase_name(caseName);
        i.setCase_examination(caseExamination);
        i.setCase_result(caseResult);
        i.setCase_treatment(caseTreatment);
        i.setCase_medicine(caseMedicine);
        i.setCase_cost(caseCost);
        i.setCase_introduction(caseIntroduction);
        //根据disease和department的name返回id
        disease d = new disease();
        d.setDisease_name(diseaseName);
        i.setDisease_id(diseaseService.getDiseasebyName(d).getDisease_id());
        department k = new department();
        k.setDepartment_name(departmentName);
        i.setDepartment_id(diseaseService.getDepartmentbyName(k).getDepartment_id());
        //更新文字信息
        if (diseaseService.getDiseasebyId(i.getDisease_id()) == null) {
            return result.error("不能修改到不存在的疾病之下！");
        }
        if (diseaseService.getDepartmentbyId(i.getDepartment_id()) == null) {
            return result.error("不能修改到不存在的科室之下！");
        }
        diseaseService.changeCase(i);

        //添加多媒体
        cases m = new cases();
        m.setCase_id(diseaseService.getCasebyName(i.getCase_name()).getCase_id());

        m.setMedia_name(mediaName);
        m.setCategory(category);
        m.setFile(file);
        if (identitySecure("user", Authorization)) {
            return result.error("无操作权限！");
        }
        if (m.getCase_id().isEmpty()) {
            return result.error("病例id不能为空！");
        }

        String contentType = file.getContentType();

        // 检查文件类型是否为图片或视频
        if (!(contentType != null && (contentType.startsWith("image/") || contentType.startsWith("video/"))))
            return result.error("文件类型只能是图片或者视频！");

        if (m.getMedia_name().isEmpty()) {
            return result.error("媒体名不能为空！");
        }
        if (m.getMedia_type().isEmpty()) {
            return result.error("媒体类型不能为空！");
        }
        if (m.getCategory().isEmpty()) {
            return result.error("媒体类别不能为空！");
        }
        if (diseaseService.getCasebyId(m.getCase_id()) == null) {
            return result.error("该病例不存在！");
        }
        if (diseaseService.getMediabyUrl(m.getMedia_url()) != null) {
            return result.error("该媒体已存在！");
        }
        if (diseaseService.getMediabyName(m.getMedia_name()) != null) {
            return result.error("该媒体名已存在！");
        }

        if (!(m.getCategory().equals("Consultation") || m.getCategory().equals("Examination")
                || m.getCategory().equals("Result") || m.getCategory().equals("Treatment"))) {
            return result.error("媒体类别只能是Consultation,Examination,Result,Treatment之一！");
        }
        diseaseService.uploadMedia(m);
        return result.success(newToken(Authorization));
    }



        */


}