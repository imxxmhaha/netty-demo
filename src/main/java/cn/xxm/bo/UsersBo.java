package cn.xxm.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xxm
 * @create 2018-10-09 13:12
 */
@Data
public class UsersBo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;
    private String faceData;
    private String nickname;
}
