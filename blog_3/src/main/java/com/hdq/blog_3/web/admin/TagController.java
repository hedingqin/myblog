package com.hdq.blog_3.web.admin;

import com.hdq.blog_3.po.Tag;
import com.hdq.blog_3.po.Type;
import com.hdq.blog_3.service.TagService;
import com.hdq.blog_3.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    //分页
//    @PageableDefault可以设置分页对象的一些参数，此处查询10条数据，根据id进行倒序排序
    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }

    //新增
    //RedirectAttributes用于重定向页面存储键值对数据
    @PostMapping("/tags")
    public String post(Tag tag, BindingResult result, RedirectAttributes attributes,Model model){
        Tag tag1 = tagService.getTagByName(tag.getName());
        System.out.print("type1:"+tag1);
        if(tag1 != null){
//            result.rejectValue("name","nameError","不能添加重复的分类");
            attributes.addFlashAttribute("error","不能添加重复的类");
            return "redirect:/admin/tags";
        }
        Tag t = tagService.saveTag(tag);
        if(t == null){
            attributes.addFlashAttribute("message","新增失败!");
        }else{
            attributes.addFlashAttribute("message","新增成功!");
        }
//        redirect:/admin/types相当于给了一个新的地址，让springboot按这个地址去重新请求
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String editPost(Tag tag, BindingResult result,@PathVariable Long id, RedirectAttributes attributes,Model model){
        Tag tag1 = tagService.getTagByName(tag.getName());
        System.out.print("tag1:"+tag1);
        if(tag1 != null){
//            result.rejectValue("name","nameError","不能添加重复的分类");
            attributes.addFlashAttribute("error","不能添加重复的标签");
            return "redirect:/admin/tags";
        }
        Tag t = tagService.updateTag(id,tag);
        if(t == null){
            attributes.addFlashAttribute("message","更新失败!");
        }else{
            attributes.addFlashAttribute("message","更新成功!");
        }
//        redirect:/admin/types相当于给了一个新的地址，让springboot按这个地址去重新请求
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功!");
        return "redirect:/admin/tags";
    }
}
