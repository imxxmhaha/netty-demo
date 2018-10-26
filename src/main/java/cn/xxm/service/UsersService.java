package cn.xxm.service;

import cn.xxm.pojo.Users;
import cn.xxm.vo.FriendRequestVo;
import cn.xxm.vo.MyFriendsVo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Xxm123
 * @since 2018-10-09
 */
public interface UsersService extends IService<Users> {

    /**
     * 根据用户名查询用户是否存在
     * @param username
     * @return
     */
    public boolean queryUsernameIsExist(String username);

    Users queryUserForLogin(Users users);


    /**
     * 用户注册
     * @param users
     * @return
     */
    Users register(Users users);

    /**
     * 修改用户记录 并返回修改后的用户
     * @param user
     * @return
     */
    Users updateByUser(Users user);

    /**
     * 搜索朋友的前置条件
     * @param myUserId
     * @param friendUsername
     * @return
     */
    public Integer preconditionSearchFriends(String myUserId,String friendUsername);

    /**
     * 通过用户名查询user
     * @param username
     * @return
     */
    public Users queryUserInfoByUsername(String username);

    /**
     * 添加好友请求
     * @param myUserId
     * @param friendUsername
     */
    void sendAddFriendRequest(String myUserId, String friendUsername);

    /**
     * 该用户接受到的朋友申请
     * @param acceptUserId
     * @return
     */
    List<FriendRequestVo> queryFriendRequestList(String acceptUserId);

    /**
     * 查询好友列表
     * @param userId
     * @return
     */
    List<MyFriendsVo> queryFriendList(String userId);
}
