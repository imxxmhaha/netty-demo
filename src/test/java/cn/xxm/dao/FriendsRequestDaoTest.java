package cn.xxm.dao;

import cn.xxm.WeChatApplication;
import cn.xxm.vo.FriendRequestVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author xxm
 * @create 2018-10-19 23:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeChatApplication.class)
public class FriendsRequestDaoTest {

    @Autowired
    private FriendsRequestDao friendsRequestDao;

    @Test
    public void queryFriendRequestList() {
        List<FriendRequestVo> list = friendsRequestDao.queryFriendRequestList("3235472526086144");
        System.out.println(list);
    }
}