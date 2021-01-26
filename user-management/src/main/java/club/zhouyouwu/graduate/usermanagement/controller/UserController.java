package club.zhouyouwu.graduate.usermanagement.controller;

import club.zhouyouwu.graduate.usermanagement.utils.CipherUtil;
import org.springframework.web.bind.annotation.*;

import java.security.KeyPair;
import java.util.Base64;

@RestController
@RequestMapping("/user")
public class UserController {
    private String privateKey;

    /**
     * 登录注册时获取公钥
     * @param userId 不存在则-1
     * @return
     * @throws Exception
     */
    @GetMapping("/{userId}/publicKey")
    public String getPublicKey(@PathVariable String userId) throws Exception {
        KeyPair keyPair = new CipherUtil.RSA().getKeyPair();
        privateKey = Base64.getUrlEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        System.out.println(privateKey);
        return Base64.getUrlEncoder().encodeToString(keyPair.getPublic().getEncoded());
    }

    @PostMapping("tset")
    public String tset(@RequestParam("text") String text) throws Exception {
        CipherUtil.RSA rsa = new CipherUtil.RSA();
        System.out.println(text);
        return rsa.decrypt(privateKey, text);
    }
}
