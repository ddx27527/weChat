package com.just.server.wechat.core.helper;

import com.just.server.wechat.core.exception.SystemException;
import com.just.server.wechat.core.model.AuthModel;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: wangkx
 * Date: 2018/08/13
 * Time: 13:15
 * Description: No Description
 */
public class SecurityHelper {

    private static RandomStringGenerator randomStringGenerator = new RandomStringGenerator.Builder().withinRange('0', 'z').filteredBy(CharacterPredicates.DIGITS, CharacterPredicates.LETTERS).build();

    public static String generateSalt() {
        return randomStringGenerator.generate(20);
    }

    public static String generatePasswordMd5(String password, String salt) {
        return DigestUtils.md5Hex(DigestUtils.md5Hex(password) + salt);
    }

    public static String generatePasswordMd5(String password) {
        return DigestUtils.md5Hex(DigestUtils.md5Hex(password) + SecurityHelper.generateSalt());
    }

    private SecurityHelper() {

    }

    public static AuthModel getAuthModel(String authorization) {
        String json = null;
        try {
            json = authorization.split("\\.")[1];
            json = new String(Base64.decodeBase64(json));
            return JsonHelper.getGson().fromJson(json, AuthModel.class);
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 为应用生成新的Key
     *
     * @return
     */
    public static String nextAppKey() {
        return randomStringGenerator.generate(12);
    }

    /**
     * 为设备生成新的Key
     *
     * @return
     */
    public static String nextDeviceKey() {
        return randomStringGenerator.generate(22);
    }

    /**
     * 为Kong的消费者生成Key，用于JWT认证
     *
     * @return
     */
    public static String nextConsumerKey() {
        return randomStringGenerator.generate(32);
    }

    /**
     * 为应用、设备、消费者生成Secret，参考Kong的实现，32位
     *
     * @return
     */
    public static String nextSecret() {
        return randomStringGenerator.generate(32);
    }

    /**
     * 生成新的UUID
     *
     * @return
     */
    public static String nextUUID() {
        return UUID.randomUUID().toString();
    }


    /***
     * 生成设备登录签名
     * @param clientId 设备唯一标识
     * @param applicationKey 应用key
     * @param deviceKey 设备key
     * @param deviceSecret 设备密钥
     * @param timeStamp 时间戳
     * @return
     */
    public static String createSign(String clientId,
                                    String applicationKey,
                                    String deviceKey,
                                    String deviceSecret,
                                    String timeStamp){
        return encryption(encryption(clientId + applicationKey
                + deviceKey + deviceSecret,"MD5")
                + applicationKey + timeStamp + deviceKey,"SHA");
    }

    /***
     * 加密
     * @param source 需要加密的字符串
     * @param hashType 加密类型 MD5或SHA
     * @return
     */
    public static String encryption(String source, String hashType) {
        StringBuilder sb = new StringBuilder();
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(hashType);
            messageDigest.update(source.getBytes());
            for (byte b : messageDigest.digest()) {
                // 10进制转16进制，X 表示以十六进制形式输出，02 表示不足两位前面补0输出
                sb.append(String.format("%02X", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new SystemException("加密失败");
        }
    }
}
