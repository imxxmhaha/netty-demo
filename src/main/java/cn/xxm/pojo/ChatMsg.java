package cn.xxm.pojo;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Xxm123
 * @since 2018-10-09
 */
@TableName("chat_msg")
@Data
public class ChatMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 发送者id
     */
    @TableField("send_user_id")
    private String sendUserId;
    /**
     * 接受者id
     */
    @TableField("accept_user_id")
    private String acceptUserId;
    /**
     * 聊天记录
     */
    private String msg;
    /**
     * 是否已读
     */
    @TableField("sign_flag")
    private Integer signFlag;
    /**
     * 发送时间
     */
    @TableField("create_time")
    private Date createTime;





}
