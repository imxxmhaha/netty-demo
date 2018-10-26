package cn.xxm.controller;


import cn.xxm.bo.UsersBo;
import cn.xxm.dto.IMoocJSONResult;
import cn.xxm.pojo.MyFriends;
import cn.xxm.pojo.Users;
import cn.xxm.service.MyFriendsService;
import cn.xxm.service.UsersService;
import cn.xxm.spring.aop.LoggerManage;
import cn.xxm.utils.FastDFSClient;
import cn.xxm.utils.FileUtils;
import cn.xxm.utils.IdWorker;
import cn.xxm.utils.StringUtil;
import cn.xxm.vo.FriendRequestVo;
import cn.xxm.vo.MyFriendsVo;
import cn.xxm.vo.UsersVo;
import enums.SearchFriendsStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping("/users")
@Slf4j
public class UsersController {


    @Autowired
    private UsersService usersService;

    @Autowired
    private FastDFSClient fastDFSClient;

    @Value("${fdfs.thumb-image.width}")
    private String imageWidth;


    @LoggerManage(description = "[用户注册或登录接口]")
    @PostMapping("/registOrLogin")
    public IMoocJSONResult registOrLogin(@RequestBody Users users) {
        // 1.判断用户名和密码不能为空
        if (StringUtil.isEmpty(users) || StringUtil.isEmpty(users.getUsername())
                || StringUtil.isEmpty(users.getPassword())) {
            return IMoocJSONResult.errorMsg("用户名或密码不能为空");
        }

        // 2.判断用户名是否存在
        boolean flag = usersService.queryUsernameIsExist(users.getUsername());

        Users user = null;
        if (flag) {
            // 2.1 登录
            user = usersService.queryUserForLogin(users);

            if (null == user) {
                return IMoocJSONResult.errorMsg("用户名或密码错误");
            }
        } else {
            // 2.2 注册
            user = usersService.register(users);
        }
        UsersVo userVo = getUsersVo(user);
        return IMoocJSONResult.ok(userVo);
    }

    private UsersVo getUsersVo(Users user) {
        UsersVo userVo = new UsersVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }


    @LoggerManage(description = "[用户上传头像]")
    @PostMapping("/uploadFaceBase64")
    public IMoocJSONResult uploadFaceBase64(@RequestBody UsersBo usersBo) throws Exception {
        // 获取前端传过来的base64字符串,然后转换为文件对象再上传
        String base64Data = usersBo.getFaceData();
        String userFacePath = "C:\\" + usersBo.getUserId() + "userface64.png";
        FileUtils.base64ToFile(userFacePath, base64Data);

        MultipartFile faceFile = FileUtils.fileToMultipart(userFacePath);
        String url = fastDFSClient.uploadBase64(faceFile);
        log.info("上传到fastDfs中的url:" + url);

        //获取缩略图的url
        //wKgAaVvIijWAKtDLAAWCrlZExuA363_150x150.jpg
        String thump = "_" + imageWidth + "x" + imageWidth + "."; //"_150x150."
        String arr[] = url.split("\\.");
        String thumpImgUrl = arr[0] + thump + arr[1]; //小图路径

        // 更新用户头像
        Users user = new Users();
        user.setId(usersBo.getUserId());
        user.setFaceImageBig(url);
        user.setFaceImage(thumpImgUrl);
        user = usersService.updateByUser(user);

        return IMoocJSONResult.ok(user);

    }


    @LoggerManage(description = "[修改用户信息]")
    @PostMapping("/updateUser")
    public IMoocJSONResult registOrLogin(@RequestBody UsersBo usersBo) {

        Users user = getUser(usersBo);

        user = usersService.updateByUser(user);

        return IMoocJSONResult.ok(user);
    }


    @LoggerManage(description = "[搜索好友接口]")
    @PostMapping("/search")
    public IMoocJSONResult searchUser(String myUserId,String friendUsername) {
        // 1.参数非空校验
        if(StringUtil.isEmpty(myUserId) || StringUtil.isEmpty(friendUsername)){
            return IMoocJSONResult.errorMsg("");
        }

        // 前置条件 - 1.搜索的用户如果不存在,返回[无此用户]
        // 前置条件 - 2.搜索的用户是你自己,返回[不能添加自己]
        // 前置条件 - 3.搜索的用户已经是你的好友,返回[该用户已经是你的好友]
        Integer status = usersService.preconditionSearchFriends(myUserId, friendUsername);
        if(SearchFriendsStatusEnum.SUCCESS.status != status){
            String msg = SearchFriendsStatusEnum.getMsgByKey(status);
            return IMoocJSONResult.errorMsg(msg);
        }
        Users user = usersService.queryUserInfoByUsername(friendUsername);

        UsersVo userVo = getUsersVo(user);
        return IMoocJSONResult.ok(userVo);
    }



    @LoggerManage(description = "[添加好友请求接口]")
    @PostMapping("/addFriendRequest")
    public IMoocJSONResult addFriendRequest(String myUserId,String friendUsername) {
        // 1.参数非空校验
        if(StringUtil.isEmpty(myUserId) || StringUtil.isEmpty(friendUsername)){
            return IMoocJSONResult.errorMsg("");
        }

        // 前置条件 - 1.搜索的用户如果不存在,返回[无此用户]
        // 前置条件 - 2.搜索的用户是你自己,返回[不能添加自己]
        // 前置条件 - 3.搜索的用户已经是你的好友,返回[该用户已经是你的好友]
        Integer status = usersService.preconditionSearchFriends(myUserId, friendUsername);
        if(SearchFriendsStatusEnum.SUCCESS.status != status){
            String msg = SearchFriendsStatusEnum.getMsgByKey(status);
            return IMoocJSONResult.errorMsg(msg);
        }

        usersService.sendAddFriendRequest(myUserId,friendUsername);

        return IMoocJSONResult.ok();
    }




    @LoggerManage(description = "[查询好友邀请请求]")
    @PostMapping("/queryFriendRequests")
    public IMoocJSONResult queryFriendRequests(String userId) {
        // 1.参数非空校验
        if(StringUtil.isEmpty(userId) ){
            return IMoocJSONResult.errorMsg("");
        }
        List<FriendRequestVo> list = usersService.queryFriendRequestList(userId);//该用户接受到的朋友申请
        return IMoocJSONResult.ok(list);

    }



    @LoggerManage(description = "[查询好友列表]")
    @PostMapping("/myFriendList")
    public IMoocJSONResult myFriendList(String userId) {
        // 1.参数非空校验
        if(StringUtil.isEmpty(userId) ){
            return IMoocJSONResult.errorMsg("");
        }
        // 1. 数据库查询好友列表

        List<MyFriendsVo> list = usersService.queryFriendList(userId);//该用户好友列表
        return IMoocJSONResult.ok(list);

    }





    private Users getUser(UsersBo usersBo) {
        Users user = new Users();
        user.setId(usersBo.getUserId());
        user.setNickname(usersBo.getNickname());
        return user;
    }
}

