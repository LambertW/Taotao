package com.lambertwu.controller;

import java.util.HashMap;
import java.util.Map;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传
 * 
 * @author wgq19
 *
 */
@Controller
public class PictureController {
	
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;

	@RequestMapping("/pic/upload")
	@ResponseBody
	public Map<Object, Object> picUpload(MultipartFile uploadFile) {
		try {
			// 接收上传的文件

			// 获取扩展名
			String originalFilename = uploadFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

			// 上传到图片服务器
			ClientGlobal.init("resource/client.conf");
			TrackerClient tracker = new TrackerClient();
			TrackerServer trackerServer = tracker.getConnection();
			StorageServer storageServer = null;
			StorageClient client = new StorageClient(trackerServer, storageServer);

			NameValuePair[] metaList = new NameValuePair[1];
			metaList[0] = new NameValuePair("fileName", originalFilename);
			String[] upload_file = client.upload_file(uploadFile.getBytes(), extName, metaList);
			System.out.println("IMAGE_SERVER_URL:" + IMAGE_SERVER_URL);
			// group1 + relative_path
			String url = IMAGE_SERVER_URL  + upload_file[0] + "/" + upload_file[1];
			
			// 响应上传图片的Url
			Map<Object, Object> result= new HashMap<>();
			result.put("error", 0);
			result.put("url", url);
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			
			Map<Object, Object> result= new HashMap<>();
			result.put("error", 1);
			result.put("message", "图片上传失败");
			
			return result;
		}
	}
}
