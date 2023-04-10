<template>
  <div class="dashboard-container">
    <div class="dashboard-text">
      个人博客管理系统
    </div>
    <div>
      基于<em>SpringBoot + MyBatisPlus + SpringSecurity</em> 的个人博客系统。
    </div>
    <div>
      <p> 前台部分主要分为了首页、文章分类、留言和友链页面展示，主要功能实现有用户注册与登录、文章分页查询、热门文章查询、评论的添加与查询、个人信息更改。<br/>
        后台部分主要包括文章撰写、文章内容管理、用户管理和角色管理。<br/>
        前台用户登录以及后台不同权限的用户登陆时均使用<em>SpringSecurity</em>进行认证.<br/>
        使用<em>Redis</em>实现用户登录后的个人信息缓存以及文章浏览量的统计，提高访问速度。<br/>
        使用<em>Docker</em> 作为运行环境，隔离容器，简化部署，方便转移。<br/>
        很感谢B站Up主三更老师的视频教程，本项目是在他的视频教程的基础上进行的二次开发。<br/></p>
    </div>
    <div>
      <p><strong>待完善功能: 菜单管理</strong>
        <strong> 缩略图</strong>
      </p>
    </div>
    <div>
      <p><strong>待添加功能: 评论管理</strong>
        <strong> 草稿箱</strong>
      </p>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name'
    ])
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
