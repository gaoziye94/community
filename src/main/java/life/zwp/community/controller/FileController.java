package life.zwp.community.controller;

import life.zwp.community.dto.FileDTO;
import life.zwp.community.provider.UCloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 文件上传 controller
 */
@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private UCloudProvider uCloudProvider;

    @RequestMapping("/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request){
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartHttpServletRequest.getFile("editormd-image-file");
        FileDTO fileDTO;
        try {
            String fileUrl = uCloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
            fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setMessage("成功");
            fileDTO.setUrl(fileUrl);
            return fileDTO;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
