package com.farst.upload.controller;

import java.io.File;
import java.io.IOException; 
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType; 
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.farst.common.exception.ServiceException;
import com.farst.common.utils.ImageUtils;
import com.farst.common.web.controller.BasicController;
import com.farst.common.web.response.RestResponse;
import com.farst.ftp.service.FtpUploadService; 

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(tags = {"上传文件"})
@RestController
@RequestMapping("/upload")
public class UploadController extends BasicController {

	private static Logger logger = LoggerFactory
			.getLogger(UploadController.class);
	
	@Autowired
	private FtpUploadService ftpUploadService;

	@Value("${spring.ftp.tempFilePath}")
	public String tempPath;

	private static final String AVATAR_PIC_PATH = "avatarPic"; 
	
	private static final String CLOCKIN_LOG_PIC_PATH = "clockinLogPic";

	/**
	 * 最大不超过10兆
	 */
	private static final long fileMaxSize = 10*1024*1024;
	/**
	 * 最大不超过5兆
	 */
	private static final long fileMaxAvatarSize = 5*1024*1024;

	/**
	 * 将上传的文件存入临时文件中
	 * 
	 * @param fileData
	 *            上传的文件
	 * @param fileName
	 *            新文件名
	 * @return
	 * @throws ServiceException
	 */
	private File storeTempFile(MultipartFile fileData) throws Exception {
		String tempPathName = tempPath + File.separator
				+ fileData.getOriginalFilename();
		File filePath = new File(tempPath);
		if (filePath.exists() == false) {
			boolean result_mkdirs = filePath.mkdirs();
			if (result_mkdirs == false) {
				throw new Exception("mkdir temp dir fail");
			}
		}
		File fileTemp = new File(tempPathName);
		fileData.transferTo(fileTemp);
		return fileTemp;
	}

	/**
	 * 上传头像图片
	 **/
	@ApiOperation(httpMethod = "POST", value = "上传头像", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/uploadAvatarPic")
	public RestResponse<String> uploadAvatarPic(MultipartFile file) {
		RestResponse<String> reponse = new RestResponse<>();
		File nfile = null;
		try {
			nfile = this.storeTempFile(file);
			
			long size = file.getSize();
			if(size>fileMaxAvatarSize){
				reponse.setErrorMsg("单张图片最大不超过5兆");
				return reponse;
			}
			
			String filePath = this.ftpUploadService.uploadFile(nfile,
					AVATAR_PIC_PATH);

			// 生成缩略图
			this.uploadZoomFileOfAvatarRelated(nfile, filePath);

			reponse.setSuccess(filePath);
		    return reponse;
		} catch (Exception e) {
			logger.error(e.getMessage());
			reponse.setErrorMsg(e.getMessage());
			return reponse;
		} finally {
			if (nfile != null) {
				nfile.delete();
				nfile = null;
			}
		}
	} 
	

	/**
	 * 上传打卡日志图片
	 **/
	@ApiOperation(httpMethod = "POST", value = "上传打卡日志图片", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/uploadClockinLogPic")
	public RestResponse<String> uploadClockinLogPic(MultipartFile file) {
		RestResponse<String> reponse = new RestResponse<>();
		File nfile = null;
		try {
			nfile = this.storeTempFile(file);
			
			long size = file.getSize();
			if(size>fileMaxSize){
				reponse.setErrorMsg("单张图片最大不超过10兆");
				return reponse;
			}
			
			String filePath = this.ftpUploadService.uploadFile(nfile,
					CLOCKIN_LOG_PIC_PATH);

			// 生成缩略图
			this.uploadZoomFileOfClockinLogRelated(nfile, filePath);

			reponse.setSuccess(filePath);
		    return reponse;
		} catch (Exception e) {
			logger.error(e.getMessage());
			reponse.setErrorMsg(e.getMessage());
			return reponse;
		} finally {
			if (nfile != null) {
				nfile.delete();
				nfile = null;
			}
		}
	} 
	 
	
	/**
	 * 头像
	 * @param file
	 * @param uploadPath
	 * @throws ServiceException
	 */
	private void uploadZoomFileOfAvatarRelated(File file, String uploadPath)
			throws ServiceException {
		this.toCutZoomImage(file, uploadPath, 50, 50);
	}

	/**
	 * 打卡日志图片
	 * @param file
	 * @param uploadPath
	 * @throws ServiceException
	 */
	private void uploadZoomFileOfClockinLogRelated(File file, String uploadPath)
			throws ServiceException {
		this.toCutZoomImage(file, uploadPath, 100, 100);
	}

	private void toCutZoomImage(File file, String uploadPath, int targetWidth,
			int targetHeight) throws ServiceException {
		String outFile = this.tempPath + File.separator + UUID.randomUUID()
				+ uploadPath.substring(uploadPath.lastIndexOf("."));
		try {
			ImageUtils.cut(file, outFile, targetWidth, targetHeight);
		} catch (IOException e) {
			throw new ServiceException(e);
		}

		File f = new File(outFile);
		try {
			this.ftpUploadService.uploadZoomFile(f, targetWidth, targetHeight,
					uploadPath, false, null);
		} catch (ServiceException e) {
			throw e;
		} finally {
			if (f != null) {
				f.delete();
				f = null;
			}
		}
	}
	
}
