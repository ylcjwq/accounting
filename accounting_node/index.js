let express = require('express');

// let { createProxyMiddleware } = require('http-proxy-middleware')

let app = express();
const cors = require('cors');
app.use(cors({
    origin: ['http://localhost:8080', 'http://127.0.0.1:5500'],//可设置多个跨域域名
    credentials: true//允许客户端携带验证信息
}))
app.use(express.urlencoded({ extended: true, limit: "10mb" }));    //允许携带最大信息增加为10mb
app.use(express.json({ extended: true, limit: "10mb" }));

app.use(express.static('./api'));

app.get('/images/*', function (req, res) {     //允许images下的图片被访问
    res.sendFile(__dirname + "/" + req.url);
})

// 导入模块
//登录相关模块
let login = require('./api/user/login')
app.use('/login', login)

//用户信息相关模块
let user = require('./api/user/user')
app.use('/user', user)

//记账相关模块
let record = require('./api/accounting/record')
app.use('/record', record)


app.listen(3300, () => {
    console.log('running port is 3300(client)');
})