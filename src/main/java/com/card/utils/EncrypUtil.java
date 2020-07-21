package com.card.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

public class EncrypUtil {

    private static final String PRIVATE_KEY="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC35fnIwTIzm1yf\n" +
            "3x6RbNuGy3wZuWFzDUt9bg9yaSXok7Crn35O/g2S9bVFFV1YnwRXVMElAkhGcuRf\n" +
            "V/bbnv4gcuVpPNXc4+mkCmH+xYsnP6zA+VjXe4jQW+ik37/f2aKo89MAf3B9tstL\n" +
            "ykTdHlYPbpiH4id2jNlrYfrHnAOLmF3gmhCSdplBvYjacTstFokqyx7/uEJoXAEG\n" +
            "MHzcllyJ+vZ619oxkW96K9eNSveJqG9oLBK8mVAm8EfqaYpdQIteZe4Zdov0RM0t\n" +
            "Wnl50tbPePIZzmwpg3CKDBOqvznxF0A6OMZ3Cv6qa0dgi06wBmfJnnEGZs07FWSs\n" +
            "pKYJ9P2JAgMBAAECggEABq5fRdDnDwLs+Y30oVnwG9SWvKSEitaH0JXID+MMEogT\n" +
            "GhkgUKRgP9+XZ1wgWRDl6npUzhBoKc8Qxvn+KPp8xxcFiK5CItVQ7rnF16i7poBk\n" +
            "uVoVxTZiwRclu5+5a9uPUk4Rc2ot3xNeDXHa0KHChKsC9aTcxCzX1VPo9YmQg2jE\n" +
            "3zbnpA7bLz5MO1kyzSQ8PJrahKnGWe4czs7sADwxWFepKLFlwXqMhfgbZ+SWitY+\n" +
            "niYzt216Rt19z/5XWAIc2Jw/fGCLQoFg8ip4sZJmE0dcWqshy8jj7wgflcdPlRGI\n" +
            "GsqFQVAdc+2n0ZDzA7bJixhC0AaVgPP5lwDNkbcKBQKBgQDwUG0u4wZ7uANlyPfI\n" +
            "qiVLGdhlW31jcWyVYrt+XbhYpl5tQLCkkhp1WvCg4WDumW77zrUgmSWnHnjt3ocg\n" +
            "MbNA5rLKBEy30OIurv0ZoIY+1CmiGDCZmqqAqpNhsrTUBVHMg9oeg0e/W07eN2mg\n" +
            "2lfW+GkWj26TBtbqDeTwBZSlVwKBgQDD5tvUJF0qiXXP4zIKEzo01jvS9c30LWWc\n" +
            "WkQzl67cKFCTFK1X7zXSLVsbJaAEDewY62mgyYvGq8+NELTKd/KyJQIn/l3Mf8gs\n" +
            "yeeoT6qaKq2lKEVJDTbwGKox0XPxHaW3Mupsnsil++FpIwcqzn/gEhAN/Rv5UCWx\n" +
            "BsiuU9/IHwKBgFEcVD3HEqEfcsYgFsH766p1hrxvLNsrpWKmeHImX9pMDQMDGpvx\n" +
            "VEzX7O/gIj9mOjdzhV8DUdoeRuLCgQk0vTPiqeXVna9NKYpoNbU4oRcDtSmo0H6H\n" +
            "3ZSf/i6ziqUlRn6o4i36UXUc9Hm8akKIb613GsEqmD+3x0IofmUk/Q7xAoGAXUiF\n" +
            "7xutvX6dOfO+tSt4sQY+kHL5skcXtn8JMOQOoMkVrSPQcyn2H/H3CxM8ghG7Fhar\n" +
            "QqQWSfEwMMT+wdaiG5Ah2bd3JNTIFgPCqtPcsfKWoyls7J89RNEJ4hURt4PdkEHP\n" +
            "fQ+TDe7F5FGIrSqNm74eJPscofg3KnfHgbNwwRMCgYAi6lHHYkOqQOJspN0hS21o\n" +
            "QvZlfhRbOle8jcmE2bMLb8Kr1EP2TjIR4brbd+OJqBiLp6lVuzPZsFGoI5Fc3c/q\n" +
            "ATZMgnFCePFOJb3nGZgcy0/38FazJ+Uw04WwWzYJTxCvpJN5AaLZHBAta0zVqrtk\n" +
            "NwU6YzlHp3SXX4gwxUBDFA==";

    private static final String PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAt+X5yMEyM5tcn98ekWzb\n" +
            "hst8Gblhcw1LfW4Pcmkl6JOwq59+Tv4NkvW1RRVdWJ8EV1TBJQJIRnLkX1f2257+\n" +
            "IHLlaTzV3OPppAph/sWLJz+swPlY13uI0FvopN+/39miqPPTAH9wfbbLS8pE3R5W\n" +
            "D26Yh+IndozZa2H6x5wDi5hd4JoQknaZQb2I2nE7LRaJKsse/7hCaFwBBjB83JZc\n" +
            "ifr2etfaMZFveivXjUr3iahvaCwSvJlQJvBH6mmKXUCLXmXuGXaL9ETNLVp5edLW\n" +
            "z3jyGc5sKYNwigwTqr858RdAOjjGdwr+qmtHYItOsAZnyZ5xBmbNOxVkrKSmCfT9\n" +
            "iQIDAQAB";

    /**
     * RSA解密
     */
    public static String rsaDecrypt(String str) {
        try {
            //64位解码加密后的字符串
            byte[] inputByte = Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8));
            //base64编码的私钥
            byte[] decoded = Base64.decodeBase64(PRIVATE_KEY);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            return new String(cipher.doFinal(inputByte));
        } catch (Exception e) {
            return "RAS解密异常"+e.getMessage();
        }
    }


    /**
     * RSA加密
     */
    public static String rsaEncrypt(String str) {
        try {
            byte[] inputByte = str.getBytes(StandardCharsets.UTF_8);
            //base64编码的公钥
            byte[] decoded = Base64.decodeBase64(PUBLIC_KEY);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return Base64.encodeBase64String(cipher.doFinal(inputByte));
        } catch (Exception e) {
            return "RAS加密异常"+e.getMessage();
        }
    }

    /**
     * 生成动态加密key
     * @param a
     * @return
     */
    public static String getKey(int a){
        String s ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int length =s.length();
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < a; i++) {
            stringBuffer.append(s.charAt(random.nextInt(length)));
        }
        return stringBuffer.toString();
    }

    /**
     * AES解密
     */
    public static String aesDecrypt(String content,String key){
        try {
            //实例化
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            //使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8),"AES"));
            //执行操作
            byte[] result = cipher.doFinal(Base64.decodeBase64(content.getBytes()));
            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "AES解密异常"+e.getMessage();
        }
    }

    /**
     * AES加密
     */
    public static String aesEncrypt(String a,String key){
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8),"AES");
            Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
            aes.init(Cipher.ENCRYPT_MODE,secretKeySpec);
            return new String(Base64.encodeBase64(aes.doFinal(a.getBytes(StandardCharsets.UTF_8))));
        } catch (Exception e) {
            return "AES加密异常"+e.getMessage();
        }
    }

    /**
     * url加密
     */
    public static String URLEncoder(String str){
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "URLEncoder异常"+e.getMessage();
        }
    }

    /**
     * url解密
     */
    public static String URLDecoder(String str){
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "UURLDecoder异常"+e.getMessage();
        }
    }

    public static void main(String[] args) {
        String a ="9I3rp12GXJEmyHjn2H+Lww==";
        String b ="ok";
        String key="gfhujihftd5678uhfDTGUJDRXdtgh7tc";
        System.out.println(aesEncrypt(b,key));
        System.out.println(aesDecrypt(a,key));
        System.out.println(rsaDecrypt("F3RM40XydgOI6vrvWrpJPSp0cOZGI5p7CpL6JdpBSSGT+/2RgseciCQDd0iZxIvOqb9HFvkL3HoX7LvVrDVOPoWc3ZLngLIenB6W+3XU1bTL2C3w/HpcJr1fx+Fd/McYliqbtbObpjKaY3VByD0FoA/cX/J3D9dpX/60dbG3Cikp5p4fNzTxEN0vrmCF2rw4fAZUEzEYS/0mfEoCveTC3DcPaogVtrhmBJEjAimFwTBiaYGVMx6WHDvaUIWDJbMp75VSqk/zvYI67IwF/t43leHVTuSbuPDGRon4mRdgPwHbrJgUOCB/Np8l3tu6NztfhMb6K9cg6FODX3llZZ5RUg=="));
        System.out.println(rsaEncrypt("abc"));
    }








}
