/*
 * Copyright (c) 2019 Vast Pure Technology(广洁科技). All rights reserved.
 *
 */
package com.shguangjie.realProj;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 图片合成功能
 *
 * @author baronjs
 * @since JDK1.8.0.181
 */
public class ImageUtils {

    /* 日志 */
    private final static Logger LOGGER = LoggerFactory.getLogger(ImageUtils.class);
    /* 要放置的商品图像宽度 */
    private static final int BACKGROUND_IMAGE_WIDTH = 1080;
    /* 要放置的商品图像宽度 */
    private static final int BACKGROUND_IMAGE_HEIGHT = 1600;
    /* 要放置的商品名称x坐标 */
    private static final int GOODS_NAME_X = 40;
    /* 要放置的商品名称y坐标 */
    private static final int GOODS_NAME_Y = 1360;
    /* 要放置的商品价格x坐标 */
    private static final int GOODS_PRICE_X = 40;
    /* 要放置的商品价格y坐标 */
    private static final int GOODS_PRICE_Y = 1220;
    /* 要放置的商品价格与划线价格的间距 */
    private static final int GOODS_PRICE_SPACING = 60;
    /* 要放置的商品图像宽度 */
    private static final int GOODS_IMAGE_MAX_SIZE = 1000;
    /* 要放置的商品图片x坐标 */
    private static final int GOODS_IMAGE_X = 40;
    /* 要放置的商品图片y坐标 */
    private static final int GOODS_IMAGE_Y = 40;
    /* 要放置的商品图像宽度 */
    private static final int GOODS_IMAGE_WIDTH = 1000;
    /* 要放置的商品图像高度 */
    private static final int GOODS_IMAGE_HEIGHT = 1000;
    /* 要放置的小程序码宽度 */
    private static final int QR_CODE_WIDTH = 380;
    /* 要放置的小程序码高度 */
    private static final int QR_CODE_HEIGHT = 380;
    /* 要放置的小程序码x坐标 */
    private static final int QR_CODE_X = 670;
    /* 要放置的小程序码y坐标 */
    private static final int QR_CODE_Y = 1080;
    /* 长按识别小程序码 */
    private static final String PROMPT = "长按识别小程序码";
    /* 长按识别小程序码x坐标 */
    private static final int PROMPT_X = 650;
    /* 长按识别小程序码y坐标 */
    private static final int PROMPT_Y = 1530;
    /* 字体_50 */
    private static final int FONT_50 = 50;
    /* 字体_60 */
    private static final int FONT_60 = 60;
    /* INT_10 */
    private static final int INT_10 = 10;
    /* INT_20 */
    private static final int INT_20 = 20;
    /* INT_27 */
    private static final int INT_27 = 27;

    /**
     * 生成分享图片(微信小程序)
     *
     * @param goodsImage        商品主图
     * @param qrCodeInputStream 小程序码输入流
     * @param goodsName         商品名称
     * @param goodsPrice        商品价格
     * @param goodsMarkingPrice 商品划线价格
     */
    public static BufferedImage generateSharingImage(String goodsImage, InputStream qrCodeInputStream,
                                                     String goodsName, String goodsPrice,
                                                     String goodsMarkingPrice) {
        try {
            // 等比例缩放商品图片
            BufferedImage bGoodsImage = scaleImage(ImageIO.read(new URL(goodsImage)), GOODS_IMAGE_MAX_SIZE);
            // 构建背景图
            BufferedImage backgroundImage = new BufferedImage(BACKGROUND_IMAGE_WIDTH, BACKGROUND_IMAGE_HEIGHT,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = backgroundImage.createGraphics();
            g.setColor(Color.WHITE);
            // 绘制白色背景图
            g.fillRect(0, 0, BACKGROUND_IMAGE_WIDTH, BACKGROUND_IMAGE_HEIGHT);
            g.setColor(Color.BLACK);
            // 绘制商品图片
            g.drawImage(bGoodsImage, GOODS_IMAGE_X, GOODS_IMAGE_Y, GOODS_IMAGE_WIDTH, GOODS_IMAGE_HEIGHT, null);
            g.setFont(new Font("宋体", Font.PLAIN, FONT_60));
            // 绘制商品价格
            g.drawString(goodsPrice, GOODS_PRICE_X, GOODS_PRICE_Y);
            // 商品划线价格存在的场合
            if (StringUtils.isNotBlank(goodsMarkingPrice)) {
                Font fontPrice = new Font("宋体", Font.PLAIN, FONT_50);
                g.setFont(fontPrice);
                FontMetrics fmPrice = g.getFontMetrics(fontPrice);
                g.setColor(Color.GRAY);
                // 绘制商品划线价格
                g.drawString(goodsMarkingPrice, GOODS_PRICE_X + fmPrice.stringWidth(goodsPrice) + GOODS_PRICE_SPACING,
                        GOODS_PRICE_Y);
                // 绘制商品划线价格对应的划线
                g.drawLine(GOODS_PRICE_X + fmPrice.stringWidth(goodsPrice) + GOODS_PRICE_SPACING, GOODS_PRICE_Y
                        - fmPrice.getHeight() / 4, GOODS_PRICE_X + fmPrice.stringWidth(goodsPrice)
                        + GOODS_PRICE_SPACING + fmPrice.stringWidth(goodsMarkingPrice), GOODS_PRICE_Y - fmPrice.getHeight() / 4);
            }
            Font fontName = new Font("宋体", Font.PLAIN, FONT_60);
            FontMetrics fmName = g.getFontMetrics(fontName);
            g.setFont(fontName);
            g.setColor(Color.BLACK);
            // 绘制商品名称
            drawGoodsName(g, goodsName, fmName);
            // 绘制小程序码图片
            g.drawImage(ImageIO.read(qrCodeInputStream), QR_CODE_X, QR_CODE_Y, QR_CODE_WIDTH, QR_CODE_HEIGHT, null);
            g.setColor(Color.GRAY);
            g.setFont(new Font("宋体", Font.PLAIN, FONT_50));
            // 绘制文本(长按识别小程序码)
            g.drawString(PROMPT, PROMPT_X, PROMPT_Y);
            g.dispose();
            return backgroundImage;
        } catch (IOException e) {
            LOGGER.error("生成分享图片(微信小程序)失败!");
            System.out.println("生成分享图片(微信小程序)失败!");
            return null; // 真实项目抛出项目自定义异常
        }
    }

    /**
     * 图像等比例缩放(高度与宽度的最大值为maxSize进行等比缩放)
     *
     * @param image   图像
     * @param maxSize 最大值
     */
    private static BufferedImage scaleImage(BufferedImage image, int maxSize) {
        int w0 = image.getWidth();
        int h0 = image.getHeight();
        int w, h;
        w = w0 > h0 ? maxSize : (maxSize * w0 / h0);
        h = w0 > h0 ? (maxSize * h0 / w0) : maxSize;
        Image scaleImage = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(scaleImage, 0, 0, null);
        return bufferedImage;
    }

    /**
     * 绘制商品名称
     *
     * @param g         画笔
     * @param goodsName 商品名称
     * @param fmName    FontMetrics
     */
    private static void drawGoodsName(Graphics2D g, String goodsName, FontMetrics fmName) {
        if (StringUtils.length(goodsName) <= INT_10) {
            // 绘制商品名称
            g.drawString(goodsName, GOODS_NAME_X, GOODS_NAME_Y);
        } else if (StringUtils.length(goodsName) <= INT_20) {
            // 绘制商品名称
            g.drawString(StringUtils.substring(goodsName, 0, INT_10), GOODS_NAME_X, GOODS_NAME_Y);
            // 绘制商品名称
            g.drawString(StringUtils.substring(goodsName, INT_10, StringUtils.length(goodsName)),
                    GOODS_NAME_X, GOODS_NAME_Y + fmName.getHeight());
        } else if (StringUtils.length(goodsName) <= INT_27) {
            // 绘制商品名称
            g.drawString(StringUtils.substring(goodsName, 0, INT_10), GOODS_NAME_X, GOODS_NAME_Y);
            // 绘制商品名称
            g.drawString(StringUtils.substring(goodsName, INT_10, INT_20),
                    GOODS_NAME_X, GOODS_NAME_Y + fmName.getHeight());
            // 绘制商品名称
            g.drawString(StringUtils.substring(goodsName, INT_20, StringUtils.length(goodsName)),
                    GOODS_NAME_X, GOODS_NAME_Y + 2 * fmName.getHeight());
        } else {
            // 绘制商品名称
            g.drawString(StringUtils.substring(goodsName, 0, INT_10), GOODS_NAME_X, GOODS_NAME_Y);
            // 绘制商品名称
            g.drawString(StringUtils.substring(goodsName, INT_10, INT_20),
                    GOODS_NAME_X, GOODS_NAME_Y + fmName.getHeight());
            // 绘制商品名称
            g.drawString(StringUtils.substring(goodsName, INT_20, INT_27) + "···",
                    GOODS_NAME_X, GOODS_NAME_Y + 2 * fmName.getHeight());
        }
    }
}
