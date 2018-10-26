package cn.xxm.dao;

import cn.xxm.pojo.MyFriends;
import cn.xxm.vo.MyFriendsVo;
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
public interface MyFriendsDao extends BaseMapper<MyFriends> {


    List<MyFriendsVo> queryMyFriends(String userId);
}
