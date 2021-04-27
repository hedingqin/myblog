package com.hdq.blog_3.web;

import com.hdq.blog_3.po.Type;
import com.hdq.blog_3.service.BlogService;
import com.hdq.blog_3.service.TypeService;
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

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    private Long id;//用于分页的类型id

    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 2, sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model){
        //查询所有的type
        List<Type> types = typeService.listTypeTop(10000);
        if(id == -1){
            id = types.get(0).getId();

        }
        this.id = id;
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types",types);
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        model.addAttribute("activeTypeId",id);
        return "types";
    }

    @GetMapping("/types")
    public String types(@PageableDefault(size = 2, sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         Model model){
        //查询所有的type
        List<Type> types = typeService.listTypeTop(10000);
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types",types);
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
