package com.futao.springmvcdemo.dao;

import com.futao.springmvcdemo.model.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

/**
 * 标签
 *
 * @author futao
 * Created on 2019-03-06.
 */
@Mapper
public interface TagDao {

    /**
     * 通过文章id查询被标记的tagList
     *
     * @param articleId 文章id
     * @return 关联的标签列表
     */
    ArrayList<Tag> selectTagByArticleId(String articleId);
}
