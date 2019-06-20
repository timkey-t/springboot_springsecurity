package com.timkey.demo.model;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @author tangfeng
 * @fileName ImageCode
 * @date 2019/6/19 15:49
 * @description 图片验证码信息
 */
@Data
public class ImageCode {

    private BufferedImage image;

    private String code;

    private LocalDateTime expireTime;

    public ImageCode(BufferedImage image, String code, int expireTime) {
        this.image = image;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireTime);
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        this.image = image;
        this.code = code;
        this.expireTime = expireTime;
    }
    public boolean isExpire(){
        return LocalDateTime.now().isAfter(expireTime);
    }
}
