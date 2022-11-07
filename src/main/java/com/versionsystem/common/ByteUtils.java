package com.versionsystem.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * useful IO control util,and stream & channel alternate,and byte algorithm analysis
 *
 * @author Tian hao
 */
public class ByteUtils {

	private static Logger logger = LogManager.getLogger(ByteUtils.class);

	public static byte[] readFileToBytes(String path) {
//		ByteArray bytes = new ByteArray();
//		ByteBuffer buffer = ByteBuffer.allocate(1024);
		try {
			return Files.readAllBytes(Paths.get(path));
//			FileInputStream fin = new FileInputStream(path);
//			FileChannel channel = fin.getChannel();
//			int len;
//			while ((len = channel.read(buffer)) != -1) {
////				byte[] array = buffer.array();
////				for (int i = 0; i < len; i++) {
////					bytes.add(array[i]);
////				}
//			}
//			channel.close();
//			fin.close();
		} catch (Exception ex) {
			logger.warn("can't load file:" + path);
			throw new ApplicationException(ex.getMessage());
		}
//		return bytes.getBytes();
	}

	public static String readFileToBase64(String path) {
		//logger.warn("can't load file:" + path);
		return Base64.getEncoder().encodeToString(readFileToBytes(path));
	}

}