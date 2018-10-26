package cn.xxm.controller;


import cn.xxm.dto.IMoocJSONResult;
import cn.xxm.pojo.FriendsRequest;
import cn.xxm.pojo.MyFriends;
import cn.xxm.service.FriendsRequestService;
import cn.xxm.service.MyFriendsService;
import cn.xxm.service.UsersService;
import cn.xxm.spring.aop.LoggerManage;
import cn.xxm.utils.IdWorker;
import cn.xxm.utils.StringUtil;
import cn.xxm.vo.MyFriendsVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import enums.OperatorFriendRequestTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Xxm123
 * @since 2018-10-09
 */
@RestController
@RequestMapping("/friendsRequest")
public class FriendsRequestController {

    @Autowired
    private FriendsRequestService friendsRequestService;

    @Autowired
    private MyFriendsService myFriendsService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private IdWorker idWorker;

    @PostMapping("/operFriendRequest")
    @LoggerManage(description = "[处理好友请求  忽略&通过]")
    public IMoocJSONResult operFriendRequest(String acceptUserId, String sendUserId, Integer operType) {
        // 1. 判断不能为空
        if (StringUtil.isEmpty(acceptUserId) || StringUtil.isEmpty(sendUserId) || StringUtil.isEmpty(operType)) {
            return IMoocJSONResult.errorMsg("");
        }
        // 2. 如果operType没有对应的枚举值,则直接跑出空错误信息
        if (StringUtil.isEmpty(OperatorFriendRequestTypeEnum.getMsgByType(operType))) {
            return IMoocJSONResult.errorMsg("");
        }

        // 然后删除好友请求的数据库表记录
        Wrapper<FriendsRequest> ew = new EntityWrapper<FriendsRequest>();
        ew.eq("send_user_id", sendUserId).eq("accept_user_id", acceptUserId);
        friendsRequestService.delete(ew);
        if (operType == OperatorFriendRequestTypeEnum.PASS.type) {
            // 3. 判断如果是通过好友请求,则互相增加好友记录到数据库对应的表
            operFriendRequest(acceptUserId, sendUserId);
        }
        // 4. 数据库查询好友列表
        List<MyFriendsVo> myFriendsVoList = usersService.queryFriendList(acceptUserId);
        return IMoocJSONResult.ok(myFriendsVoList);
    }

    // 互相添加为好友
    private void operFriendRequest(String acceptUserId, String sendUserId) {
        MyFriends friend1 = new MyFriends();
        friend1.setId(getIdWorkerId());
        friend1.setMyUserId(Long.parseLong(acceptUserId));
        friend1.setMyFriendsUserId(Long.parseLong(sendUserId));
        myFriendsService.insert(friend1);

        MyFriends friend2 = new MyFriends();
        friend2.setId(getIdWorkerId());
        friend2.setMyUserId(Long.parseLong(sendUserId));
        friend2.setMyFriendsUserId(Long.parseLong(acceptUserId));
        myFriendsService.insert(friend2);
    }


    private Long getIdWorkerId() {
        Long userId = idWorker.nextId();
        userId = Long.parseLong(userId.toString().substring(3));
        return userId;
    }
}

