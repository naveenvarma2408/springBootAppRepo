package com.siemens.camerapoc.main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.siemens.camerapoc.beans.CameraImageBean;
import com.siemens.camerapoc.service.impl.CameraPOCServiceImpl;


@RestController
public class CameraPOCController {
	private static final Logger logger = LoggerFactory.getLogger(CameraPOCController.class);
	private @Autowired CameraPOCServiceImpl cameraPOCServiceImpl;

	@Value("${pid.path}")
	private static String pidPath;
	
	@GetMapping("/CameraPOC/Images")
	public ResponseEntity<List<CameraImageBean>> getAllImages(
			@RequestParam(value = "count", defaultValue = "25") int count) {
		logger.info("count is " + count);
		logger.info("the value is "+pidPath);
		HttpHeaders header = new HttpHeaders();
		header.setAccessControlAllowOrigin("*");
		List<CameraImageBean> images = new ArrayList<CameraImageBean>();
		try {
			images = cameraPOCServiceImpl.getAllImagesFromDB(count);
			logger.debug("the number of results -- "+images.size());
		} catch (SQLException e) {
			logger.error("error while getting images list " + e);
			return  ResponseEntity.badRequest().headers(header).body(images);
		}
		;
		return  ResponseEntity.ok().headers(header).body(images);
		//return  ResponseEntity.ok().header("Access-Control-Allow-Origin", "*").body(images);
	}

	@PostMapping("/CameraPOC/addImages")
	public ResponseEntity<String> insertImage(@RequestBody List<CameraImageBean> images) {
		HttpHeaders header = new HttpHeaders();
		header.setAccessControlAllowOrigin("*");
		try {
			logger.info("inside post call" + images);
			cameraPOCServiceImpl.insertImageIntoDB(images);
			 return ResponseEntity.ok().headers(header)
				      .body("Data has been successfully stored in server");
		} catch (Exception e) {
			logger.error("error while adding the images info to DB " + e);
			return  ResponseEntity.badRequest().headers(header).body("Unable to add info. Please try again later.");
		}
	}

}
