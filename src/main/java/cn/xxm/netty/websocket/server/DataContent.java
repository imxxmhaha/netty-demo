package cn.xxm.netty.websocket.server;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xxm
 * @create 2018-10-28 8:52
 */

@Data
public class DataContent implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer action;   //  动作类型
    private ChatMsg chatMsg;  //  用户的聊天内容entity
    private String  extand;   //  扩展字段

}
