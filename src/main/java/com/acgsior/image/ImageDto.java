package com.acgsior.image;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Yove on 16/08/08.
 */
public class ImageDto {

	private static final int MAX_SIZE = 400;

	private String image;

	private int width;

	private int height;

	public static ImageDto newInstance(String image) {
		ImageDto instance = new ImageDto();
		instance.setImage(image.toLowerCase());
		try {
			instance.init();
		} catch (IOException e) {

		}
		return instance;
	}

	public FileInputStream getImageInputStream() throws FileNotFoundException {
		return new FileInputStream(image);
	}

	public ImageDto init() throws IOException {
		try (FileInputStream fis = getImageInputStream()) {
			BufferedImage bi = ImageIO.read(fis);
			if (bi.getWidth() <= MAX_SIZE) {
				width = bi.getWidth();
				height = bi.getHeight();
			} else {
				width = MAX_SIZE;
				height = Double.valueOf(bi.getHeight() * MAX_SIZE * 1d / bi.getWidth()).intValue();
			}
		}
		return this;
	}

	public int getFormat() {
		int format = 0;
		if (image.endsWith(".emf")) {
			format = XWPFDocument.PICTURE_TYPE_EMF;
		} else if (image.endsWith(".wmf")) {
			format = XWPFDocument.PICTURE_TYPE_WMF;
		} else if (image.endsWith(".pict")) {
			format = XWPFDocument.PICTURE_TYPE_PICT;
		} else if (image.endsWith(".jpeg") || image.endsWith(".jpg")) {
			format = XWPFDocument.PICTURE_TYPE_JPEG;
		} else if (image.endsWith(".png")) {
			format = XWPFDocument.PICTURE_TYPE_PNG;
		} else if (image.endsWith(".dib")) {
			format = XWPFDocument.PICTURE_TYPE_DIB;
		} else if (image.endsWith(".gif")) {
			format = XWPFDocument.PICTURE_TYPE_GIF;
		} else if (image.endsWith(".tiff")) {
			format = XWPFDocument.PICTURE_TYPE_TIFF;
		} else if (image.endsWith(".eps")) {
			format = XWPFDocument.PICTURE_TYPE_EPS;
		} else if (image.endsWith(".bmp")) {
			format = XWPFDocument.PICTURE_TYPE_BMP;
		} else if (image.endsWith(".wpg")) {
			format = XWPFDocument.PICTURE_TYPE_WPG;
		}
		return format;
	}

	public void setImage(final String image) {
		this.image = image;
	}

	public String getImage() {
		return image;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
