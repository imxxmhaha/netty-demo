package cn.xxm.pojo;

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
@TableName("my_friends")
@Data
public class MyFriends implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    @TableField("my_user_id")
    private Long myUserId;
    @TableField("my_friends_user_id")
    private Long myFriendsUserId;



}
