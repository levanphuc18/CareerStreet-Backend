package com.careerstreet.file_service.service.implement;
import com.careerstreet.file_service.service.FileService;
import com.cloudinary.Cloudinary;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final Cloudinary cloudinary;
    private final RestTemplate restTemplate;

    @Override
    public Map<String, Object> uploadFile(MultipartFile file) {
        try {
            // Tạo một bản đồ để chứa các tham số
            Map<String, Object> params = new HashMap<>();

            // Tạo một UUID ngẫu nhiên cho display name
            String randomDisplayName = UUID.randomUUID().toString();

            // Kiểm tra loại file
            if (file.getContentType().equals("application/pdf")) {
                // Nếu là file PDF, đặt resource_type là "raw" và folder là "pdf"
                params.put("resource_type", "raw");
                String publicId = file.getOriginalFilename().replaceAll("\\.pdf$", "") + "_" + randomDisplayName; // Đặt public_id
                params.put("public_id", publicId); // Đặt public_id
                params.put("display_name", randomDisplayName); // Đặt display name
                params.put("folder", "cvs"); // Chỉ định thư mục cho file PDF
            } else {
                // Mặc định, đặt resource_type là "image" và folder là "images"
                params.put("resource_type", "image");
                String publicId = file.getOriginalFilename().replaceAll("\\.(jpg|jpeg|png)$", "") + "_" + randomDisplayName; // Đặt public_id
                params.put("public_id", publicId); // Đặt public_id
                params.put("display_name", randomDisplayName); // Đặt display name
                params.put("folder", "images"); // Chỉ định thư mục cho file hình ảnh
            }

            // Tải lên file và lưu kết quả vào bản đồ data
            Map<String, Object> data = this.cloudinary.uploader().upload(file.getBytes(), params);

            // Kiểm tra xem định dạng có hợp lệ không
            if ("N/A".equals(data.get("format"))) {
                // Nếu định dạng là N/A, bạn có thể xử lý lại hoặc chuyển đổi
                throw new RuntimeException("File format is not valid. Try uploading as PDF.");
            }

            // Thêm tên file vào kết quả
            data.put("fileName", data.get("original_filename")); // Lưu tên file vào bản đồ kết quả
            System.out.println(data);
            // Trả về kết quả
            return data;
        } catch (IOException io) {
            // Xử lý lỗi nếu việc tải lên gặp sự cố
            throw new RuntimeException("File upload failed", io);
        } catch (RuntimeException e) {
            // Xử lý lỗi định dạng file
            throw new RuntimeException("Error processing the uploaded file: " + e.getMessage(), e);
        }
    }


    @Override
    public void downloadFile(String publicId, HttpServletResponse response) {
        String publicIdCvs = "cvs/" +publicId;
        String publicIdImages = "images/" +publicId;
        try {
            // Tạo URL tải file từ Cloudinary cho file PDF
            String rawUrl = cloudinary.url()
                    .resourceType("raw")
                    .publicId(publicIdCvs) // Sử dụng publicId đã chỉnh sửa
                    .generate();
            System.out.println(publicIdCvs + " cvs");

            // Tạo URL để xem hình ảnh
            String imageUrl = cloudinary.url()
                    .resourceType("image")
                    .publicId(publicIdImages) // Sử dụng publicId đã chỉnh sửa
                    .generate();
            System.out.println(publicIdImages + " images");

            // Log URL để kiểm tra
            System.out.println("Raw URL: " + rawUrl);
            System.out.println("Image URL: " + imageUrl);

            // Gửi yêu cầu GET để tải file
            byte[] fileBytes = restTemplate.getForObject(rawUrl, byte[].class);

            // Kiểm tra loại file
            if (publicId.endsWith(".jpg") || publicId.endsWith(".png") || publicId.endsWith(".jpeg")) {
                // Nếu file là hình ảnh, gửi trực tiếp hình ảnh về cho trình duyệt
                byte[] imageBytes = restTemplate.getForObject(imageUrl, byte[].class);
                if (imageBytes != null) {
                    response.setContentType("image/jpg"); // Hoặc "image/png", tùy theo loại hình ảnh
                    response.setHeader("Content-Disposition", "inline; filename=\"" + publicId + "\"");

                    // Ghi hình ảnh vào OutputStream
                    try (OutputStream os = response.getOutputStream()) {
                        os.write(imageBytes);
                        os.flush();
                    }
                } else {
                    throw new RuntimeException("Hình ảnh không tìm thấy hoặc rỗng.");
                }
            } else if (fileBytes != null) {
                // Nếu file không phải hình ảnh, cho phép tải về
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=\"" + publicId + ".pdf\"");

                // Ghi file vào OutputStream
                try (OutputStream os = response.getOutputStream()) {
                    os.write(fileBytes);
                    os.flush();
                }
            } else {
                throw new RuntimeException("File not found or is empty.");
            }
        } catch (HttpClientErrorException e) {
            // Xử lý lỗi 404 Not Found
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            try {
                response.getWriter().write("File không tìm thấy trên server: " + e.getMessage());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while downloading file: " + e.getMessage(), e);
        }
    }

}
