package com.etiansoft.ole.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageUtil {

	public static void main(String[] args) {
	}

	public final static void scale(InputStream is, String imagePath) throws Exception {
		BufferedImage src = ImageIO.read(is); // 读入文件
		int width = src.getWidth(); // 得到源图宽
		int height = src.getHeight(); // 得到源图长
		if (width > 500 || height > 500) {
			if (width > height) {
				for (int i = 1; i < 20; i++) {
					if ((width / i) <= 500) {
						width = width / i;
						height = height / i;
						break;
					}
				}
			} else {
				for (int i = 1; i < 20; i++) {
					if ((height / i) <= 500) {
						width = width / i;
						height = height / i;
						break;
					}
				}
			}
		} else {
			if (width > height) {
				for (int i = 1; i < 20; i++) {
					if ((width * i) >= 400) {
						width = width * i;
						height = height * i;
						break;
					}
				}
			} else {
				for (int i = 1; i < 20; i++) {
					if ((height * i) >= 400) {
						width = width * i;
						height = height * i;
						break;
					}
				}
			}
		}
		Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = tag.getGraphics();
		g.drawImage(image, 0, 0, null); // 绘制缩小后的图
		g.dispose();
		ImageIO.write(tag, "JPEG", new File(imagePath));// 输出到文件流
	}
}