package cn.xxm.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xxm
 * @create 2018-10-09 13:12
 */
@Data
public class FriendRequestVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String sendUserId;
    private String sendUsername;
    private String sendFaceImage;
    private String sendNickname;

}
