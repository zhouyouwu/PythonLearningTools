package club.zhouyouwu.graduate.usermanagement.controller;

import club.zhouyouwu.graduate.usermanagement.utils.CipherUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.util.Base64;

@RestController
public class UserController {
    private String privateKey;

    @GetMapping("test")
    public String test() throws Exception {
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
