package com.example.pet_hospital.Controller;
import com.example.pet_hospital.Entity.paper;
import com.example.pet_hospital.Entity.question;
import com.example.pet_hospital.Entity.result;
import com.example.pet_hospital.Service.PracticeService;
import com.example.pet_hospital.Util.JWTUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class practiceController {

    @Autowired
    private PracticeService practiceService;

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


    @PostMapping("/question/add")
    public result addQuestion(@RequestBody question q, @RequestHeader String Authorization) {
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (practiceService.getQuestion(q)!=null){
            result r=new result(0,"该题目已存在！",null);
            return r;
        }
        practiceService.addQuestion(q);
        return result.success(newToken(Authorization));
    }

    @PostMapping("/question/delete")
    public result deleteQuestion(@RequestBody question q, @RequestHeader String Authorization) {
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (practiceService.getQuestion(q)==null){
            return result.error("该题目不存在！");
        }
        practiceService.deleteQuestion(q);
        return result.success(newToken(Authorization));
    }

    @GetMapping("/question/getAll")
    public result getAllQuestions() {
        question[] questions = practiceService.getAllQuestions();
        Map<String, question> questionMap = new HashMap<>();
        for (question q : questions) {
            questionMap.put(q.getId(), q);
        }
        return result.success(questionMap);
    }

    @PostMapping("/question/alter")
    public result alterQuestion(@RequestBody question q, @RequestHeader String Authorization) {
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限。");
        }
        if (practiceService.getQuestion(q)==null){
            return result.error("该题目不存在！");
        }
        practiceService.alterQuestion(q);
        return result.success(newToken(Authorization));
    }

    @PostMapping("/question/getquestion")
    public result getQuestion(@RequestBody question q) {
        //no identity secure needed.
        if (practiceService.getQuestion(q)==null){
            return result.error("该题目不存在！");
        }
        return result.success(practiceService.getQuestion(q));
    }

    @PostMapping("/paper/create")
    public result createNewPaper(@RequestBody paper p, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限。");
        }
        if(practiceService.getPaper(p)!=null){
            return result.error("该试卷已存在！");
        }
        practiceService.createNewPaper(p);
        return result.success(newToken(Authorization));
    }

    @PostMapping("/paper/addquestion")
    public result insertNewQuestion(@RequestBody paper p,@RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限。");
        }

        //先查询后修改，防止bad request。
        question q=new question();
        q.setQuestion_id(p.getQuestion_id());
        if (practiceService.getQuestion(q)==null){
            return result.error("该题目不存在！");
        }
        if (practiceService.getPaper(p)==null){
            return result.error("该试卷不存在！");
        }
        practiceService.insertNewQuestion(p);
        return result.success(newToken(Authorization));
    }

    @PostMapping("/paper/getpaper")
    public result getPaper(@RequestBody paper p){
        //no identity secure needed.
        if (practiceService.getPaper(p)==null){
            return result.error("该试卷不存在！");
        }
        return result.success(practiceService.getPaper(p));
    }


}