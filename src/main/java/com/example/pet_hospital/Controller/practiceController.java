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
            result r=new result(0,"无操作权限！",null);
            return r;
        }

        practiceService.addQuestion(q);
        result r = new result(1, "添加成功", new HashMap<>());
        r.getData().put("Token",newToken(Authorization));
        return r;
    }

    @PostMapping("/question/delete")
    public result deleteQuestion(@RequestBody question q, @RequestHeader String Authorization) {
        if (identitySecure("user",Authorization)){
            result r=new result(0,"无操作权限！",null);
            return r;
        }
        practiceService.deleteQuestion(q);
        result r = new result(1, "删除成功", new HashMap<>());
        r.getData().put("Token",newToken(Authorization));
        return r;
    }

    @GetMapping("/question/getAll")
    public result getAllQuestions() {
        question[] questions = practiceService.getAllQuestions();
        Map<String, question> questionMap = new HashMap<>();
        for (question q : questions) {
            questionMap.put(q.getId(), q);
        }
        result r = new result(1, "获取成功", questionMap);
        return r;
    }

    @PostMapping("/question/alter")
    public result alterQuestion(@RequestBody question q, @RequestHeader String Authorization) {
        if (identitySecure("user",Authorization)){
            result r=new result(0,"无操作权限！",null);
            return r;
        }
        practiceService.alterQuestion(q);
        result r = new result(1, "修改成功", new HashMap<>());
        r.getData().put("Token",newToken(Authorization));
        return r;
    }

    @PostMapping("/question/getquestion")
    public result getQuestion(@RequestBody question q) {
        question que=practiceService.getQuestion(q);
        Map<String,question> m=new HashMap<>();
        m.put("question",que);
        result r = new result(1, "操作成功", m);
        return r;
    }

    @PostMapping("/paper/create")
    public result createNewPaper(@RequestBody paper p, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            result r=new result(0,"无操作权限！",null);
            return r;
        }
        practiceService.createNewPaper(p);
        result r = new result(1, "创建成功", new HashMap<>());
        r.getData().put("Token",newToken(Authorization));
        return r;
    }

    @PostMapping("/paper/addquestion")
    public result insertNewQuestion(@RequestBody paper p,@RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            result r=new result(0,"无操作权限！",null);
            return r;
        }
        practiceService.insertNewQuestion(p);
        result r=new result(1,"插入成功！",new HashMap<>());
        r.getData().put("Token",newToken(Authorization));
        return r;
    }

}