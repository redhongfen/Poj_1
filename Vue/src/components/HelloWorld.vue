<template>
  <div>
    <h1>测试从后端读取数据</h1>
    <!-- 显示返回的数据 -->
    <p>状态码：{{ apiResponse.code }}</p>
    <p>消息：{{ apiResponse.msg }}</p>
    <p>数据：{{ apiResponse.data }}</p>
  </div>
</template>
<script>
import axios from 'axios';

export default {
  data() {
    return {
      apiResponse: {
        code: '',
        msg: '',
        data: ''
      }
    };
  },
  created() {
    this.fetchData(); // 组件创建时调用函数请求数据
  },
  methods: {
  async fetchData() {
    try {
      const response = await axios.get('http://localhost:8081/');  // 确认使用的是正确的URL
      this.apiResponse = response.data;
    } catch (error) {
      console.error('请求数据失败：', error);
    }
  }
  } 

};
</script>
