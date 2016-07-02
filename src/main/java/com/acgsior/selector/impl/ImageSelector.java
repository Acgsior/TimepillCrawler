package com.acgsior.selector.impl;

import com.acgsior.image.ImageAsyncDownloader;
import com.acgsior.image.ImageSyncDownloader;
import com.acgsior.image.ImageType;
import com.acgsior.selector.AttributeSelector;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by Yove on 16/07/01.
 */
public class ImageSelector extends AttributeSelector {

	private static Logger logger = LoggerFactory.getLogger(ImageSelector.class);

	private boolean synchronize;

	@Autowired
	private ImageSyncDownloader imageSyncDownloader;

	private ImageAsyncDownloader imageAsyncDownloader;

	private ImageType imageType;

	@Override
	public String select(final Document document, final Optional parentId) {
		String imageSrc = standardizeImageURL(super.select(document, parentId));
		if (synchronize) {
			try {
				imageSyncDownloader.downloadImage(imageType, imageSrc, String.valueOf(parentId.get()));
			} catch (IOException e) {
				logger.error(String.format("Failed to download image: %s", imageSrc), e);
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