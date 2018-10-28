package cn.xxm.service.impl;

import cn.xxm.pojo.ChatMsg;
import cn.xxm.dao.ChatMsgDao;
import cn.xxm.service.ChatMsgService;
import cn.xxm.utils.IdWorker;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import enums.MsgSignFlagEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Xxm123
 * @since 2018-10-09
 */
@Service
public class ChatMsgServiceImpl extends ServiceImpl<ChatMsgDao, ChatMsg> implements ChatMsgService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private ChatMsgDao chatMsgDao;

    /**
     * 保存聊天记录
     * @param chatMsg
     * @return
     */
    @Override
    public String saveMsg(cn.xxm.netty.websocket.server.ChatMsg chatMsg) {
        ChatMsg msgDb = new ChatMsg();
        Long msgId = getIdWorkerId();
        msgDb.setId(msgId);
        msgDb.setAcceptUserId(chatMsg.getReceiverId());
        msgDb.setSendUserId(chatMsg.getSenderId());
        msgDb.setCreateTime(new Date());
        msgDb.setSignFlag(MsgSignFlagEnum.unsign.type);
        msgDb.setMsg(chatMsg.getMsg());
        chatMsgDao.insert(msgDb);

        return msgId.toString();
    }

    /**
     * 批量签收消息
     * @param msgIdList
     */
    @Override
    public void updateMsgSigned(List<String> msgIdList) {
        chatMsgDao.batchUpdateMsgSign(msgIdList);
    }

    /**
     * 获取用户未签收消息列表
     * @param acceptUserId
     * @return
     */
    @Override
    public List<ChatMsg> getUnReadMsgList(String acceptUserId) {
        Wrapper ew = new EntityWrapper<ChatMsg>();
        ew.eq("accept_user_id",acceptUserId).eq("sign_flag",0);
        List<ChatMsg> list = chatMsgDao.selectList(ew);
        return list;
    }


    private Long getIdWorkerId() {
        Long userId = idWorker.nextId();
        userId = Long.parseLong(userId.toString().substring(3));
        return userId;
    }
}
