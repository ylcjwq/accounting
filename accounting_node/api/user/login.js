const express = require('express');
let Router = express.Router();
let mysql = require('../sql');
const JWT = require('jsonwebtoken');

module.exports = Router;

Router.use(express.json(), express.urlencoded())


//登录接口
Router.post('/login', async (req, res) => {
    let { username, password } = req.body;
    console.log(req.body);
    let row = await mysql.query(`SELECT * FROM user WHERE username='${username}' AND password='${password}';`);  //查询数据库中是否有对应的账号密码

    if (row.length == 0) {     //不存在，返回400
        res.send({
            code: 403,
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


//注册接口
Router.post("/enroll", async (req, res) => {
    const { username, password } = req.body
    const row = await mysql.query(`SELECT * FROM user WHERE username='${username}'`) //查询数据库中用户名是否存在
    if (row.length == 0) {     //用户名不存在，可以注册
        const row = await mysql.query(`INSERT INTO user  VALUES (NULL,'${username}', '${password}',NULL,'${username}')`)   //将用户名、密码、名称写入user表
        res.send({
            code: 200,
            msg: '注册成功',
        })
    } else {
        // 如果存在
        res.send({
            code: 409,
            msg: "该用户已存在，请重新输入",
        })
    }
})



// !校验登录(暂未实现)
Router.post("/checkLogin", async (req, res) => {
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

module.exports = Router