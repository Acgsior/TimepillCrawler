package com.acgsior.selector.impl;

import com.acgsior.image.ImageAsyncDownloader;
import com.acgsior.image.ImageSyncDownloader;
import com.acgsior.image.ImageType;
import com.acgsior.selector.AttributeObjectSelector;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by Yove on 16/07/01.
 */
public class ImageObjectSelector extends AttributeObjectSelector {

	private static Logger logger = LoggerFactory.getLogger(ImageObjectSelector.class);

	private boolean synchronize;

	@Autowired
	private ImageSyncDownloader imageSyncDownloader;

	private ImageAsyncDownloader imageAsyncDownloader;

	private ImageType imageType;

	@Override
	public String select(final Element element, final Optional parentId) {
		String imageSrc = standardizeImageURL(super.select(element, parentId));
		if (synchronize) {
			try {
				imageSyncDownloader.downloadImage(imageType, imageSrc, String.valueOf(parentId.get()));
			} catch (IOException e) {
				logger.error(String.format("Failed to download image: %s", imageSrc));
			}
		} else {
			imageAsyncDownloader.downloadImage(imageType, imageSrc, String.valueOf(parentId.get()));
		}
		return String.valueOf(parentId.get());
	}

	protected String standardizeImageURL(String URL) {
		if (!URL.startsWith("http:")) {
			return "http:".concat(URL);
		}
		return URL;
	}

	public void setSynchronize(final boolean synchronize) {
		this.synchronize = synchronize;
	}

	public void setImageType(final ImageType imageType) {
		this.imageType = imageType;
	}
}