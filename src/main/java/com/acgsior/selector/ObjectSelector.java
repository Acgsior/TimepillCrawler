package com.acgsior.selector;

import com.acgsior.bootstrap.ICrawledDataCache;
import com.acgsior.cache.CacheManager;
import org.jsoup.nodes.Document;

import java.util.Optional;

/**
 * Created by Yove on 16/07/01.
 */
public abstract class ObjectSelector<T> extends AbstractSelector {

    private AttributeObjectSelector idSelector;

    private PropertySelector[] syncSelectors;

    private ObjectSelector[] asyncSelectors;

    private CacheManager cacheManager;

    public abstract T select(Document document, Optional<String> parentId);

    public AttributeObjectSelector getIdSelector() {
        return idSelector;
    }

    public void setIdSelector(AttributeObjectSelector idSelector) {
        this.idSelector = idSelector;
    }

    public PropertySelector[] getSyncSelectors() {
        return syncSelectors;
    }

    public void setSyncSelectors(final PropertySelector[] syncSelectors) {
        this.syncSelectors = syncSelectors;
    }

    public ObjectSelector[] getAsyncSelectors() {
        return asyncSelectors;
    }

    public void setAsyncSelectors(final ObjectSelector[] asyncSelectors) {
        this.asyncSelectors = asyncSelectors;
    }

    public ICrawledDataCache getCache() {
        return cacheManager.getCache();
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}