package com.hdq.blog_3.service;

import com.hdq.blog_3.po.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTagTop(Integer size);
    //通过字符串id查询多个tag---value=”1，2，3，4“
    List<Tag> listTag(String ids);

    Tag updateTag(Long id, Tag tag);

    void deleteTag(Long id);
}
