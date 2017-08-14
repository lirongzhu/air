package com.wing.utils.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.wing.common.constant.Constant;

import net.coobird.thumbnailator.Thumbnails;

public class FileUploadUtil {

    public static File uploadImage(MultipartRequest request) throws IllegalStateException, IOException {

    	File file = null;
    	
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfss = new SimpleDateFormat("yyyyMMddHHmmsss");

        Map<String, MultipartFile> files = request.getFileMap();
        Iterator<Map.Entry<String, MultipartFile>> it = files.entrySet().iterator();

        while(it.hasNext()){

            MultipartFile mf = it.next().getValue();
            
            if(mf.getSize() > 0){
                String path = ((HttpServletRequest)request).getServletContext().getRealPath(Constant.IMAGE_UPLOAD_FOLDER) 
                		+ System.getProperty("file.separator")
                		+ sdf.format(new Date()) + System.getProperty("file.separator") ;

                String timeStamp = sdfss.format(new Date());
                file = new File(path, timeStamp + Constant.createRandom(true, 6) + "." + mf.getOriginalFilename().split("\\.")[mf.getOriginalFilename().split("\\.").length - 1]);

                if(!file.exists()){
                    file.mkdirs();
                }
                
                mf.transferTo(file);
            }
        }

        createThumbnailImage((HttpServletRequest)request, file);
        
        return file;
    }
    
    public static File createThumbnailImage(HttpServletRequest request, File file) throws IOException{

    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        
    	String path = (request).getServletContext().getRealPath(Constant.THUMBNAIL_IMAGE_UPLOAD_FOLDER) 
        		+ System.getProperty("file.separator")
        		+ sdf.format(new Date()) + System.getProperty("file.separator") ;
    	
    	File thumbnailDir = new File(path);
    	
    	if(!thumbnailDir.exists()){
    		thumbnailDir.mkdirs();
    	}
    	
    	File thumbnailFile = new File(path, file.getName());
    	thumbnailFile.createNewFile();

        Thumbnails.of(file).size(200, 300).toFile(thumbnailFile);

        return thumbnailFile;
    }
}
