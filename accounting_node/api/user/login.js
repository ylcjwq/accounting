const express = require("express");
let Router = express.Router();
let mysql = require("../sql");
const JWT = require("jsonwebtoken");
var dayjs = require("dayjs");

module.exports = Router;

Router.use(express.json(), express.urlencoded());

//登录接口
Router.post("/login", async (req, res) => {
  try {
    let { username, password } = req.body;
    console.log(req.body);
    let row = await mysql.query(
      `SELECT * FROM user WHERE username='${username}' AND password='${password}';`
    ); //查询数据库中是否有对应的账号密码

    if (row.length == 0) {
      //不存在，返回400
      res.send({
        code: 403,
        msg: "用户名或者密码错误",
      });
      return;
    }
    // 存在
    // 根据用户数据，生成token
    // 将id和账号信息存入对象，转换为token
    delete row[0].password;
    row[0].time = dayjs(row[0].time).format("YYYY-MM-DD HH:mm:ss");
    let obj = { id: row[0].id, username: row[0].username };
    const token = JWT.sign(obj, "hello", {
      expiresIn: "7d", //过期时间
    });
    res.send({
      code: 200,
      msg: "登录成功",
      data: row[0],
      token: token,
    });
  } catch (error) {
    console.error(error);
    res.send({
      code: 500,
      msg: "服务端内部错误",
    });
  }
});

//注册接口
Router.post("/enroll", async (req, res) => {
  try {
    const { username, password } = req.body;
    const row = await mysql.query(
      `SELECT * FROM user WHERE username='${username}'`
    ); //查询数据库中用户名是否存在
    if (row.length == 0) {
      //用户名不存在，可以注册
      const time = dayjs().format("YYYY-MM-DD HH:mm:ss");
      console.log(time);
      //将用户名、密码、名称写入user表
      const row = await mysql.query(
        `INSERT INTO user  VALUES (NULL,'${username}', '${password}','${username}','男',NULL,'${time}')`
      );
      res.send({
        code: 200,
        msg: "注册成功",
      });
    } else {
      // 如果存在
      res.send({
        code: 409,
        msg: "该用户已存在，请重新输入",
      });
    }
  } catch (err) {
    console.log(err);
    res.send({
      code: 500,
      msg: "服务端内部错误",
    });
  }
});

module.exports = Router;
