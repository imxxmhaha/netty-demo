package cn.xxm.dao;

import cn.xxm.pojo.ChatMsg;
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
public interface ChatMsgDao extends BaseMapper<ChatMsg> {

    /**
     * 批量签收消息
     * @param msgIdList
     */
    void batchUpdateMsgSign(List<String> msgIdList);
}
