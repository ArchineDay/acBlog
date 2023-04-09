<!-- 文章列表 -->
<template>
<div>
	<ac-nav></ac-nav>
	<div class="container">
		<el-row :gutter="30">
			<el-col :sm="24" :md="16" style="transition:all .5s ease-out;margin-bottom:30px;">
				<ac-articlelist></ac-articlelist>
			</el-col>
			<el-col :sm="24" :md="8">
				<ac-rightlist></ac-rightlist>
			</el-col>
		</el-row>
	</div>
</div>
</template>

<script>
import header from '../components/header.vue'
import articlelist from '../components/articlelist.vue'
import rightlist from '../components/rightlist.vue'
export default {
	name: "Share",
	data() { //选项 / 数据
		return {

		}
	},
	methods: { //事件处理器

	},
	components: { //定义组件
		'ac-nav': header,
		'ac-articlelist': articlelist,
		'ac-rightlist': rightlist,
	},

	created() { //生命周期函数

	}
}
</script>

<style>

</style>
