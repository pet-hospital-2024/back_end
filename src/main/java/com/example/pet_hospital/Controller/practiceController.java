package com.example.pet_hospital.Controller;
import com.example.pet_hospital.Entity.question;
import com.example.pet_hospital.Entity.result;
import com.example.pet_hospital.Service.PracticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController

public class practiceController {

    @Autowired
    private PracticeService practiceService;

    @PostMapping("/question/add")
    public result addQuestion(@RequestBody question q) {
        question q1 = practiceService.addQuestion(q);
        result r = new result(1, "添加成功", (Map) q1);
        return r;
    }

    @PostMapping("/question/delete")
    public result deleteQuestion(@RequestBody question q) {
        practiceService.deleteQuestion(q);
        result r = new result(1, "删除成功", null);
        return r;
    }

    @PostMapping("/question/getAll")
    public result getAllQuestions() {
        question[] questions = practiceService.getAllQuestions();
        Map<Integer, question> questionMap = new HashMap<>();
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
}
