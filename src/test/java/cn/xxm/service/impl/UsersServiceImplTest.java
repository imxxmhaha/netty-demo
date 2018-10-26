package cn.xxm.service.impl;

import cn.xxm.WeChatApplication;
import cn.xxm.service.UsersService;
import cn.xxm.vo.MyFriendsVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author xxm
 * @create 2018-10-26 23:36
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeChatApplication.class)
public class UsersServiceImplTest {

    @Autowired
    private UsersService usersService;

    @Test
    public void queryFriendList(){
        List<MyFriendsVo> myFriendsVoList = usersService.queryFriendList("3235472526086144");
        System.out.println(myFriendsVoList);
    }
}