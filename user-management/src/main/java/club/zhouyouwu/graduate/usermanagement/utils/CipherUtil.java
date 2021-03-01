package club.zhouyouwu.graduate.usermanagement.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;
import java.util.Base64;

/**
 * @author zhou
 */
public class CipherUtil {
    /**
     * 算法
     */
    private static final String ALGORITHM_RSA = "RSA";
    private static final String ALGORITHM_AES = "AES";
    /**
     * 密钥长度选择
     */
    private static final int KEY_SIZE = 1024;
    private static final int KEY_SIZE_AES = 128;

    public static final String DEFAULT_KEY = "b5659a1fc88c45beb2c1282817643489";

    public static class RSA {

        /**
         * RSA最大加密明文大小
         */
        private static final int MAX_ENCRYPT_BLOCK = 117;

        /**
         * RSA最大解密密文大小
         */
        private static final int MAX_DECRYPT_BLOCK = 128;


        public KeyPair getKeyPair() throws Exception {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_RSA);
            keyPairGenerator.initialize(KEY_SIZE);
            return keyPairGenerator.genKeyPair();
        }

        /**
         * RSA加密
         * @param publicKey BASE64存储的公钥
         * @param plaintext 直接可以看的明文
         * @return 返回BASE64编码的密文
         * @throws Exception
         */
        public String encrypt(String publicKey, String plaintext) throws Exception {
            Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);

            //将BASE64编码转为bytes，然后再生成密钥
            //**公钥**要使用X509EncodedKeySpec
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getUrlDecoder().decode(publicKey));
            KeyFactory factory = KeyFactory.getInstance(ALGORITHM_RSA);
            RSAPublicKey key = (RSAPublicKey) factory.generatePublic(keySpec);

            cipher.init(Cipher.ENCRYPT_MODE, key);

            //加密时超过117则报错，所以要分组加密
            byte[] plaintextByte = plaintext.getBytes();
            int offset = 0;
            int length = plaintextByte.length;
            StringBuilder ciphertext = new StringBuilder();
            while (length - offset > 0){
                byte[] cache;
                if(length - offset >= MAX_ENCRYPT_BLOCK){
                    cache = cipher.doFinal(plaintextByte, offset, MAX_ENCRYPT_BLOCK);
                }else {
                    cache = cipher.doFinal(plaintextByte, offset, length - offset);
                }
                ciphertext.append(Base64.getUrlEncoder().encodeToString(cache));
                offset += MAX_ENCRYPT_BLOCK;
            }
            return ciphertext.toString();
        }

        /**
         * RSA解密
         * @param privateKey BASE64编码存储的私钥
         * @param ciphertext BASE64编码存储的密文
         * @return 返回直接看的明文
         * @throws Exception
         */
        public String decrypt(String privateKey, String ciphertext) throws Exception {
            Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);

            //将BASE64编码转为bytes，然后再生成密钥
            //**私钥**要使用PKCS8EncodedKeySpec
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getUrlDecoder().decode(privateKey));
            KeyFactory factory = KeyFactory.getInstance(ALGORITHM_RSA);
            RSAPrivateKey key = (RSAPrivateKey) factory.generatePrivate(keySpec);

            cipher.init(Cipher.DECRYPT_MODE, key);

            //解密时超过128会报错，所以要分组解密
            byte[] ciphertextBytes = Base64.getUrlDecoder().decode(ciphertext);
            int offset = 0;
            int length = ciphertextBytes.length;
            StringBuilder plaintext = new StringBuilder();
            while (length - offset > 0){
                if(length - offset >= MAX_DECRYPT_BLOCK){
                    plaintext.append(new String(cipher.doFinal(ciphertextBytes, offset, MAX_DECRYPT_BLOCK)));
                }else {
                    plaintext.append(new String(cipher.doFinal(ciphertextBytes, offset, length - offset)));
                }
                offset += MAX_DECRYPT_BLOCK;
            }
            return plaintext.toString();
        }
    }

    /**
     * AES 加密解密，加密后的byte[]->String会导致信息不对，所以要用BASE64的数据
     */
    public static class AES {
        public String encrypt(String password, String plaintext) throws Exception {
            Cipher cipher = Cipher.getInstance(ALGORITHM_AES);

            KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM_AES);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            kgen.init(KEY_SIZE_AES, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            SecretKeySpec key = new SecretKeySpec(secretKey.getEncoded(), ALGORITHM_AES);

            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] ciphertext = cipher.doFinal(plaintext.getBytes());
            return Base64.getUrlEncoder().encodeToString(ciphertext);
        }

        public String decrypt(String password, String ciphertext) throws Exception{
            Cipher cipher = Cipher.getInstance(ALGORITHM_AES);

            KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM_AES);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            kgen.init(KEY_SIZE_AES, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            SecretKeySpec key = new SecretKeySpec(secretKey.getEncoded(), ALGORITHM_AES);

            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plaintext = cipher.doFinal(Base64.getUrlDecoder().decode(ciphertext));
            return new String(plaintext);
        }
    }

    public static void main(String[] args) throws Exception {
        String pw = "12345678";
        String text = "{content:我和我的祖国}";
//        CipherUtil.AES aes = new CipherUtil.AES();
//        String txet = aes.encrypt(pw, text);
//        System.out.println(txet);
//        System.out.println(aes.decrypt(pw, txet));

        CipherUtil.RSA rsa = new RSA();
//        KeyPair keyPair = rsa.getKeyPair();
//        String pKey = new BASE64Encoder().encode(keyPair.getPublic().getEncoded());
//        String qKey = new BASE64Encoder().encode(keyPair.getPrivate().getEncoded());
//        System.out.println(rsa.encrypt(pKey, text));
//        System.out.println(rsa.decrypt(qKey, rsa.encrypt(pKey, text)));
        String s = rsa.decrypt("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALDnkLUdRHrEnkbw6PiMnzjEj_NvUS_ujgFOjGp_YrD1IMObr_v6Ws015t0P8WwjanV8Vn68cNcKQth25yn4rX2CGJ7QbfH4BdmFP47CGpH7Tnx5JlMzLh6r07zQqN9U7IsSusjbDCAGlJG6w7py2QxfeaLeq727FJJDcUN4yBNbAgMBAAECgYEAhgzi5aso4hKtkToqf3Unjf-8yQ24KqcNKkZYNrcSlN8bFb6ay0j5l3jZ2PekzHnFyQMQE_c9VmXrI1ZucqqC40xeD2RMCkYCEsXVscjO9gU1YTG0owgDEgTjaMbKwvT0ZnrYsRdmGaOtrxd9Rk7aHs5WHso8trU7ybMK_CwIzsECQQDwDn6bvKoVYU9V6OvIQa3aZEhkovMPgUo7YXgjduKdF0IlYz50W-z_1cW2OZ3lM9FLiy0ruknKa87OYBUAWVhhAkEAvKdXvP9a0GXHhi50q-50heSjoK6sX2EVNM0m2qdsZClK-OybTcIJBmNQL0MH7X09jMThGGvCcJ-IBYaizqjVOwJBALlERnxFH_L-Ne90orvbzKQW7zQT-oiF3Nd1QvYD46MfdyJJtMKyy3cq8brQPsbMtgOadxMfY6HAkEwAJVUunyECQBXKdpX8IO9FcmCLaSqa5pa4rDXZN0vlGN_6emxzPk_rfR93pRC8QvKb8h0jbvz9pGhoLV_2woTjyIBq5s9cLRUCQC6FoYB5_nltQT6leG-nNucf9fdjZhRhYlie_WNPUvPot2nP_WKuVCYNFj3opJiiWSGAlyjBgYmGz5SBgUNDcK8="
        , "jUfEbOToBGJ4gBK-2sYGIDmDJKmmG62jHYsiQ2lH61pa7zoFP3GcgvpbRvWnZvo8Il0miU0B4B1VRs5lTTeKzEOlpCwlP0E2ZDUJEDmUwli7oygN2ljfmvJsNjRakmZsiTOsutExHV6-jXtIZ3KOOqPocW-eYgJSoCwuNTsTP8=");
        System.out.println(s);
        }
}
