package com.lambertwu.fastdfs;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

public class TestFastDFS {

	@Test
	public void uploadFile() throws Exception {
		ClientGlobal.init("D:/Developer/Repos/Taotao/taotao-manager-web/src/main/resources/resource/client.conf");
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getConnection();
		StorageServer storageServer = null;
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		String[] upload_file = storageClient.upload_file("D:/banma.png", "png", null);
		
		for (String string : upload_file) {
			System.out.println(string);
		}
		
	}
}
