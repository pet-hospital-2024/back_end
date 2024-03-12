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

    @PostMapping("/question/add")
    public result addQuestion(@RequestBody question q) {
        practiceService.addQuestion(q);
        result r = new result(1, "添加成功", new HashMap<>());
        return r;
    }

    @PostMapping("/question/delete")
    public result deleteQuestion(@RequestBody question q) {
        practiceService.deleteQuestion(q);
        result r = new result(1, "删除成功", null);
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
    public result alterQuestion(@RequestBody question q) {
        practiceService.alterQuestion(q);
        result r = new result(1, "修改成功", null);
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
    public result createNewPaper(@RequestBody paper p){
//        Claims claims = JWTUtils.jwtParser(Authorization);
//        String identity =(String) claims.get("identity");
//        if (identity.equals("user")){
//            result r = new result(0, "无操作权限", null);
//            return r;
//        }
        practiceService.createNewPaper(p);
        result r = new result(1, "创建成功", null);
        return r;
    }

    @PostMapping("/paper/addquestion")
    public result insertNewQuestion(@RequestBody paper p){
        //jwt part

        practiceService.insertNewQuestion(p);
        result r=new result(1,"插入成功！",null);
        return r;
    }

}