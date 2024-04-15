package com.example.pet_hospital.Controller;

import cn.hutool.json.JSONUtil;
import com.example.pet_hospital.Entity.paper;
import com.example.pet_hospital.Entity.question;
import com.example.pet_hospital.Entity.result;
import com.example.pet_hospital.Service.PaperService;
import com.example.pet_hospital.Service.PracticeService;
import com.example.pet_hospital.Util.JWTUtils;
import com.github.pagehelper.PageInfo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController//
public class PaperController {

    @Autowired
    private PaperService paperService;
    @Autowired
    private PracticeService practiceService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    String PAPER_KEY = "paper:";

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
        return JWTUtils.jwtGenerater(newclaim);
    }

    @PostMapping("/paper/create")
    public result createNewPaper(@RequestBody paper p, @RequestHeader String Authorization) {
        if (identitySecure("user", Authorization)) {
            return result.error("无操作权限。");
        }
        if (paperService.getPaperByName(p) != null) {
            return result.error("该试卷已存在！");
        }
        paperService.createNewPaper(p);
        if (JWTUtils.refreshTokenNeeded(Authorization)) {
            return result.success(newToken(Authorization));
        } else {
            return result.success(Authorization);
        }

    }

    @PostMapping("/paper/change")
    public result changePaper(@RequestBody paper p, @RequestHeader String Authorization) {
        if (identitySecure("user", Authorization)) {
            return result.error("无操作权限。");
        }
        if (paperService.getPaperByID(p) == null) {
            return result.error("该试卷不存在！");
        }
        paperService.changePaper(p);

        // 在试卷信息更新后，删除Redis中的缓存
        String paperCacheKey = "paper:" + p.getPaper_id();
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(paperCacheKey))) {
            // 如果Redis中存在这个试卷的缓存，则删除
            stringRedisTemplate.delete(paperCacheKey);
        }
        // 还需要考虑删除与这个试卷相关的问题和选项的缓存
        List<String> questionIds = paperService.getQuestionsIDFromPaper(p.getPaper_id());
        for (String questionId : questionIds) {
            String questionCacheKey = "question:" + questionId;
            if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(questionCacheKey))) {
                stringRedisTemplate.delete(questionCacheKey);
            }
        }


        if (JWTUtils.refreshTokenNeeded(Authorization)) {
            return result.success(newToken(Authorization));
        } else {
            return result.success(Authorization);
        }
    }

    @PostMapping("/paper/addquestion")
    public result insertNewQuestion(@RequestBody paper p, @RequestHeader String Authorization) {
        if (identitySecure("user", Authorization)) {
            return result.error("无操作权限。");
        }

        if (paperService.ifPaperContainsQueston(p) != null &&
                !paperService.ifPaperContainsQueston(p).isEmpty()) {
            return result.error("该题目已经在试卷中！");
        }

        question q = new question();
        q.setQuestion_id(p.getQuestion_id());
        if (practiceService.getQuestionByID(q) == null) {
            return result.error("该题目不存在！");
        }
        if (paperService.getPaperByID(p) == null) {
            return result.error("该试卷不存在！");
        }

        if (p.getQuestion_order() == null) {
            return result.error("order不能为空！");
        }
        if (p.getQuestion_order() < 1) {
            return result.error("order不能小于1！");
        }

        if (paperService.ifOrderExist(p) != null && !paperService.ifOrderExist(p).isEmpty()) {
            return result.error("order已存在！");
        }

        paperService.insertNewQuestion(p);

        // 由于试卷内容已更新，需要清除与该试卷相关的缓存
        String paperCacheKey = "paper:" + p.getPaper_id();
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(paperCacheKey))) {
            stringRedisTemplate.delete(paperCacheKey);
        }

        if (JWTUtils.refreshTokenNeeded(Authorization)) {
            return result.success(newToken(Authorization));
        } else {
            return result.success(Authorization);
        }
    }


    @PostMapping("/paper/getpaper")
    public result getPaper(@RequestBody paper p) {
        //no identity secure needed.
        //缓存命中
        if (stringRedisTemplate.opsForValue().get(PAPER_KEY + p.getPaper_id()) != null) {
            return result.success(JSONUtil.toBean(
                    stringRedisTemplate.opsForValue().
                            get(PAPER_KEY + p.getPaper_id()), paper.class));
        } else {//缓存未命中
            if (paperService.getPaperByID(p) != null) {
                stringRedisTemplate.opsForValue().
                        set(PAPER_KEY + p.getPaper_id(),
                                JSONUtil.toJsonStr(paperService.getPaperByID(p)),
                                30, TimeUnit.MINUTES);

                return result.success(JSONUtil.toBean(
                        stringRedisTemplate.opsForValue().
                                get(PAPER_KEY + p.getPaper_id()), paper.class));
            }
        }
        return result.error("该试卷不存在！");
    }

    @PostMapping("/paper/delete")
    public result deletePaper(@RequestBody paper p, @RequestHeader String Authorization) {
        if (identitySecure("user", Authorization)) {
            return result.error("无操作权限！");
        }

        // 删除试卷之前，先删除相关的缓存
        String paperCacheKey = "paper:" + p.getPaper_id();
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(paperCacheKey))) {
            stringRedisTemplate.delete(paperCacheKey);
        }

        // 删除与试卷相关的所有问题的缓存
        List<String> questionIds = paperService.getQuestionsIDFromPaper(p.getPaper_id());
        for (String questionId : questionIds) {
            String questionCacheKey = "question:" + questionId;
            if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(questionCacheKey))) {
                stringRedisTemplate.delete(questionCacheKey);
            }
        }

        // 检查试卷是否存在，然后执行删除操作
        if (paperService.getPaperByID(p) == null) {
            return result.error("该试卷不存在！");
        }
        paperService.deletePaper(p);

        if (JWTUtils.refreshTokenNeeded(Authorization)) {
            return result.success(newToken(Authorization));
        } else {
            return result.success(Authorization);
        }
    }


    @PostMapping("/paper/deletequestion")
    public result deleteQuestionFromPaper(@RequestBody paper p, @RequestHeader String Authorization) {
        if (identitySecure("user", Authorization)) {
            return result.error("无操作权限！");
        }

        question q = new question();
        q.setQuestion_id(p.getQuestion_id());
        if (practiceService.getQuestionByID(q) == null) {
            return result.error("该题目不存在！");
        }
        if (paperService.getPaperByID(p) == null) {
            return result.error("该试卷不存在！");
        }

        // 执行删除题目操作前，删除相关的缓存
        // 删除试卷缓存
        String paperCacheKey = "paper:" + p.getPaper_id();
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(paperCacheKey))) {
            stringRedisTemplate.delete(paperCacheKey);
        }

        // 删除题目缓存
        String questionCacheKey = "question:" + p.getQuestion_id();
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(questionCacheKey))) {
            stringRedisTemplate.delete(questionCacheKey);
        }

        // 从试卷中删除问题
        paperService.deleteQuestionFromPaper(p);

        if (JWTUtils.refreshTokenNeeded(Authorization)) {
            return result.success(newToken(Authorization));
        } else {
            return result.success(Authorization);
        }
    }


//    @GetMapping("/paper/getpaperbyid")
//    public result getQuestionsFromPaper(@RequestParam(name = "paper_id") String paper_id){
//        paper p=new paper();
//        p.setPaper_id(paper_id);
//        //no identity secure needed.
//        if (paperService.getPaperByID(p)==null){
//            return result.error("该试卷不存在！");
//        }
//        ArrayList<String> questions_id =paperService.getQuestionsFromPaper(p);
//        ArrayList<question> questions=new ArrayList<>();
//
//        for (String qid:questions_id){
//            question q=new question();
//            q.setQuestion_id(qid);
//            questions.add(practiceService.getQuestionByID(q));
//        }
//        return result.success(questions);
//    }

    @GetMapping("/paper/getPaperById")
    public result GetPaperById(@RequestParam(name = "paper_id") String paper_id, @RequestHeader String Authorization) {
        paper p = new paper();
        p.setPaper_id(paper_id);
        //no identity secure needed.
        if (paperService.getPaperByID(p) == null) {
            return result.error("该试卷不存在！");
        }
        paper r = paperService.getQuestionsFromPaper(paper_id);
        List<question> questions = r.getQuestions();
        int question_number = questions.size();
        int value = 0;
        if(question_number != 0){

            for (question question : questions) {
                // 对于每个问题，根据question_id获取选项内容
                List<Map<String, String>> optionResult = paperService.selectOptionsForQuestion(question.getQuestion_id());
                if (optionResult != null) {
                    if (question.getType().equals("choice")) {
                        question.setOptions(optionResult);
                    }
                    if (question.getType().equals("judge")) {
                        optionResult = new ArrayList<>(optionResult.subList(0, 2));
                        question.setOptions(optionResult);
                    }
                    value += question.getValue();
                }
            }

        }else{
            //返回一个空的question列表
            r.setQuestions(new ArrayList<>());

        }
        //System.out.println(questions);

        r.setQuestion_number(question_number);
        r.setValue(value);
        //更新paper的value和question_number
        paperService.updatePaperValueAndQuestionNumber(paper_id, value, question_number);

        return result.success(r);
    }


    @GetMapping("/paper/getPaperList")
    public result GetPaperList(@RequestHeader String Authorization,
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "10") int size) {
        if (identitySecure("user", Authorization)) {
            return result.error("无操作权限。");
        }
        if (page <= 0 || size <= 0) {
            return result.error("参数错误！");
        }
        PageInfo<paper> pageResult = paperService.getPaperList(page, size);
        return result.success(pageResult);
    }


}
