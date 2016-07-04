package com.acgsior.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.monoid.web.Resty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Yove on 16/07/01.
 */
public class ImageSyncDownloader extends ImageDownloader {

	private static Logger log = LoggerFactory.getLogger(ImageSyncDownloader.class);

	private boolean deletedDuplicate;

	public Path downloadImage(ImageType type, String URL, String imageId) throws IOException {
		Path imagePath = generateImagePath(type, URL, imageId);
		log.info(String.format("Synchronize download image: %s", URL));
		if (Files.exists(imagePath) && deletedDuplicate) {
			Files.delete(imagePath);
		}
		new Resty().bytes(URL).save(Files.createFile(imagePath).toFile());
		return imagePath;
	}

	public void setDeletedDuplicate(final boolean deletedDuplicate) {
		this.deletedDuplicate = deletedDuplicate;
	}
}
