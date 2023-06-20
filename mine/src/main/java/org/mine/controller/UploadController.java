package org.mine.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.mine.domain.AttachFileDTO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		
		String str = sdf.format(date);
		
		return str.replace("-", File.separator);
	}
	
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			return contentType.startsWith("image");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName) {
	    Resource resource = new FileSystemResource("C:\\upload\\" + fileName);
	    log.info("resource: " + resource);

	    if (!resource.exists()) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }

	    String resourceName = resource.getFilename();
	    HttpHeaders headers = new HttpHeaders();

	    try {
	        String downloadName = null;

	        if (userAgent.contains("Trident")) {
	            log.info("IE browser");
	            downloadName = URLEncoder.encode(resourceName, "UTF-8").replaceAll("\\+", " ");
	        } else if (userAgent.contains("Edg")) {
	            log.info("Edge browser");
	            downloadName = URLEncoder.encode(resourceName, "UTF-8");
	            log.info("Edge name: " + downloadName);
	        } else {
	            log.info("Chrome");
	            downloadName = new String(resourceName.getBytes("utf-8"), "ISO-8859-1");
	        }

	        headers.add("Content-Disposition", "attachment; filename=" + downloadName);
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }

	    return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}

	
	
	@GetMapping("/uploadForm")
	public void uploadForm() {
		log.info("upload");
	}
	
	@PostMapping("/uploadFormAction")
	@ResponseBody
	public void uploadFormAction(MultipartFile[] uploadFile, Model model) {
		String uploadFolder = "C:\\upload";
		log.info("mapping");
		for (MultipartFile multipartfile :uploadFile) {
			log.info("================================");
			log.info("Upload File Name" + multipartfile.getOriginalFilename());
			log.info("Upload File Size " + multipartfile.getSize());
			
			File saveFile = new File(uploadFolder, multipartfile.getOriginalFilename());
			try {
				multipartfile.transferTo(saveFile);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){
		log.info("fileName : " + fileName);
		File file  = new File("C:\\upload\\" + fileName);
		
		log.info("file : " + file);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type){
		log.info("deleteFile" + fileName);
		
		File file;
		try {
			file = new File("c:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));
			
			file.delete();
			
			if(type.equals("image")) {
				String largeFileName = file.getAbsolutePath().replace("s_","");
				
				log.info("largeFileName : " + largeFileName);
				
				file = new File(largeFileName);
				
				file.delete();
			}
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
	
	@GetMapping("uploadAjax")
	public void uploadAjax() {
		log.info("upload Ajax");
	}
	
	@PostMapping(value="/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxAction(MultipartFile[] uploadFile) {
		
		List<AttachFileDTO> list = new ArrayList<>();
		String uploadFolder = "C:\\upload";
		
		// make folder
		String uploadFolderPath = getFolder();
		
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		log.info("upload path : " + uploadPath);
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdir();
		}
		
		// make yyyy/mm/dd
		
		for (MultipartFile multipartFile : uploadFile) {
			log.info("=========================");
			log.info("UploadFile Name: " + multipartFile.getOriginalFilename());
			log.info("UploadFile Size: " + multipartFile.getSize());

			AttachFileDTO attFileDTO = new AttachFileDTO();
			
			String uploadFileName = multipartFile.getOriginalFilename();
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
			
			log.info("Only file name : " + uploadFileName);
			attFileDTO.setFileName(uploadFileName);
			
			
			UUID uuid = UUID.randomUUID();
			
			uploadFileName = uuid.toString() + "_" + uploadFileName;
		
			
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
		
				attFileDTO.setUuid(uuid.toString());
				attFileDTO.setUploadPath(uploadFolderPath);
				
				// check image type file
				if(checkImageType(saveFile)) {
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100,100);
					
					thumbnail.close();
					attFileDTO.setImage(true);
				}
				
				list.add(attFileDTO);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
