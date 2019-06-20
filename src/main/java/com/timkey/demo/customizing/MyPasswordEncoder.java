package com.timkey.demo.customizing;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author tangfeng
 * @fileName MyPasswordEncoder
 * @date 2019/6/19 17:51
 * @description 自定义加密方式
 */
public class MyPasswordEncoder  implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        if (charSequence==null || s==null){
            return false;
        }
        return charSequence.toString().equals(s);
    }
}
