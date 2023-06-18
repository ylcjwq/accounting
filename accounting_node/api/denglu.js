const express = require('express');
let Router = express.Router();
let mysql = require('./sql');
const JWT = require('jsonwebtoken');

module.exports = Router;

Router.use(express.json(), express.urlencoded())

Router.post('/login', async (req, res) => {    //登录接口
    let { username, password } = req.body;
    console.log(req.body);
    let row = await mysql.query(`select * from user WHERE username='${username}' AND password='${password}';`);  //查询数据库中是否有对应的账号密码

    if (row.length == 0) {     //不存在，返回400
        res.send({
            code: 400,
            msg: '用户名或者密码错误'
        })
        return
    }
    // 存在
    // 根据用户数据，生成token
    //将id账号密码信息存入对象，转换为token
    let obj = { id: row[0].id, username: row[0].username, password: row[0].password }
    const token = JWT.sign(obj, 'hello', {
        expiresIn: '7d',   //过期时间
    });
    res.send({
        code: 200,
        msg: '登录成功',
        data: row[0],
        token: token
    })
})

// !校验登录(暂未实现)
router.post("/check_login", async (req, res) => {
    // 从token获取用户id
    const token = req.body.token;
    try {
        const { _id } = JWT.verify(token, 'hello');
        //token没问题
        const result = await User.findById(_id).populate('roles');
        if (result) {
            //登录的状态是正确的。
            res.json({ code: 0, message: "登录成功", data: result });
        } else {
            //登录状态被窜改了。
            res.json({ code: -1, message: "过期了,请重新登录" });
        }

    } catch (error) {
        //token失效了
        res.json({ code: -1, message: "过期了,请重新登录" });
    }
});

