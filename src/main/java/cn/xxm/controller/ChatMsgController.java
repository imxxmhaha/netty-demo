package cn.xxm.controller;


import cn.xxm.dto.IMoocJSONResult;
import cn.xxm.pojo.ChatMsg;
import cn.xxm.service.ChatMsgService;
import cn.xxm.spring.aop.LoggerManage;
import cn.xxm.utils.StringUtil;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xxm123
 * @since 2018-10-09
 */
@RestController
@RequestMapping("/chatMsg")
public class ChatMsgController {

    @Autowired
    private ChatMsgService chatMsgService;

    @LoggerManage(description = "[用户手机端获取未签收消息列表]")
    @PostMapping("/getUnReadMsgList")
    public IMoocJSONResult getUnReadMsgList(String acceptUserId){

        // 1.参数非空校验
        if(StringUtil.isEmpty(acceptUserId) ){
            return IMoocJSONResult.errorMsg("");
        }
        // 2.查询列表
        List<ChatMsg> unReadMsgList =  chatMsgService.getUnReadMsgList(acceptUserId);
        return IMoocJSONResult.ok(unReadMsgList);
    }


}

