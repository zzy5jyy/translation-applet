package com.zzy.translation.service.impl;

import com.zzy.translation.api.MD5;
import com.zzy.translation.dao.ArticleDao;
import com.zzy.translation.entity.Article;
import com.zzy.translation.entity.User;
import com.zzy.translation.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;
    @Override
    public boolean addArticle(Article article) {
        if (article.getrLongTitle() != null && !"".equals(article.getrLongTitle()) && article.getUserId() != null && !"".equals(article.getUserId())){
            String title = article.getrLongTitle();
            String subheading = article.getrSubheading();
            StringBuilder sb = new StringBuilder();
            sb.append(title);
            sb.append(subheading);
            sb.append(new Date().getTime());
            String rId = MD5.stringMD5(sb.toString());
            article.setrId(rId);
            SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
            String creatTime = df.format(new Date());
            String lastEditTime = df.format(new Date());
            article.setCreateTime(creatTime);
            article.setLastEditTime(lastEditTime);
            try {
                int effectedNum = articleDao.insertArticle(article);
                if (effectedNum > 0){
                    return true;
                }else {
                    throw new RuntimeException("插入文章信息失败！");
                }
            } catch (Exception e) {
                throw new RuntimeException("插入文章信息失败！" + e.getMessage());
            }
        }else {
            throw new RuntimeException("文章标题和用户Id不能为空");
        }

    }

    @Override
    public List<Article> queryArticle() {
        List<Article> listArticle = articleDao.queryArticle();
        for (int i = 0; i < listArticle.size(); i ++){
            if (listArticle.get(i).getrPublish() == 1){
                listArticle.get(i).setPublishInfo("已发布");
            }else if (listArticle.get(i).getrPublish() == 0){
                listArticle.get(i).setPublishInfo("未发布");
            }
        }
        return listArticle;
    }

    @Override
    public Article queryArticleByArticleId(String rId) {
        return articleDao.queryArticleByArticleId(rId);
    }

    @Override
    public boolean modifyArticleByStatus(Article article) {
        if (article.getrId() != null && !"".equals(article.getrId()) && article.getUserId() != null && !"".equals(article.getUserId())){
            SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
            article.setLastEditTime(df.format(new Date()));
            try {
                int effectedNum = articleDao.updateArticleByStatus(article);
                if (effectedNum > 0){
                    return true;
                }else {
                    throw new RuntimeException("删除文章信息失败！");
                }
            } catch (Exception e) {
                throw new RuntimeException("删除文章信息失败！" + e.getMessage());
            }
        }else {
            throw new RuntimeException("文章Rid不能为空！");
        }
    }

    @Override
    public boolean modifyArticle(Article article) {
        if (article.getrId() != null && !"".equals(article.getrId()) && article.getUserId() != null && !"".equals(article.getUserId())){
            SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
            article.setLastEditTime(df.format(new Date()));
            try {
                int effectedNum = articleDao.updateArticle(article);
                if (effectedNum > 0){
                    return true;
                }else {
                    throw new RuntimeException("更新文章失败！");
                }
            } catch (Exception e) {
                throw new RuntimeException("更新文章失败！" + e.getMessage());
            }
        }else {
            throw new RuntimeException("新闻的rid不能为空！");
        }

    }

    @Override
    public List<Article> queryArticleByUserId(User user) {
        List<Article> listArticle = articleDao.queryArticleByUserId(user);
        for (int i = 0; i < listArticle.size(); i ++){
            if (listArticle.get(i).getrPublish() == 1){
                listArticle.get(i).setPublishInfo("已发布");
            }else if (listArticle.get(i).getrPublish() == 0){
                listArticle.get(i).setPublishInfo("未发布");
            }
        }
        return listArticle;

    }

    @Override
    public boolean checkPower(User user) {
        if (user.getPower() != null && !"".equals(user.getPower())){
            if (user.getPower() == 1){
                throw new RuntimeException("你是普通用户，不能修改新闻！");
            }else {
                return true;
            }
        }else {
            throw new RuntimeException("用户权限为空！");
        }
    }
}
