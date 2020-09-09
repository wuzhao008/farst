/**
 * Project: sp-common
 * 
 * File Created at 2018-10-11
 * $Id$
 * 
 * Copyright 2018 uup.com Croporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * uup Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with uup.com.
 */
package com.farst.common.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;

import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * TODO Comment of CCCC
 * 
 * @author MichalWoo
 * @version $Id: ImageUtils.java 2018-10-15 下午09:37:30 $
 */
public class ImageUtils {
 

	static {

		PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

	}

	/**
	 * 生成水印图的数据流
	 */
	public final static InputStream waterMarkImage(File waterMark,
			InputStream is) {
		try {
			InputStream is_result = null;
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);

			// 目标文件
			BufferedImage image = ImageIO.read(is);
			int wideth = image.getWidth();
			int height = image.getHeight();
			Graphics2D g = image.createGraphics();
			g.drawImage(image, 0, 0, wideth, height, null);

			// 水印文件
			InputStream is_watermark = new FileInputStream(waterMark);
			BufferedImage src_biao = ImageIO.read(is_watermark);
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			// 在已经绘制的图片中加入透明度通道
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					0.2f));
			g.drawImage(src_biao, (wideth - wideth_biao) / 2,
					(height - height_biao) / 2, wideth_biao, height_biao, null);

			// 水印文件结束
			g.dispose();
			ImageIO.write(image, "jpg", imOut);

			is_result = new ByteArrayInputStream(bs.toByteArray());
			image.flush();
			return is_result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成水印图的数据流
	 */
	public final static InputStream waterMarkImage(File waterMark,
			BufferedImage image) {
		try {
			InputStream is_result = null;
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);

			// 目标文件
			int wideth = image.getWidth();
			int height = image.getHeight();
			Graphics2D g = image.createGraphics();
			g.drawImage(image, 0, 0, wideth, height, null);

			// 水印文件
			InputStream is_watermark = new FileInputStream(waterMark);
			BufferedImage src_biao = ImageIO.read(is_watermark);
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			// 在已经绘制的图片中加入透明度通道
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					0.2f));
			g.drawImage(src_biao, (wideth - wideth_biao) / 2,
					(height - height_biao) / 2, wideth_biao, height_biao, null);

			// 水印文件结束
			g.dispose();
			ImageIO.write(image, "jpg", imOut);

			is_result = new ByteArrayInputStream(bs.toByteArray());
			image.flush();
			return is_result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成缩略图
	 * 
	 * @param srcFile
	 *            原文件
	 * @param targetWidth
	 *            目标文件宽度
	 * @param targetHeight
	 *            目标文件高度
	 * @param fillBlank
	 *            是否填充空白
	 * @return
	 */
	public static InputStream zoomImage(File srcFile, int targetWidth,
			int targetHeight, boolean fillBlank, File watermark) {
		try {
			int srcWidth, srcHeight;
			BufferedImage bufferedImage = null;
			InputStream is = null;
			try {
				// bufferedImage = ImageIO.read(srcFile); //读取图片,可能导致ICC信息丢失
				Image image = Toolkit.getDefaultToolkit().getImage(
						srcFile.getPath());
				bufferedImage = ImageUtils.toBufferedImage(image);
				srcWidth = bufferedImage.getWidth();
				srcHeight = bufferedImage.getHeight();
			} catch (Exception e) {
				return null;
			}
			double sx = (double) targetWidth / srcWidth;
			double sy = (double) targetHeight / srcHeight;
			sx = sx <= sy ? sx : sy;
			double scale = 0.00;
			scale = sx == 0 ? sy : sx;
			if (targetWidth == 0 && targetHeight == 0) {
				scale = 1;
			}
			Image image = bufferedImage.getScaledInstance(
					(int) (srcWidth * scale), (int) (srcHeight * scale),
					Image.SCALE_SMOOTH);
			BufferedImage bid = new BufferedImage((int) (srcWidth * scale),
					(int) (srcHeight * scale), BufferedImage.TYPE_INT_RGB);
			bid.getGraphics().drawImage(image, 0, 0, null);
			bid.getGraphics().dispose();

			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
			if (fillBlank) {
				int x = (targetWidth - (int) (srcWidth * scale)) / 2;
				int y = (targetHeight - (int) (srcHeight * scale)) / 2;
				targetWidth = targetWidth == 0 ? (int) (srcWidth * scale)
						: targetWidth;
				targetHeight = targetHeight == 0 ? (int) (srcHeight * scale)
						: targetHeight;
				BufferedImage scaleImg = new BufferedImage(targetWidth,
						targetHeight, BufferedImage.TYPE_INT_BGR);
				Graphics2D graphics = scaleImg.createGraphics();
				graphics.setColor(Color.white);
				graphics.fillRect(0, 0, targetWidth, targetHeight);

				graphics.drawImage(bid, x < 0 ? 0 : x, y < 0 ? 0 : y,
						Color.white, null);
				graphics.dispose();
				ImageIO.write(scaleImg, "jpg", imOut);
			} else {
				ImageIO.write(bid, "jpg", imOut);
			}
			is = new ByteArrayInputStream(bs.toByteArray());
			bid.flush();

			if (watermark != null) {
				is = ImageUtils.waterMarkImage(watermark, is);
			}
			return is;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取图片的真实像素
	 * 
	 * @param imgPath
	 * @return
	 */
	public static int[] getImgW_H(File file) {
		FileChannel fc = null;
		if (file.exists() && file.isFile()) {
			try {
				@SuppressWarnings("resource")
				FileInputStream fs = new FileInputStream(file);
				fc = fs.getChannel();
				System.out.println(fc.size() + "-----fc.size()");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(file.length() + "-----file.length  B");
		System.out.println(file.length() * 1024 + "-----file.length  kb");
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int width = bi.getWidth();
		int height = bi.getHeight();
		System.out.println("宽：像素-----" + width + "高：像素" + height);

		int[] result = { width, height };

		return result;
	}

	
	public static void cut(File file, String outFile,int targetWidth,int targetHeight) throws IOException {
		
		int[] w_h = getImgW_H(file);
		int width = w_h[0];
		int height = w_h[1];

		// 计算截图的坐标位置
		boolean prop = (float)width / (float)targetWidth > (float)height / (float)targetHeight;

		int x = 0;
		int y = 0;

		// 图片过宽
		if (prop) {
			x = (width - ((height * targetWidth) / targetHeight)) / 2;
			System.out.print("x==="+x);
			width = (height * targetWidth) / targetHeight;
			System.out.print("width==="+width);
		} else {
			// 图片过高
			System.out.print("height==="+height);
			System.out.print("width==="+width);
			System.out.print("targetHeight==="+targetHeight);
			System.out.print("targetWidth==="+targetWidth);
			y = (height - ((width * targetHeight) / targetWidth)) / 2;
			System.out.print("y==="+y);
			height = (width * targetHeight) / targetWidth;
			System.out.print("height==="+height);
		}

		ImageInputStream imageStream = null;
		try {
			Iterator<ImageReader> readers = ImageIO
					.getImageReadersByFormatName(outFile.substring(outFile.lastIndexOf(".")+1));
			ImageReader reader = readers.next();
			imageStream = ImageIO.createImageInputStream(file);
			reader.setInput(imageStream, true);
			ImageReadParam param = reader.getDefaultReadParam();

			System.out.println(reader.getWidth(0));
			System.out.println(reader.getHeight(0));
			Rectangle rect = new Rectangle(x, y, width, height);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, "jpg", new FileOutputStream(outFile));
		} finally {
			imageStream.close();
		}
	}

	/**
	 * 为防止ICC信息丢失,将image按照一定方式转换成BufferedImage
	 * 
	 * @param image
	 * @return
	 */
	public static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}
		image = new ImageIcon(image).getImage();
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		try {
			int transparency = Transparency.OPAQUE;

			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null),
					image.getHeight(null), transparency);
		} catch (HeadlessException e) {
		}
		if (bimage == null) {
			int type = BufferedImage.TYPE_INT_RGB;
			bimage = new BufferedImage(image.getWidth(null),
					image.getHeight(null), type);
		}
		Graphics g = bimage.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return bimage;
	}

	/**
	 * 测试生成缩略图
	 * 
	 * @param a
	 */
	public static void main(String[] a) throws Exception {
		String url = "E:\\1.jpg";
		File file = new File(url);
		File waterMark_original = new File("E:\\log_1.png");
		File waterMark = new File("E:\\log_11.png");
		// 原图的水印图测试
		InputStream fin_original = null;

		File savedFile_original = new File("E:\\11.jpg");

		Image image = Toolkit.getDefaultToolkit().getImage(file.getPath());
		BufferedImage bufferedImage = ImageUtils.toBufferedImage(image);
		// 为了防止原图ICC失真，调用此方法
		fin_original = ImageUtils.waterMarkImage(waterMark_original,
				bufferedImage);
		FileOutputStream fout_original = null;
		byte[] buffer = new byte[10240];
		int read = -1;
		try {
			if (!savedFile_original.exists()) {
				if (!savedFile_original.createNewFile()) {
				}
			}
			fout_original = new FileOutputStream(savedFile_original);
			read = fin_original.read(buffer);
			while (read > 0) {
				fout_original.write(buffer);
				read = fin_original.read(buffer);
			}
		} catch (Exception e) {
		} finally {
			try {
				fin_original.close();
				fout_original.close();
			} catch (IOException e) {
			}
		}

		// 400x400缩略图+水印图测试
		File savedFile = new File("E:\\22.jpg");
		InputStream fin = ImageUtils.zoomImage(file, 400, 400, true, waterMark);

		FileOutputStream fout = null;
		buffer = new byte[10240];
		read = -1;
		try {
			if (!savedFile.exists()) {
				if (!savedFile.createNewFile()) {
				}
			}
			fout = new FileOutputStream(savedFile);
			read = fin.read(buffer);
			while (read > 0) {
				fout.write(buffer);
				read = fin.read(buffer);
			}
		} catch (Exception e) {
		} finally {
			try {
				fin.close();
				fout.close();
			} catch (IOException e) {
			}
		}

	}

}
