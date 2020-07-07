package com.lengqi.controller;

import com.lengqi.entity.ResultData;
import com.lengqi.entity.User;
import com.lengqi.service.IUserService;
import com.lengqi.util.JwtUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sso")
public class SSOController {
    @Resource
    private IUserService userService;
    @RequestMapping("/register")
    public ResultData register(User user){
        try {
            int row = userService.insert(user);
            if (row > 0){
                return new ResultData().setCode(ResultData.Code.CODE_SUCC).setData(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultData().setCode(ResultData.Code.CODE_ERROR).setMsg("账号已经存在，注册失败！");
    }
    @RequestMapping("/login")
    public ResultData login(String username,String password){
        System.out.println("进行登录：" + username + " " + password);
        User user = userService.queryByUserName(username);
        if (user != null && user.getPassword().equals(password)){
            //登录成功
            //使用jwt生成登录凭证token
            String jwtToken = JwtUtil.createJwtToken(user);
            Map<String, String> map = new HashMap<>();
            //因为要显示游客名称，所以要单独的nickname
            map.put("nickname",user.getNickname());
            map.put("jwtToken",jwtToken);
            return new ResultData().setCode(ResultData.Code.CODE_SUCC).setData(map);
        }
        return new ResultData().setCode(ResultData.Code.CODE_ERROR).setMsg("密码错误，登录失败！");
    }
}
