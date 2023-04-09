<!-- 友情链接 -->
<template>
    <div>
        <ac-nav></ac-nav>
        <div class="container">
            <el-row  :gutter="30">
                <el-col :sm="24" :md="16" style="transition:all .5s ease-out;margin-bottom:30px;">
                    <ac-friends></ac-friends>
                    <ac-message></ac-message>
                </el-col>
                <el-col :sm="24"  :md="8" >
                    <ac-rightlist></ac-rightlist>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
import header from '../components/header.vue'
import friends from '../components/friends.vue'
import rightlist from '../components/rightlist.vue'
import message from '../components/message.vue'
    export default {
        name: 'FriendsLink',
        data() { //选项 / 数据
            return {

            }
        },
        methods: { //事件处理器

        },
        components: { //定义组件
            'ac-nav':header,
            'ac-message':message,
            'ac-friends':friends,
            'ac-rightlist':rightlist,
        },
        created() { //生命周期函数

        }
    }
</script>

<style>

</style>
