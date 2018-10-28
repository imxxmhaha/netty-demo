package cn.xxm.netty.websocket.server;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author xxm
 * @create 2018-10-28 8:52
 */
@Slf4j
public class UserChannelRel implements Serializable {

    private static final long serialVersionUID = 1L;
    private static HashMap<String, Channel> manager = new HashMap<>();

    public static void put(String senderId, Channel channel) {
        manager.put(senderId, channel);
    }

    public static Channel get(String senderId) {
        return manager.get(senderId);
    }

    public static void output() {
        for (HashMap.Entry<String, Channel> entry : manager.entrySet()) {
            log.info("UserId:{} --- ChannelId:{}",entry.getKey(),entry.getValue().id().asLongText());
        }
    }
}
