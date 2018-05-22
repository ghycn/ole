package com.etiansoft.ole.util;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FilePathProvider {

	@Value("${uploadPath}")
	public String uploadPath;

	public String getUploadPath(String relativePath) {
		File rootFolder = new File(uploadPath);
		if (!rootFolder.isDirectory()) {
			rootFolder.mkdir();
		}
		File file = new File(rootFolder + File.separator + relativePath);
		File parentFile = file.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		return file.getAbsolutePath();
	}
}
