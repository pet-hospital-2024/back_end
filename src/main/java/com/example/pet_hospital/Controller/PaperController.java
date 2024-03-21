package com.example.pet_hospital.Controller;

import cn.hutool.json.JSONUtil;
import com.example.pet_hospital.Entity.paper;
import com.example.pet_hospital.Entity.paperItem;
import com.example.pet_hospital.Entity.question;
import com.example.pet_hospital.Entity.result;
import com.example.pet_hospital.Service.PaperService;
import com.example.pet_hospital.Service.PracticeService;
import com.example.pet_hospital.Util.JWTUtils;
import io.jsonwebtoken.Claims;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
public class PaperController {

    @Autowired
    private PaperService paperService;
    @Autowired
    private PracticeService practiceService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    String PAPER_KEY="paper:";

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
    @PostMapping("/paper/create")
    public result createNewPaper(@RequestBody paper p, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限。");
        }
        if(paperService.getPaperByID(p)!=null){
            return result.error("该试卷已存在！");
        }
        paperService.createNewPaper(p);
        return result.success(newToken(Authorization));
    }

    @PostMapping("/paper/addquestion")
    public result insertNewQuestion(@RequestBody paper p, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限。");
        }

        //先查询后修改，防止bad request。
        question q=new question();
        q.setQuestion_id(p.getQuestion_id());
        if (practiceService.getQuestionByID(q)==null){
            return result.error("该题目不存在！");
        }
        if (paperService.getPaperByID(p)==null){
            return result.error("该试卷不存在！");
        }
        paperService.insertNewQuestion(p);
        return result.success(newToken(Authorization));
    }


    @PostMapping("/paper/getpaper")
    public result getPaper(@RequestBody paper p){
        //no identity secure needed.
        //缓存命中
        if (stringRedisTemplate.opsForValue().get(PAPER_KEY+p.getPaper_id())!=null){
            return result.success(JSONUtil.toBean(
                    stringRedisTemplate.opsForValue().
                            get(PAPER_KEY+p.getPaper_id()),paper.class));
        }
        else {//缓存未命中
            if (paperService.getPaperByID(p)!=null) {
                stringRedisTemplate.opsForValue().
                        set(PAPER_KEY+p.getPaper_id(),
                                JSONUtil.toJsonStr(paperService.getPaperByID(p)),
                                30, TimeUnit.MINUTES);

                return result.success(JSONUtil.toBean(
                        stringRedisTemplate.opsForValue().
                                get(PAPER_KEY+p.getPaper_id()),paper.class));
            }
        }
        return result.error("该试卷不存在！");
    }

    @PostMapping("/paper/delete")
    public result deletePaper(@RequestBody paper p ,@RequestHeader String Authorization){
        if (identitySecure("user",Authorization)){
            return result.error("无操作权限！");
        }
        if (stringRedisTemplate.opsForValue().get(PAPER_KEY+p.getPaper_id())!=null){
            stringRedisTemplate.delete(PAPER_KEY+p.getPaper_id());
        }
        if (paperService.getPaperByID(p)==null){
            return result.error("该试卷不存在！");
        }
        paperService.deletePaper(p);
        return result.success(newToken(Authorization));
    }

    @PostMapping("/paper/deletequestion")
    public result deleteQuestionFromPaper(@RequestBody paper p, @RequestHeader String Authorization){
        if (identitySecure("user",Authorization))
        return result.error("无操作权限！");

        question q=new question();
        q.setQuestion_id(p.getQuestion_id());
        if (practiceService.getQuestionByID(q)==null){
            return result.error("该题目不存在！");
        }
        if (paperService.getPaperByID(p)==null){
            return result.error("该试卷不存在！");
        }
        paperService.deleteQuestionFromPaper(p);
        return result.success(newToken(Authorization));
    }

    @PostMapping("/paper/getquestionbypaper")//写了，但不知道用途在哪里。。。。。
    public result getQuestionsFromPaper(@RequestBody paper p){
        //no identity secure needed.
        return result.success(paperService.getQuestionsFromPaper(p));
    }

}
