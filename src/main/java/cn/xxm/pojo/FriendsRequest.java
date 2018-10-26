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
@TableName("friends_request")
@Data
public class FriendsRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    @TableField("send_user_id")
    private Long sendUserId;
    @TableField("accept_user_id")
    private Long acceptUserId;
    @TableField("request_date_time")
    private Date requestDateTime;


}
