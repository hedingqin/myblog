package com.hdq.blog_3.web;

import com.hdq.blog_3.po.Tag;
import com.hdq.blog_3.service.BlogService;
import com.hdq.blog_3.service.TagService;
import com.hdq.blog_3.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
//ctrl + R 替换字符串
@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    private Long id;//用于分页的标签id

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 2, sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model){
        //查询所有的tag
        List<Tag> tags = tagService.listTagTop(10000);
        if(id == -1){
            id = tags.get(0).getId();

        }
        this.id = id;
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlog(id,pageable));
        model.addAttribute("activeTagId",id);
        return "tags";
    }

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 2, sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                       Model model){
        //查询所有的tag
        List<Tag> tags = tagService.listTagTop(10000);
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlog(id,pageable));
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}
