package cn.xxm.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xxm
 * @create 2018-10-09 13:12
 */
@Data
public class UsersVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 小头像
     */
    private String faceImage;
    /**
     * 大头像
     */
    private String faceImageBig;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 二维码
     */
    private String qrcode;
}
