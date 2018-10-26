package cn.xxm.dao;

import cn.xxm.pojo.FriendsRequest;
import cn.xxm.vo.FriendRequestVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Xxm123
 * @since 2018-10-09
 */
public interface FriendsRequestDao extends BaseMapper<FriendsRequest> {

    List<FriendRequestVo> queryFriendRequestList(String acceptUserId);
}
