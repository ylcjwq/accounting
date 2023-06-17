const express = require('express');
let Router = express.Router();
let mysql = require('./sql');

module.exports = Router;

Router.use(express.json(), express.urlencoded())

Router.post('/login', async (req, res) => {    //登录接口
    let { username, password } = req.body;
    console.log(req.body);
    let row = await mysql.query(`select * from user WHERE username='${username}' AND password='${password}';`);

    if (row.length == 0) {
        res.send({
            code: 400,
            msg: '用户名或者密码错误'
        })
        return
    }
    res.send({
        code: 200,
        msg: '登录成功',
        data: row[0]
    })
})

Router.post('/isUser/:username', async (req, res) => {   //判断用户名是否存在接口
    let { username } = req.params;
    let row = await mysql.query(`select * from user where username='${username}';`);
    if (row.length == 0) {
        res.send({
            code: 200,
            msg: '该用户名可以注册'
        })
        return
    }
    res.send({
        code: 400,
        msg: '用户名已经存在，请重新输入'
    })
})

Router.post('/reg', async (req, res) => {     //注册接口
    let { username, password } = req.body
    let row = await mysql.query(`INSERT INTO user  VALUES (NULL,'${username}', '${password}',NULL,'${username}')`);
    res.send({
        code: 200,
        id: row.insertId,
        msg: '注册成功'
    })
})