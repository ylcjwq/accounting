const express = require("express");
let Router = express.Router();
let mysql = require("../sql");
let path = require("path");
let multer = require("multer");

module.express = Router;
Router.use(express.json(), express.urlencoded());

//查询用户基本信息接口
Router.get("/getUserMessage/:id", async (req, res) => {
  try {
    const { id } = req.params;
    const row = await mysql.query(`SELECT * FROM user WHERE id='${id}'`); //查询数据库中该用户是否存在
    if (row.length <= 0) {
      res.send({
        code: 401,
        msg: "该用户不存在，请联系管理员",
      });
    } else {
      res.send({
        code: 200,
        msg: "查询成功",
        data: {
          id: row[0].id,
          name: row[0].name,
          userimg: row[0].img,
          gender: row[0].gender,
        },
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

//修改用户基本信息接口
Router.post("/userMessage", async (req, res) => {
  try {
    const { id, name, gender } = req.body;
    const row = await mysql.query(`SELECT * FROM user WHERE id='${id}'`); //查询数据库中该用户是否存在
    if (row.length <= 0) {
      res.send({
        code: 401,
        msg: "该用户不存在，请联系管理员",
      });
    } else {
      const inset = await mysql.query(
        `UPDATE user SET name='${name}',gender='${gender}' WHERE id='${id}'` //修改指定的用户名称和性别
      );
      res.send({
        code: 200,
        msg: "修改用户基本信息成功",
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

//修改用户密码接口
Router.post("/changePassword", async (req, res) => {
  try {
    const { id, oldPassword, newPassword } = req.body;
    const row = await mysql.query(`SELECT * FROM user WHERE id='${id}'`); //查询数据库中该用户是否存在
    if (row.length <= 0) {
      res.send({
        code: 401,
        msg: "该用户不存在，请联系管理员",
      });
    } else {
      const verify = await mysql.query(
        `SELECT * FROM user WHERE password='${oldPassword}' AND id='${id}'` //查询该用户的密码是否正确
      );
      if (verify.length <= 0) {
        res.send({
          code: 403,
          msg: "旧密码错误，请确定后重新输入",
        });
      } else {
        const revise = await mysql.query(
          `UPDATE user SET password='${newPassword}' WHERE id='${id}'` //修改密码
        );
        res.send({
          code: 200,
          msg: "修改成功",
        });
      }
    }
  } catch (err) {
    console.log(err);
    res.send({
      code: 500,
      msg: "服务端内部错误",
    });
  }
});

//上传头像的中间件
let storage = multer.diskStorage({
  destination: "./images",
  filename: function (req, file, cb) {
    // console.log(req.params);
    let ext = path.extname(file.originalname); //获取后缀
    let { id } = req.params; //获取id
    cb(null, file.fieldname + "-" + Date.now() + "-" + id + ext); //拼接生成唯一文件名
  },
});
let upload = multer({ storage });

//上传头像接口
// 是否有参数：图片文件  用户id
Router.post("/avatar/:id", upload.single("avatar"), async (req, res) => {
  try {
    // let server = "http://localhost:3300";
    let server = "http://8.130.71.186:3300"; //拼接服务器ip地址
    if (req.file == undefined) {
      res.send({
        code: 403,
        msg: "未上传成功，请重新上传图片",
      });
      return;
    }
    let url = req.file.destination.substring(1); //移除路径前面的.
    let filename = req.file.filename; //获取文件名
    let path = server + url + "/" + filename; //构建完整的url
    let { id } = req.params; //multer会把普通文本数据格式化到body中
    let row = await mysql.query(
      //存储头像路径
      `UPDATE user SET img='${path}' WHERE id=${id};select * from user where id=${id};`
    );
    // console.log(req.params);
    res.send({
      code: 200,
      msg: "头像上传成功",
      data: { path },
    });
  } catch (error) {
    console.log(error);
    res.send({
      code: 500,
      msg: "服务端内部错误",
    });
  }
});
module.exports = Router;
