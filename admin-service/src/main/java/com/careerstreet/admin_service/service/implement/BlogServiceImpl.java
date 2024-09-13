package com.careerstreet.admin_service.service.implement;


import com.careerstreet.admin_service.dto.BlogRequest;
import com.careerstreet.admin_service.dto.BlogResponse;
import com.careerstreet.admin_service.entity.Admin;
import com.careerstreet.admin_service.entity.Blog;
import com.careerstreet.admin_service.exception.EntityNotFoundException;
import com.careerstreet.admin_service.exception.GlobalCode;
import com.careerstreet.admin_service.repository.AdminRepository;
import com.careerstreet.admin_service.repository.BlogRepository;
import com.careerstreet.admin_service.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final AdminRepository adminRepository;
    private final ModelMapper modelMapper;

    @Override
    public BlogResponse createBlog(BlogRequest blogRequest){
        Admin admin = adminRepository.findById(blogRequest.getAdmin_id())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        Blog blog = modelMapper.map(blogRequest, Blog.class);

        blog.setAdmin(admin);

        blog = blogRepository.save(blog);

        BlogResponse blogResponse = modelMapper.map(blog, BlogResponse.class);

        blogResponse.setAdmin_id(admin.getAdminId());

        return blogResponse;
    }

    @Override
    public BlogResponse updateBlog(BlogRequest blogRequest, Long blogId){
        Blog blog = blogRepository.findById(blogId).orElseThrow(
                () -> new EntityNotFoundException("Blog not found with id: " + blogId, GlobalCode.ERROR_ENTITY_NOT_FOUND)
        );

        modelMapper.map(blogRequest, blog);

        blog = blogRepository.save(blog);

        BlogResponse blogResponse = modelMapper.map(blog, BlogResponse.class);

        return blogResponse;
    }
}
