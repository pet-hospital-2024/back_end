package com.example.pet_hospital.Controller;
import cn.hutool.json.JSONUtil;
import com.example.pet_hospital.Entity.paper;
import com.example.pet_hospital.Entity.question;
import com.example.pet_hospital.Entity.result;
import com.example.pet_hospital.Service.PracticeService;
import com.example.pet_hospital.Util.JWTUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class practiceController {

    @Autowired
    private PracticeService practiceService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    String QUESTION_KEY="question:";

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


    @PostMapping("/question/add")//已解决非法SQL问题，可正常使用。
    public result addQuestion(@RequestBody question q, @RequestHeader String Authorization) {
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (practiceService.getQuestionByBody(q)!=null){
            return result.error("该题目已存在！");
        }
        practiceService.addQuestion(q);
        return result.success(newToken(Authorization));
    }

    @PostMapping("/question/delete")
    public result deleteQuestion(@RequestBody question q, @RequestHeader String Authorization) {
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (stringRedisTemplate.opsForValue().get(QUESTION_KEY+q.getId())!=null){
            stringRedisTemplate.delete(QUESTION_KEY+q.getId());
        }
        if (practiceService.getQuestionByID(q)==null){
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
        if (stringRedisTemplate.opsForValue().get(QUESTION_KEY+q.getId())!=null){
            stringRedisTemplate.opsForValue().set(QUESTION_KEY+q.getId(), JSONUtil.toJsonStr(q),30, TimeUnit.MINUTES);
            practiceService.alterQuestion(q);
            return result.success(newToken(Authorization));
        }
        if (practiceService.getQuestionByID(q)==null){
            return result.error("该题目不存在！");
        }
        stringRedisTemplate.opsForValue().set(QUESTION_KEY+q.getId(), JSONUtil.toJsonStr(q),30, TimeUnit.MINUTES);
        practiceService.alterQuestion(q);
        return result.success(newToken(Authorization));
    }

    @PostMapping("/question/getquestion")
    public result getQuestion(@RequestBody question q) {
        //no identity secure needed.
        if (stringRedisTemplate.opsForValue().get(QUESTION_KEY+q.getId())!=null){//缓存命中
            return result.success(JSONUtil.toBean(stringRedisTemplate.opsForValue().get(QUESTION_KEY+q.getId()), question.class));
        }
        else {//缓存未命中，进行数据库查询
            if (practiceService.getQuestionByID(q)==null){
                return result.error("该题目不存在！");
            }else{
                stringRedisTemplate.opsForValue().set(QUESTION_KEY+q.getId(), JSONUtil.toJsonStr(practiceService.getQuestionByID(q)),30, TimeUnit.MINUTES);
                return result.success(JSONUtil.toBean(stringRedisTemplate.opsForValue().get(QUESTION_KEY+q.getId()), question.class));
            }

        }
    }




}