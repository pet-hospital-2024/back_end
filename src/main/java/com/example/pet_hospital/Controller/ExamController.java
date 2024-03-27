package com.example.pet_hospital.Controller;

import com.example.pet_hospital.Entity.Exam;
import com.example.pet_hospital.Entity.paper;
import com.example.pet_hospital.Entity.result;
import com.example.pet_hospital.Service.ExamService;
import com.example.pet_hospital.Service.PaperService;
import com.example.pet_hospital.Util.JWTUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class ExamController {

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

    @Autowired
    private ExamService examService;

    @Autowired
    private PaperService paperService;

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
        return result.success(newToken(Authorization));
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
        return result.success(newToken(Authorization));
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
        return result.success(newToken(Authorization));
    }


    @PostMapping("/exam/getexam")
    public result GetExam(@RequestBody Exam e){
        if (examService.getExamById(e)==null){
            return result.error("未找到该考试！");
        }

        return result.success(examService.getExamById(e));
    }

}
