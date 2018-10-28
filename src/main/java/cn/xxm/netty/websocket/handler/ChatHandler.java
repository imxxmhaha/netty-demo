package cn.xxm.netty.websocket.handler;

import cn.xxm.netty.websocket.server.ChatMsg;
import cn.xxm.netty.websocket.server.DataContent;
import cn.xxm.netty.websocket.server.UserChannelRel;
import cn.xxm.service.ChatMsgService;
import cn.xxm.utils.JSONUtils;
import cn.xxm.utils.SpringUtil;
import cn.xxm.utils.StringUtil;
import enums.MsgActionEnum;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


/**
 * 处理消息的handler
 * TextWebSocketFrame: 在netty中,用于为websocket专门处理文本的对象,frame是消息的载体
 *
 * @author xxm
 * @create 2018-10-07 22:41
 */
@Slf4j
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // 用于记录和管理所有客户端的channel
    private static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Autowired
    private SpringUtil springUtil;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 获取客户端传输过来的消息
        String content = msg.text();

        Channel currentChannel = ctx.channel();

        // 1. 获取客户端发来的消息
        DataContent dataContent = JSONUtils.json2pojo(content, DataContent.class);
        Integer action = dataContent.getAction();
        // 2. 判断消息类型,根据不同的类型来处理不同的业务
        if (action == MsgActionEnum.CONNECT.type) {
            //  2.1 当webSocket第一次open的时候,初始化channel,把用户的channel 与userId关联起来
            String sendUserId = dataContent.getChatMsg().getSenderId();
            UserChannelRel.put(sendUserId, currentChannel);


            //测试
            for (Channel c : users){
                log.info(c.id().asLongText());
            }

            UserChannelRel.output();


        } else if (action == MsgActionEnum.CHAT.type) {
            //  2.2 聊天类型的消息,把聊天记录保存到数据库,同时标记消息的签收状态[未签收]
            ChatMsg chatMsg = dataContent.getChatMsg();
            String msgText = chatMsg.getMsg();
            String receiverId = chatMsg.getReceiverId();
            String sendUserId = chatMsg.getSenderId();

            // 保存消息到数据库,并且标记为未签收
            ChatMsgService chatMsgService = (ChatMsgService) springUtil.getBean("chatMsgServiceImpl");
            String msgId = chatMsgService.saveMsg(chatMsg);
            chatMsg.setMsg(msgId);


            DataContent dataContentMsg = new DataContent();
            dataContentMsg.setChatMsg(chatMsg);

            // 发送消息
            Channel receiveChannel = UserChannelRel.get(receiverId);
            if (null == receiveChannel) {
                // TODO channel为空代表用户离线,推送消息 (JPush,个推,小米推送)
            } else {
                // 当receiveChannel不为空的时候,从ChannelGroup去查找对应的channel是否存在
                Channel findChannel = users.find(receiveChannel.id());
                if (null != findChannel) {
                    // 用户在线
                    receiveChannel.writeAndFlush(new TextWebSocketFrame(JSONUtils.obj2json(dataContentMsg)));

                } else {
                    // 用户离线 TODO

                }
            }


        } else if (action == MsgActionEnum.SIGNED.type) {
            //  2.3 签收消息,针对具体的消息进行签收,修改数据库中对应消息的签收状态[已签收]
            ChatMsgService chatMsgService = (ChatMsgService) springUtil.getBean("chatMsgServiceImpl");
            // 扩展字段在signed类型的消息中,代表需要去签收的消息id,逗号间隔
            String msgIdsStr = dataContent.getExtand();
            String[] msgIds = msgIdsStr.split(",");
            List<String> msgIdList = new ArrayList<>();

            for (String msgId:msgIds){
                if(!StringUtil.isEmpty(msgId)){
                    msgIdList.add(msgId);
                }
            }

            System.out.println(msgIdList.toString());

            if(msgIdList.size()>0){
                // 批量签收
                chatMsgService.updateMsgSigned(msgIdList);
            }



        } else if (action == MsgActionEnum.KEEPALIVE.type) {
            //  2.4 心跳类型的消息

        }


    }


    /**
     * 当客户端连接客户端之后(打开连接)
     * 获取客户端的channel ,并且放到ChannelGroup中去进行管理
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        Channel channel = ctx.channel();
        users.add(ctx.channel());
        System.out.println("服务器建立连接");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 当触发handlerRemoved,ChannelGroup会自动一处对应客户端的channel
        String channelId = ctx.channel().id().asLongText();
        System.out.println("客户端被移除,channelId为:" + channelId);
        users.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 发生异常之后关闭连接(关闭连接),随后从ChannelGroup中移除
        ctx.channel().close();
        users.remove(ctx.channel());
    }
}
