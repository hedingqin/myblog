package com.hdq.blog_3.dao;

import com.hdq.blog_3.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
//除了可以直接使用Spring Data JPA接口提供的基础功能外，
// Spring Data JPA还允许开发者自定义查询方法，
// 对于符合以下命名规则的方法，Spring Data JPA能够根据其方法名为其自动生成SQL，
// 除了使用示例中的 find 关键字，
// 还支持的关键字有：query、get、read、count、delete等。 
public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {

    //查询推荐的文章
    @Query("select b from Blog b where b.recommend = true ")
    List<Blog> findTop(Pageable pageable);

//    ?1表示传入第一个参数
    //正常的sql语句----select * from t_blog where title like '%内容%'
    //此处的jpa查询并不会去处理query的值，需要我们手动去处理，将query处理成"%"+query+"%"，数据库才会去做模糊查询
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query,Pageable pageable);

    //update操作不仅要使用query，还要使用modifying
    @Transactional
    @Modifying
    @Query("update Blog b set b.views = b.views+1 where b.id = ?1")
    int updateViews(Long id);

    //查询blog中都有哪些年份
    @Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b.updateTime,'%Y') order by year desc")
    List<String> findGroupYear();

    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1")
    List<Blog> findByYear(String year);
}
