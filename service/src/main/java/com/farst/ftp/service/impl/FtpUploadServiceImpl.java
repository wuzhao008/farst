/**
 * Project: sp-biz-mainsite
 * 
 * File Created at 2018-10-11
 * $Id$
 * 
 * Copyright 2018 uup.com Croporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * uup Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with uup.com.
 */
package com.farst.ftp.service.impl; 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.farst.common.exception.ServiceException;
import com.farst.common.utils.DateUtils;
import com.farst.common.utils.ImageUtils;
import com.farst.common.utils.StringUtils;
import com.farst.ftp.service.FtpUploadService; 

/**
 * FTP操作实现类
 * 
 * @author MichaelWoo
 * @version $Id: FtpUpload.java 2018-10-14 下午7:44:26 $
 */
@Service
public class FtpUploadServiceImpl implements FtpUploadService {

	private static org.slf4j.Logger log = LoggerFactory
			.getLogger(FtpUploadServiceImpl.class);

	private ThreadLocal<FTPClient> ftpClientThreadLocal = new ThreadLocal<FTPClient>();

	@Value("${spring.ftp.password}")
	public String passrod;

	@Value("${spring.ftp.username}")
	public String username;

	@Value("${spring.ftp.address}")
	public String address;

	@Value("${spring.ftp.workingDirectory}")
	public String workingDirectory;

	@Value("${spring.ftp.port}")
	public String port;

	@Value("${spring.ftp.retry}")
	public String connectRetry;

	@Value("${spring.ftp.tempFilePath}")
	public String tempFilePath;

	public FTPClient getFTPClient() {
		FTPClient ftpClient = null;
		if (ftpClientThreadLocal.get() != null
				&& ftpClientThreadLocal.get().isConnected()) {
			ftpClient = ftpClientThreadLocal.get();
		} else {
			try {
				ftpClient = this.connectServer();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ftpClient;
	}

	/**
	 * 打开连接 connect to ftp server
	 * 
	 * @throws IOException
	 * @exception IllegalArgumentException
	 *                address/port/username/port incorrect
	 */
	public FTPClient connectServer() throws IOException {
		FTPClient ftpClient = new FTPClient();
		// connect to server
		int retryTimes = 0;
		while (!ftpClient.isConnected() && retryTimes++ < getFtpConnectRetry()) {
			try {
				ftpClient.connect(this.address, Integer.valueOf(this.port));
				log.debug(toString(ftpClient.getReplyStrings()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (!ftpClient.isConnected()) {
			// also not connected
			throw new IllegalArgumentException(
					"connect failed. maybe address or port is incorrect");
		}
		// login
		if (!ftpClient.login(this.username, this.passrod)) {
			log.debug(toString(ftpClient.getReplyStrings()));
			ftpClient.logout();
			log.debug(toString(ftpClient.getReplyStrings()));
			ftpClient.disconnect();
			log.debug(toString(ftpClient.getReplyStrings()));
			throw new IllegalArgumentException(
					"failed to login ftp,please check the username or password");
		}
		log.debug(toString(ftpClient.getReplyStrings()));
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		// 开通一个端口来传输数据
		ftpClient.enterLocalPassiveMode();
		ftpClient.setSoTimeout(300000);
		ftpClient.setControlKeepAliveTimeout(300);
		ftpClient.setControlKeepAliveReplyTimeout(60000);
		ftpClientThreadLocal.set(ftpClient);
		return ftpClient;
	}

	/**
	 * 关闭连接
	 */
	public void closeConnect() throws IOException {
		FTPClient ftpClient = this.ftpClientThreadLocal.get();
		if (ftpClient != null && ftpClient.isConnected()) {
			try {
				ftpClient.logout();
			} catch (IOException e) {
				e.printStackTrace();
				log.error("not disconnect");
			} finally {
				ftpClient.disconnect();
			}
		}
	}

	@Override
	public String uploadFile(File file, String uploadPath)
			throws ServiceException {
		System.out.println("===========开始上传文件============");
		System.out.println("=====开始时间:" + DateUtils.getCurDate());

		String fileName = file.getName();
		String fileType = "";
		if (fileName != null) {
			fileType = fileName.substring(fileName.lastIndexOf("."));
		}

		// 得到服务器上的路径
		String timePath = DateUtils.getFilePathUseTime("/");

		// 上传后的图片对应URL
		String uploadUrl = uploadPath + timePath; 

		// 新图片名
		String newFileName = UUID.randomUUID().toString() + fileType;

		// 开始上传
		InputStream fin = null;
		FTPClient ftpClient = null;
		try {
			ftpClient = this.getFTPClient();
			fin = new FileInputStream(file);
			makeDirectory(uploadUrl);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				uploadUrl = null;
				fin.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			try{
				this.closeConnect();
			}catch(IOException ee){
				
			}
			throw new ServiceException(
					"uploadFile fail in FtpUploadServiceImpl", e);
		}
		try {
			ftpClient.storeFile(newFileName, fin);
			System.out
					.println("=====FTP返回CODE=====" + ftpClient.getReplyCode());
			System.out.println("=====结束时间:" + DateUtils.getCurDate());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(
					"uploadFile fail in FtpUploadServiceImpl", e);
		} finally {
			try {
				if (fin != null) {
					fin.close();
					fin = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try{
				this.closeConnect();
			}catch(IOException ee){
				
			}
		}
		return uploadUrl + "/" + newFileName;
	} 
	
	@Override
	public String uploadInputStream(InputStream fin,String fileName, String uploadPath)
			throws ServiceException {
		System.out.println("===========开始上传文件============");
		System.out.println("=====开始时间:" + DateUtils.getCurDate());

		String fileType = "";
		if (fileName != null) {
			fileType = fileName.substring(fileName.lastIndexOf("."));
		}

		// 得到服务器上的路径
		String timePath = DateUtils.getFilePathUseTime("/");

		// 上传后的图片对应URL
		String uploadUrl = uploadPath + timePath; 

		// 新图片名
		String newFileName = UUID.randomUUID().toString() + fileType;

		FTPClient ftpClient = null;
		try {
			ftpClient = this.getFTPClient();
			makeDirectory(uploadUrl);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				uploadUrl = null;
				fin.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			try{
				this.closeConnect();
			}catch(IOException ee){
				
			}
			throw new ServiceException(
					"uploadFile fail in FtpUploadServiceImpl", e);
		}
		try {
			ftpClient.storeFile(newFileName, fin);
			System.out
					.println("=====FTP返回CODE=====" + ftpClient.getReplyCode());
			System.out.println("=====结束时间:" + DateUtils.getCurDate());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(
					"uploadFile fail in FtpUploadServiceImpl", e);
		} finally {
			try {
				if (fin != null) {
					fin.close();
					fin = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try{
				this.closeConnect();
			}catch(IOException ee){
				
			}
		}
		return uploadUrl + "/" + newFileName;
	}
	
	/**
	 * 上传图片包括图片大小及路径(生成缩略图并将其上传到ftp)
	 */
	public void uploadZoomFile(File file, int targetWidth, int targetHeight,
			String fullPath, boolean fillBank, File watermark) throws ServiceException{
		InputStream inputStream = ImageUtils.zoomImage(file, targetWidth,
				targetHeight, fillBank, watermark);
		System.out.println();
		System.out.println("===========开始上传文件============");
		System.out.println("=====开始时间:" + DateUtils.getCurDate());
		int fileTypeIndex = fullPath.lastIndexOf("/");
		// 上传后的图片对应URL
		String uploadUrl = targetWidth + "x" + targetHeight
					+ "/"+fullPath.substring(0, fileTypeIndex);
		
		String fileName = fullPath.substring(fileTypeIndex + 1);

		FTPClient ftpClient = null;
		try {
			ftpClient = this.getFTPClient();
			makeDirectory(uploadUrl);
			ftpClient.storeFile(fileName, inputStream);
			System.out
					.println("=====FTP返回CODE=====" + ftpClient.getReplyCode());
			System.out.println("=====结束时间:" + DateUtils.getCurDate());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
					inputStream = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			try{
				this.closeConnect();
			}catch(IOException ee){
				
			}
		}
	}

	/**
	 * create directory by given path(can be recursived).<br/>
	 * DO NOT include file name.
	 * 
	 * @param path
	 * @throws IOException
	 */
	private void makeDirectory(String path) throws IOException {
		if (path == null || "".equals(path)) {
			throw new IllegalArgumentException("the path must be required.");
		}

		Pattern pattern = Pattern.compile("[\\\\//]");
		String[] directory = pattern.split(path);
		if (directory.length <= 0) {
			throw new IllegalArgumentException("the path must be required.");
		}
		String uploadDir = "";
		
		if(StringUtils.isNotEmpty(this.workingDirectory)){
			uploadDir = this.workingDirectory;
		}
		
		for (Integer i = 0; i < directory.length; i++) {
			String dir = directory[i];
			FTPClient ftpClient = this.getFTPClient();
			if ("".equals(dir) && i == 0) {
				// if path start with "/" or "\", should change to root
				ftpClient.changeWorkingDirectory("//");
			} else if ("".equals(dir)) {
				// "" in middle means do nothing.
			} else {
				uploadDir = uploadDir + "//" + dir;
				ftpClient.makeDirectory(uploadDir);
				ftpClient.changeWorkingDirectory(uploadDir);
			}
		}

	}

	public Integer getFtpConnectRetry() {
		return Integer.valueOf(this.connectRetry);
	}

	private String toString(String[] arg) {
		StringBuilder sb = new StringBuilder();
		for (String line : arg) {
			sb.append(line + "\n");
		}
		return sb.toString();
	}
	
}
