import router from '@/router';
import axios from 'axios'

// 创建可一个新的axios对象
const request = axios.create({
    baseURL: process.env.VUE_APP_BASEURL,   // 后端的接口地址  ip:port
    timeout: 300000
})
// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    let user = JSON.parse(localStorage.getItem("honey-user")) ||{}
    config.headers['token'] = user.token  // 设置请求头

    return config
}, error => {
    console.error('request error: ' + error) // for debug
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;

        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        if(res.code === '401'){
            router.push('/login')
        }
        return res;
    },
    error => {
        if (error.response && error.response.status === 401) {
            console.error('Unauthorized, redirecting to login...');
            router.push('/login');
            return Promise.resolve({}); 
        }else{
            console.error('response error: ' + error)
        } // for debug
        return Promise.reject(error)
    }
)


export default request