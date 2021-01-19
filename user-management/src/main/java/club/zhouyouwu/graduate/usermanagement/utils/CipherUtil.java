package club.zhouyouwu.graduate.usermanagement.utils;

import org.bouncycastle.jcajce.provider.digest.SHA256;
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
        String salt = BCrypt.gensalt();
        String pp = BCrypt.hashpw(pw, salt);

        System.out.println(pp);
        String ww = BCrypt.hashpw(pw, salt);
        System.out.println(ww);
        System.out.println(BCrypt.checkpw(pw, ww));
        System.out.println(rsa.encrypt("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC7AUhS5GzYFIefstz6Hp4Mefdg-NzrChDnKCTJIOsfh2d-FgL8oGJvwEVYalkftTRkbPWbcf-DOJuwenQUqhEC8ab3868okHxU8wwF9UM0WR5_hXk9g6zcTIyyn4ttfQ3m51m9kCOn1Q2GoE7Ibva_vUTbErEhOH4rimQXJEffsQIDAQAB",text));
    }
}
