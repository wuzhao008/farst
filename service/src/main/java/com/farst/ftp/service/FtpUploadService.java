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
package com.farst.ftp.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import com.farst.common.exception.ServiceException; 

/**
 * FTP操作接口
 * 
 * @author MichaelWoo
 * @version $Id: IFtpUploadService.java 2018-10-14 下午8:43:46 $
 */
public interface FtpUploadService {

	// public void initFtpProperties();

	public FTPClient getFTPClient() throws IOException;

	public FTPClient connectServer() throws IOException;

	public void closeConnect() throws IOException;

	/**
	 * 上传图片
	 * 
	 * @param file
	 * @param uploadPath
	 *            路径,类似于 product review brand等
	 * @return
	 * @throws ServiceException
	 */
	public String uploadFile(File file, String uploadPath)
			throws ServiceException;
	
	public String uploadInputStream(InputStream fin,String fileName, String uploadPath)
			throws ServiceException;

	/**
	 * 针对缩略图上传到ftp
	 * 
	 * @param file
	 *            缩略图的源文件
	 * @param targetWidth
	 *            缩略图的宽度
	 * @param targetHeight
	 *            缩略图的高度
	 * @param fullPath
	 *            被裁减的原图片全路径
	 * @param fillBank
	 *            是否填充白色
	 * @param watermark
	 *            水印图文件
	 * @return
	 * @throws ServiceException
	 */
	public void uploadZoomFile(File file, int targetWidth, int targetHeight,
			String fullPath, boolean fillBank, File watermark) throws ServiceException;
	
}
