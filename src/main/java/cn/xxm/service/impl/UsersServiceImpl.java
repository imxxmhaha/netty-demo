package cn.xxm.service.impl;

import cn.xxm.dao.FriendsRequestDao;
import cn.xxm.dao.MyFriendsDao;
import cn.xxm.pojo.FriendsRequest;
import cn.xxm.pojo.MyFriends;
import cn.xxm.pojo.Users;
import cn.xxm.dao.UsersDao;
import cn.xxm.service.UsersService;
import cn.xxm.utils.*;
import cn.xxm.vo.FriendRequestVo;
import cn.xxm.vo.MyFriendsVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import enums.SearchFriendsStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Xxm123
 * @since 2018-10-09
 */
@Service
@Slf4j
@Transactional
public class UsersServiceImpl extends ServiceImpl<UsersDao, Users> implements UsersService {

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private MyFriendsDao myFriendsDao;

    @Autowired
    private FriendsRequestDao friendsRequestDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 二维码工具类
     */
    @Autowired
    private QRCodeUtils qrCodeUtils;

    /**
     * fastDfs操作组件
     */
    @Autowired
    private FastDFSClient fastDFSClient;

    /**
     * 根据用户名查询用户是否存在
     *
     * @param username
     * @return
     */
    @Override
    public boolean queryUsernameIsExist(String username) {

        Wrapper<Users> wrapper = new EntityWrapper<Users>();
        wrapper.eq("username", username);
        Integer count = usersDao.selectCount(wrapper);

        return (null != count && count > 0);
    }

    @Override
    public Users queryUserForLogin(Users users) {
        Wrapper<Users> wrapper = new EntityWrapper<Users>();
        wrapper.eq("username", users.getUsername());
        wrapper.eq("password", MD5Utils.gasjEncrypt(users.getPassword()));

        List<Users> usersList = usersDao.selectList(wrapper);
        if (null != usersList && usersList.size() > 1) {
            log.error("根据用户名和密码查询得到的用户有{}个,用户名:{} 密码:{}", usersList.size(), users.getUsername(), users.getPassword());
        }

        return (null == usersList || usersList.size()==0) ? null : usersList.get(0);
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public Users register(Users user) {
        Long userId = getIdWorkerId();
        user.setId(userId);   //twitter 雪花算法分布式id生成器
        user.setNickname(user.getUsername());
        user.setPassword(MD5Utils.gasjEncrypt(user.getPassword()));

        //  为每个用户生成一个唯一的二维码
        String qrCodePath = "D://user"+userId +"qrcode.png";
        // weixin_qrcode:[username]
        qrCodeUtils.createQRCode(qrCodePath,"weixin_qrcode:["+user.getUsername()+"]");
        MultipartFile qrCodeFile = FileUtils.fileToMultipart(qrCodePath);
        String qrCodeUrl = "";
        try {
            qrCodeUrl = fastDFSClient.uploadQRCode(qrCodeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        user.setQrcode(qrCodeUrl);
        usersDao.insert(user);
        return user;
    }

    private Long getIdWorkerId() {
        Long userId = idWorker.nextId();
        userId = Long.parseLong(userId.toString().substring(3));
        return userId;
    }

    @Override
    public Users updateByUser(Users user) {
        usersDao.updateById(user);
        user = usersDao.selectById(user.getId());
        return user;
    }

    /**
     * 搜索朋友的前置条件
     * @param myUserId
     * @param friendUsername
     * @return
     */
    @Override
    public Integer preconditionSearchFriends(String myUserId, String friendUsername) {
        // 1.搜索的用户如果不存在,返回[无此用户]
        Users user = queryUserInfoByUsername(friendUsername);
        if(null == user){ // 无此用户
            return SearchFriendsStatusEnum.USER_NOT_EXIST.getStatus();
        }

        if(myUserId.equals(user.getId().toString())){  //查询的朋友 是自己
            return SearchFriendsStatusEnum.NOT_YOURSELF.getStatus();
        }

        // 将朋友id  与自己的id  去My_friends 表中去查,看看是否有记录
        EntityWrapper<MyFriends> ew = new EntityWrapper<MyFriends>();
        ew.eq("my_user_id",myUserId).eq("my_friends_user_id",user.getId());
        Integer count = myFriendsDao.selectCount(ew);
        if(null != count && count>0){
            return SearchFriendsStatusEnum.ALREADY_FRIENDS.getStatus();
        }
        return SearchFriendsStatusEnum.SUCCESS.getStatus();
    }




    public Users queryUserInfoByUsername(String username){
        Users user = new Users();
        user.setUsername(username);
        user = usersDao.selectOne(user);
        return user;
    }


    /**
     * 添加好友请求
     * @param myUserId
     * @param friendUsername
     */
    @Override
    public void sendAddFriendRequest(String myUserId, String friendUsername) {
        Users friendUser = queryUserInfoByUsername(friendUsername);

        // 先查询  是否有好友请求记录,如果有就更新  没有就添加
        FriendsRequest friendsRequest = new FriendsRequest();
        friendsRequest.setSendUserId(Long.parseLong(myUserId));
        friendsRequest.setAcceptUserId(friendUser.getId());

        //查询是否有  好友请求的记录
        FriendsRequest friendsRequest1 = friendsRequestDao.selectOne(friendsRequest);

        if(null == friendsRequest1){//没有,做插入操作
            friendsRequest.setId(getIdWorkerId());
            friendsRequest.setRequestDateTime(new Date());
            friendsRequestDao.insert(friendsRequest);
        }else{ // 有,做更新操作
            friendsRequest1.setRequestDateTime(new Date());
            friendsRequestDao.updateById(friendsRequest1);
        }
    }

    /**
     * 该用户接受到的朋友申请
     * @param acceptUserId
     * @return
     */
    @Override
    public List<FriendRequestVo> queryFriendRequestList(String acceptUserId) {
        return friendsRequestDao.queryFriendRequestList(acceptUserId);
    }

    /**
     * 查询好友列表
     * @param userId
     * @return
     */
    @Override
    public List<MyFriendsVo> queryFriendList(String userId) {
        List<MyFriendsVo> myFriendsVoList =  myFriendsDao.queryMyFriends(userId);
        return myFriendsVoList;
    }

}
