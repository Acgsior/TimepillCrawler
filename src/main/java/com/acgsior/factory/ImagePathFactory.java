package com.acgsior.factory;

import com.acgsior.bootstrap.ICreateFolder;
import com.acgsior.exception.CrawlerInitialException;
import com.acgsior.image.ImageType;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Created by Yove on 16/07/02.
 */
public class ImagePathFactory implements ICreateFolder {

	private static Logger log = LoggerFactory.getLogger(ImagePathFactory.class);

	private Map<ImageType, String> imageTypeBasePathMap;

	public ImagePathFactory(Map<ImageType, String> imageTypeBasePathMap) {
		imageTypeBasePathMap.values().forEach(dir -> initial(dir));
		this.imageTypeBasePathMap = imageTypeBasePathMap;
	}

	protected void initial(String dir) {
		Path path = Paths.get(dir);
		if (Files.notExists(path, LinkOption.NOFOLLOW_LINKS)) {
			try {
				createFolder(path);
			} catch (CrawlerInitialException e) {
				log.error("Create image file directory failed: ", e);
			}
		}
	}

	public String getImageExtension(String URL) {
		return Splitter.on('!').splitToList(Splitter.on('?').splitToList(Iterables.getLast(Splitter.on('.').split(URL))).get(0)).get(0);
	}

	public String getPathWithoutExtension(ImageType imageType, String objectId) {
		return imageTypeBasePathMap.get(imageType).toString().concat(objectId);
	}
}
