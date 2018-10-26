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
@TableName("users")
@Data
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 用户名
     */
    private String username;
    private String password;
    /**
     * 小头像
     */
    @TableField("face_image")
    private String faceImage;
    /**
     * 大头像
     */
    @TableField("face_image_big")
    private String faceImageBig;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 二维码
     */
    private String qrcode;
    /**
     * 设配id   用作消息推送
     */
    private String cid;



}
