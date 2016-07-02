package com.acgsior.image;

import com.acgsior.factory.ImagePathFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Yove on 16/07/02.
 */
public abstract class ImageDownloader {

	protected ImagePathFactory imagePathFactory;

	public Path generateImagePath(ImageType type, String URL, String imageId) {
		String imagePath = new StringBuilder().append(imagePathFactory.getPathWithoutExtension(type, imageId)).append('.')
				.append(imagePathFactory.getImageExtension(URL)).toString();
		return Paths.get(imagePath);
	}

	public void setImagePathFactory(final ImagePathFactory imagePathFactory) {
		this.imagePathFactory = imagePathFactory;
	}
}
