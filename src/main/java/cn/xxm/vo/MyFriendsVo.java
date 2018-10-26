package cn.xxm.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xxm
 * @create 2018-10-09 13:12
 */
@Data
public class MyFriendsVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String friendUserId;
    private String friendUsername;
    private String friendFaceImage;
    private String friendNickname;

}
