import axios from 'axios'
import ElementUI from "element-ui";
import {serverIp} from "../../public/config";
//通过axios实现后端数据传输到前端的功能
const request = axios.create({
    baseURL: 'http://124.71.166.37:9090',  // 注意！！ 这里是全局统一加上了 '/api' 前缀，也就是说所有接口都会加上'/api'前缀在，页面里面写接口的时候就不要加 '/api'了，否则会出现2个'/api'，类似 '/api/api/user'这样的报错，切记！！！
    //baseURL: 'http://124.71.166.37:9090',
    timeout: 5000
})

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    //一定要用{}
    let user = localStorage.getItem("loguserinfo") ? JSON.parse(localStorage.getItem("loguserinfo")):{}
    if(user){
        config.headers['token'] = user.token;  //设置请求头
        //config.headers['token'] = '11111111111122222222';
    }

    //利用token避免直接通过更改路由来进行越界访问
    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;
        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        //当权限验证不同过的时候，给出提示
        if (res.code == '401'){
            ElementUI.Message({
                message:res.msg(),
                type:'error'
            })
        }
        return res;
    },
    error => {
        console.log('err' + error) // for debug
        return Promise.reject(error)
    }
)


export default request
