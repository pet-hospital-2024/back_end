package com.example.pet_hospital.Controller;

import cn.hutool.json.JSONUtil;
import com.example.pet_hospital.Entity.Exam;
import com.example.pet_hospital.Entity.paper;
import com.example.pet_hospital.Entity.result;
import com.example.pet_hospital.Service.ExamService;
import com.example.pet_hospital.Service.PaperService;
import com.example.pet_hospital.Util.JWTUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
public class ExamController {

    public Boolean identitySecure(String target, String Authorization){
        Claims claims = JWTUtils.jwtParser(Authorization);
        String identity=(String) claims.get("identity");
        return target.equals(identity);
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
        return JWTUtils.jwtGenerater(newclaim);
    }

    @Autowired
    private ExamService examService;

    @Autowired
    private PaperService paperService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    String EXAM_KEY="exam:";

    @PostMapping("/exam/addexam")
    public result AddNewExam(@RequestBody Exam e , @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        paper p=new paper();
        p.setPaper_id(e.getPaper_id());
        if (paperService.getPaperByID(p)==null){
            return result.error("该试卷不存在！");
        }
        examService.createExam(e);
        if (JWTUtils.refreshTokenNeeded(Authorization)){
            return result.success(newToken(Authorization));
        }
        else {
            return result.success(Authorization);
        }
    }

    @PostMapping("/exam/deleteexam")
    public result DeleteExam(@RequestBody Exam e,@RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (examService.getExamById(e)==null){
            return result.error("该考试不存在！");
        }
        examService.deleteExam(e);
        stringRedisTemplate.delete(EXAM_KEY+e.getExam_id());
        if (JWTUtils.refreshTokenNeeded(Authorization)){
            return result.success(newToken(Authorization));
        }
        else {
            return result.success(Authorization);
        }
    }


    @PostMapping("/exam/alterexam")
    public result AlterExam(@RequestBody Exam e,@RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (examService.getExamById(e)==null){
            return result.error("该考试不存在！");
        }
        paper p=new paper();
        p.setPaper_id(e.getPaper_id());
        if (paperService.getPaperByID(p)==null){
            return result.error("该试卷不存在！");
        }
        examService.updateExam(e);
        stringRedisTemplate.opsForValue().set(EXAM_KEY+e.getExam_id(),
                e.getExam_id(),30,TimeUnit.MINUTES);

        if (JWTUtils.refreshTokenNeeded(Authorization)){
            return result.success(newToken(Authorization));
        }
        else {
            return result.success(Authorization);
        }
    }


    @PostMapping("/exam/getexam")
    public result GetExam(@RequestBody Exam e){
        if (stringRedisTemplate.opsForValue().get(EXAM_KEY+e.getExam_id())!=null){//缓存命中
            stringRedisTemplate.expire(EXAM_KEY+e.getExam_id(),30, TimeUnit.MINUTES);
            return result.success(JSONUtil.toBean(stringRedisTemplate.opsForValue().
                    get(EXAM_KEY+e.getExam_id()),Exam.class));
        }

        if (examService.getExamById(e)==null){
            return result.error("未找到该考试！");
        }
        stringRedisTemplate.opsForValue().set(EXAM_KEY+e.getExam_id(),
                JSONUtil.toJsonStr(examService.getExamById(e)), 30, TimeUnit.MINUTES);

        return result.success(examService.getExamById(e));
    }

    @GetMapping("/exam/getExamList")
    public result GetExamList(@RequestHeader String Authorization){
        return result.success(examService.getExamList());
    }

}
