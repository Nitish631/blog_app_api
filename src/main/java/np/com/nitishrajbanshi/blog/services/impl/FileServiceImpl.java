package np.com.nitishrajbanshi.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.stereotype.Service;
import np.com.nitishrajbanshi.blog.services.FileService;
@Service
public class FileServiceImpl implements FileService {
    
    @Override
    public String uploadingImage(String path, MultipartFile file) throws IOException {
       String name=file.getOriginalFilename();
       String randomID=UUID.randomUUID().toString();
       String fileName=randomID.concat(name.substring(name.lastIndexOf(".")));
       String filePath=path+File.separator+fileName;
       File f=new File(path);
       if(!f.exists()){
            f.mkdirs();
       }
       Files.copy(file.getInputStream(), Paths.get(filePath));
       return fileName;
    }

    @Override
    public InputStream getImage(String path, String fileName) throws FileNotFoundException {
        String fullPath=path+File.separator+fileName;
        InputStream is=new FileInputStream(fullPath);
        return is;
    }
    
}
