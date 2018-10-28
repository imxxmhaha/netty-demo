package cn.xxm.service.impl;

import cn.xxm.WeChatApplication;
import cn.xxm.dao.ChatMsgDao;
import cn.xxm.pojo.ChatMsg;
import cn.xxm.service.ChatMsgService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author xxm
 * @create 2018-10-28 22:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeChatApplication.class)
public class ChatMsgServiceImplTest {

    @Autowired
    private ChatMsgService chatMsgService;
    @Test
    public void getUnReadMsgList() {
        List<ChatMsg> unReadMsgList = chatMsgService.getUnReadMsgList("1001");
        System.out.println(unReadMsgList);
    }
}