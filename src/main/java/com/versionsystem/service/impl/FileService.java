package com.versionsystem.service.impl;

import com.versionsystem.basic.parameter.ParameterService;
import com.versionsystem.common.ApplicationException;
import com.versionsystem.common.DataMap;
import com.versionsystem.common.ProcessBuilderInstance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;
import java.util.zip.CRC32;

@Service
public class FileService {

	@Autowired
	private ParameterService parameterService;

	@Autowired
	private ProcessBuilderInstance pbInstance;
	@Autowired
	private HttpServletRequest httpRequest;

	private static final Logger logger = LogManager.getLogger(FileService.class);
	private final String RESOURCE_PATH = "WEB-INF/resources";

	public DataMap upload(InputStream input, String uploadPath, String fileName) {
		DataMap result = new DataMap();
		try {
			if (!uploadPath.endsWith("/")) {
				uploadPath += "/";
			}
			String path = uploadPath + fileName;
			result.put("fileName", fileName);
			result.put("uploadPath", uploadPath);
			result.put("fullPath", path);
			result.put("success", true);
			pbInstance.exeCommand("mkdir -p " + uploadPath);
			String crc = writeExtractCRC(createFile(uploadPath, fileName), input);
			result.put("crcVal", crc);
		} catch (Exception ex) {
			result.put("success", false);
			ex.printStackTrace();
			logger.error(ex);
		}
		return result;
	}

	public static File createFile(String uploadFolder, String fileName) {
		File newFile = new File(uploadFolder, fileName);
		try {
			if (newFile.exists()) {
				throw new ApplicationException("This file already exists");
			}
			if (!newFile.createNewFile()) {
				throw new ApplicationException("Unable to create new file");
			}
		} catch (IOException e) {
			logger.warn("Unable to create new file:");
			logger.warn(newFile.getPath());
		}
		return newFile;
	}

	/**
	 * 将文件写到磁盘，并在写入时提取文件的CRC32值.
	 */
	public static String writeExtractCRC(File newFile, InputStream stream) {
		CRC32 crc = new CRC32();
		logger.info("writeExtractCRC:" + newFile.getAbsolutePath());
		try (RandomAccessFile fw = new RandomAccessFile(newFile, "rwd")) {
			FileChannel outChannel = fw.getChannel();
			FileLock flout = outChannel.tryLock();
			ByteBuffer buf = ByteBuffer.allocate(1024);
			ReadableByteChannel inChannel = Channels.newChannel(stream);
			while (inChannel.read(buf) != -1) {
				buf.flip();
				outChannel.write(buf);
				crc.update(buf.array());//CRC32计算
				buf.compact();
			}
			flout.release();//解锁
		} catch (Exception e) {
			logger.error("Failed during file reading and writing");
			e.printStackTrace();
			throw new ApplicationException(e.getMessage());
		}
		String crcVal = Long.toHexString(crc.getValue());
		logger.info("crc value:" + crcVal);
		return crcVal;
	}

	/**
	 * @see this.writeExtractCRC(String,InputStream)
	 */
	@Deprecated
	public static void fileUpload(InputStream in, String filename, String upload_path)
			throws IOException {
		File uploadFolder = new File(upload_path);
		if (!uploadFolder.exists()) {
			uploadFolder.mkdirs();
		}
		File uploadFile = new File(uploadFolder, filename);
		OutputStream out = new FileOutputStream(uploadFile);
		int length;
		byte[] buffer = new byte[1024 * 1024];
		while ((length = in.read(buffer)) > 0) {
			out.write(buffer, 0, length);
		}
		in.close();
		out.close();
	}

	/**
	 * 假删除
	 * 如果文件在磁盘上，则移走文件重命名为[UUID]
	 * 如果是AWS，暂时不处理
	 */
	public void move(String folder, String fileName){
		String bucket = parameterService.findKey("app.aws.s3.bucket");
		if (!bucket.isEmpty()) {
			return;
		}
		File moveFile = new File(folder, fileName);
		if (!moveFile.exists()) {
			throw new ApplicationException("The " + fileName + "file cannot be found");
		}
		Path path = Paths.get(moveFile.toURI());
		try {
			Files.move(path, path.resolveSibling(UUID.randomUUID().toString()));
		} catch (IOException e) {
			e.printStackTrace();
			throw new ApplicationException(e.getMessage());
		}
	}

	public DataMap uploadFile(CommonsMultipartFile file, String filename) throws IOException {
		return this.upload(file.getInputStream(), getUploadPath(), filename + getFileType(file.getContentType()));
	}

	public String getUploadPath() {
		String projPath = httpRequest.getSession().getServletContext().getRealPath(File.separator);
		return projPath + RESOURCE_PATH;
	}

	private String getFileType(String fileType) {
		switch (fileType) {
			case "image/jpg":
			case "image/jpeg":
				return ".jpg";
			case "image/png":
			case "image/x-png":
				return ".png";
			case "image/gif":
				return ".gif";
			case "image/bmp":
				return ".bmp";
			case "application/pdf":
				return ".pdf";
			default:
				throw new ApplicationException("Unknown image or PDF type");
		}
	}

}
