package cn.xxm.service;

import cn.xxm.pojo.ChatMsg;
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
public interface ChatMsgService extends IService<ChatMsg> {

    String saveMsg(cn.xxm.netty.websocket.server.ChatMsg chatMsg);

    /**
     * 批量签收消息
     * @param msgIdList
     */
    void updateMsgSigned(List<String> msgIdList);

    /**
     * 获取用户未签收消息列表
     * @param acceptUserId
     * @return
     */
    List<ChatMsg> getUnReadMsgList(String acceptUserId);
}
