package run.app.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.app.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/6/26 21:29
 * Description: ://TODO ${END}
 */
@RestController
@RequestMapping("/api")
public class HelloController {

    @Autowired
    UserService userService;

    @Autowired
    RedisTemplate redisTemplate;


    @GetMapping("/hello")
    public Map<String,String> doHello(){
        Map map = new HashMap();

        map.put("hello","leoo");

        return map;
    }





}
