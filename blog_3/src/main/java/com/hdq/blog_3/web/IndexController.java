package com.hdq.blog_3.web;


import com.hdq.blog_3.NotFoundException;
import com.hdq.blog_3.service.BlogService;
import com.hdq.blog_3.service.TagService;
import com.hdq.blog_3.service.TypeService;
import com.hdq.blog_3.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    //首页
    @GetMapping("/")
    public String index(@PageableDefault(size = 5, sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(5));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(3));
        return "index";
    }

    private String query; //查询操作传入的字符串
//    @RequestParam  获取表单参数
    @PostMapping("/search")
    public String search(@PageableDefault(size = 3, sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model){
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        this.query=query;
            return "search";
    }
    // 查询结果的分页操作
    @GetMapping("/search")
    public String search(@PageableDefault(size = 3, sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                          Model model){
        model.addAttribute("page",blogService.listBlog("%"+this.query+"%",pageable));
        model.addAttribute("query",this.query);
        return "search";
    }

    //博客详情页
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }

    //footer部分的最新博客部分，进行动态更新
    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }
}
