package cn.yog.chess.constants;

public class ConstantsRedis {

    //图像验证码 redis key
    public static final String IMG_CODE_PREFIX = "chess:img:code:";


    // ------------------------缓存时间 begin---------------------

    // 默认的数据缓存时间(秒)
    public static final int DATA_NORMAL_TIME = 2 * 60;
    // 用户数据的缓存时间(秒)
    public static final int USER_TOKEN_PREFIX_TIME = 24 * 60 * 60;
    // 图形验证码缓存时间(秒)
    public static final int IMG_CODE_PREFIX_TIME = 2 * 60;

    // ------------------------缓存时间 end---------------------


    // ------------------------分布式锁 end---------------------
}
