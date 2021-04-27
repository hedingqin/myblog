package com.hdq.blog_3.web.admin;

import com.hdq.blog_3.po.Blog;
import com.hdq.blog_3.po.User;
import com.hdq.blog_3.service.BlogService;
import com.hdq.blog_3.service.TagService;
import com.hdq.blog_3.service.TypeService;
import com.hdq.blog_3.util.UploadImageUtils;
import com.hdq.blog_3.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT="admin/blogs-input";
    private static final String LIST="admin/blogs";
    private static final String REDIRECT_LIST="redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    //跳转到博客列表页面
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 2 , sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return LIST;
    }

    //点击上一页下一页时，返回博客列表片段，进行动态局部渲染，只更新网页的某一片段
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2 , sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model){
        System.out.println(blog.isRecommend());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs :: blogList";
    }

    //跳转到博客新增页面
    @GetMapping("/blogs/input")
    public String Input(Model model){
        setTypeAndTag(model);
        model.addAttribute("blog",new Blog());
        return INPUT;
    }

    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
    }
    //修改
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;
    }

    //新增 or 修改
    @PostMapping("/blogs")
    public String post(@RequestParam("picture") MultipartFile picture, Blog blog, RedirectAttributes attributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        //1.修改：picture为空，则不改变FirstPicture，否则改变FirstPicture。
        //2.新增：直接添加图片文件
        Blog b;
        if(blog.getId() == null){
            blog.setFirstPicture(UploadImageUtils.upload(picture));
            b=blogService.saveBlog(blog);
        }else{
//            isEmpty()与null的区别：前者表示内容是否为空，后者表示对象是否实例化，在这里前端发送请求到后端时，就实例化了picture对象
            if(picture.isEmpty()){
                blog.setFirstPicture(blogService.getBlog(blog.getId()).getFirstPicture());
            }else {
                blog.setFirstPicture(UploadImageUtils.upload(picture));
            }
            b=blogService.updateBlog(blog.getId(),blog);
        }
        if(b == null){
            attributes.addFlashAttribute("message","操作失败!");
        }else{
            attributes.addFlashAttribute("message","操作成功!");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return REDIRECT_LIST;
    }
}
