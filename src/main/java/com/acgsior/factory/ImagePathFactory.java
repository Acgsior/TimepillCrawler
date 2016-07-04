package com.acgsior.factory;

import com.acgsior.image.ImageType;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Map;
import java.util.Set;

/**
 * Created by Yove on 16/07/02.
 */
public class ImagePathFactory {

	private static Logger log = LoggerFactory.getLogger(ImagePathFactory.class);

	private Map<ImageType, String> imageTypeBasePathMap;

	public ImagePathFactory(Map<ImageType, String> imageTypeBasePathMap) {
		imageTypeBasePathMap.forEach((imageType, dir) -> {
			Path path = Paths.get(dir);
			if (Files.notExists(path, LinkOption.NOFOLLOW_LINKS)) {
				createImageFolder(path);
			}
		});
		this.imageTypeBasePathMap = imageTypeBasePathMap;
	}

	public String getImageExtension(String URL) {
		return Splitter.on('?').splitToList(Iterables.getLast(Splitter.on('.').split(URL))).get(0);
	}

	public String getPathWithoutExtension(ImageType imageType, String objectId) {
		return imageTypeBasePathMap.get(imageType).toString().concat(objectId);
	}

	private void createImageFolder(final Path path) {
		try {
			if (SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_LINUX) {
				Files.createDirectories(path, getMacFolderAttributes());
			} else if (SystemUtils.IS_OS_WINDOWS) {
				Files.createDirectories(path, getMacFolderAttributes());
			}
		} catch (Exception e) {
			String msg = String.format("Create file directory failed for path: %s", path);
			log.error(msg, e);
			// throw new CrawlerInitialException(msg);
		}
	}

	protected FileAttribute<Set<PosixFilePermission>> getMacFolderAttributes() {
		Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxrwxrwx");
		return PosixFilePermissions.asFileAttribute(perms);
	}

}
