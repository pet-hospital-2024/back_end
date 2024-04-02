package com.example.pet_hospital.Controller;

import cn.hutool.json.JSONUtil;
import com.example.pet_hospital.Entity.question;
import com.example.pet_hospital.Vo.result;
import com.example.pet_hospital.Service.PracticeService;
import com.example.pet_hospital.Util.JWTUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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


    @PostMapping("/question/add")//已解决非法SQL问题，可正常使用。
    public result addQuestion(@RequestBody question q, @RequestHeader String Authorization) {
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (practiceService.getQuestionByBody(q)!=null){
            return result.error("该题目已存在！");
        }
        String type=q.getType();
        if (!type.equals("choice") && !type.equals("judge")){
            return result.error("题目类型只能是choice或者judge！");
        }
        if(type.equals("judge")){
            if (!(q.getA().equals("对") && q.getB().equals("错") )){
                return result.error("判断题选项只能是A是对，B是错！");
            }
            if(!(q.getRight_choice().equals("a") || q.getRight_choice().equals("b"))){
                return result.error("判断题答案只能是a或者b！");
            }
        }else {
            if(!(q.getRight_choice().equals("a") || q.getRight_choice().equals("b") ||
                    q.getRight_choice().equals("c") || q.getRight_choice().equals("d"))){
                return result.error("选择题答案只能是a,b,c,d中的一个！");
            }
        }
        practiceService.addQuestion(q);
        if (JWTUtils.refreshTokenNeeded(Authorization)){
            return result.success(newToken(Authorization));
        }
        else {
            return result.success(Authorization);
        }
    }

    @PostMapping("/question/delete")
    public result deleteQuestion(@RequestBody question q, @RequestHeader String Authorization) {
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (stringRedisTemplate.opsForValue().get(QUESTION_KEY+q.getQuestion_id())!=null){
            stringRedisTemplate.delete(QUESTION_KEY+q.getQuestion_id());
        }
        if (practiceService.getQuestionByID(q)==null){
            return result.error("该题目不存在！");
        }
        practiceService.deleteQuestion(q);
        if (JWTUtils.refreshTokenNeeded(Authorization)){
            return result.success(newToken(Authorization));
        }
        else {
            return result.success(Authorization);
        }
    }

    @GetMapping("/question/getAll")
    public result getAllQuestions() {
        question[] questions = practiceService.getAllQuestions();
        return result.success(questions);
    }

    @PostMapping("/question/alter")
    public result alterQuestion(@RequestBody question q, @RequestHeader String Authorization) {
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限。");
        }
        String type=q.getType();
        if (!type.equals("choice") && !type.equals("judge")){
            return result.error("题目类型只能是choice或者judge！");
        }
        if(type.equals("judge")){
            if (!(q.getA().equals("对") && q.getB().equals("错") )){
                return result.error("判断题选项只能是A是对，B是错！");
            }
            if(!(q.getRight_choice().equals("a") || q.getRight_choice().equals("b"))){
                return result.error("判断题答案只能是a或者b！");
            }
        }else {
            if(!(q.getRight_choice().equals("a") || q.getRight_choice().equals("b") ||
                    q.getRight_choice().equals("c") || q.getRight_choice().equals("d"))){
                return result.error("选择题答案只能是a,b,c,d中的一个！");
            }
        }
        if (stringRedisTemplate.opsForValue().get(QUESTION_KEY+q.getQuestion_id())!=null){
            stringRedisTemplate.opsForValue().set(QUESTION_KEY+q.getQuestion_id(),
                    JSONUtil.toJsonStr(q),30, TimeUnit.MINUTES);
            practiceService.alterQuestion(q);
            if (JWTUtils.refreshTokenNeeded(Authorization)){
                return result.success(newToken(Authorization));
            }
            else {
                return result.success(Authorization);
            }
        }
        if (practiceService.getQuestionByID(q)==null){
            return result.error("该题目不存在！");
        }
        stringRedisTemplate.opsForValue().set(QUESTION_KEY+q.getQuestion_id(),
                JSONUtil.toJsonStr(q),30, TimeUnit.MINUTES);
        practiceService.alterQuestion(q);
        if (JWTUtils.refreshTokenNeeded(Authorization)){
            return result.success(newToken(Authorization));
        }
        else {
            return result.success(Authorization);
        }
    }


    @PostMapping("/question/getquestion")
    public result getQuestion(@RequestBody question q) {
        //no identity secure needed.
        if (stringRedisTemplate.opsForValue().get(QUESTION_KEY+q.getQuestion_id())!=null){//缓存命中
            return result.success(JSONUtil.toBean(stringRedisTemplate.opsForValue().
                    get(QUESTION_KEY+q.getQuestion_id()), question.class));
        }
        else {//缓存未命中，进行数据库查询
            if (practiceService.getQuestionByID(q)==null){
                return result.error("该题目不存在！");
            }else{
                stringRedisTemplate.opsForValue().set(QUESTION_KEY+q.getQuestion_id(),
                        JSONUtil.toJsonStr(practiceService.getQuestionByID(q)),
                        30, TimeUnit.MINUTES);
                return result.success(JSONUtil.toBean(stringRedisTemplate.opsForValue().
                        get(QUESTION_KEY+q.getQuestion_id()), question.class));
            }
        }
    }

    //模糊查询


    //根据疾病返回试题

}