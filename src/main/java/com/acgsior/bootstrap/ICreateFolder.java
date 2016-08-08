package com.acgsior.bootstrap;

import com.acgsior.exception.CrawlerInitialException;
import org.apache.commons.lang3.SystemUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

/**
 * Created by mqin on 8/3/16.
 */
public interface ICreateFolder {

    default void createFolder(final Path path) {
        try {
            if (SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_LINUX) {
                Files.createDirectories(path, getMacFolderAttributes());
            } else if (SystemUtils.IS_OS_WINDOWS) {
                Files.createDirectories(path, getMacFolderAttributes());
            }
        } catch (Exception e) {
            String msg = String.format("Create file directory failed for path: %s", path);
            throw new CrawlerInitialException(msg);
        }
    }

    default FileAttribute<Set<PosixFilePermission>> getMacFolderAttributes() {
        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxr-xr-x");
        return PosixFilePermissions.asFileAttribute(perms);
    }
}
