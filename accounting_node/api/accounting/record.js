const express = require("express");
let Router = express.Router();
let mysql = require("../sql");
var dayjs = require("dayjs");

module.express = Router;
Router.use(express.json(), express.urlencoded());

//记录收入支出接口
Router.post("/record", async (req, res) => {
  try {
    const {
      form: { region, number, remark },
      dialogType,
      userId,
    } = req.body;
    const time = dayjs().format("YYYY-MM-DD HH:mm:ss");
    const row = await mysql.query(
      `INSERT INTO record  VALUES (NULL,'${userId}','${dialogType}', '${region}','${number}','${remark}','${time}')`
    );
    // id、用户id、支出/收入、支出/收入方式、金额、备注、时间
    res.send({
      code: 200,
      msg: "记录成功",
    });
  } catch (err) {
    console.log(err);
    res.send({
      code: 500,
      msg: "服务端内部错误",
    });
  }
});

//设置预算接口
Router.post("/budget", async (req, res) => {
  try {
    const { userId, budget } = req.body;
    const row = await mysql.query(`SELECT * FROM user WHERE id='${userId}'`); //查询数据库中该用户是否存在
    if (row.length <= 0) {
      res.send({
        code: 401,
        msg: "该用户不存在，请联系管理员",
      });
    } else {
      //查询是否设置过预算
      const set = await mysql.query(
        `SELECT * FROM budget Where usedId='${userId}'`
      );
      if (set.length <= 0) {
        //如果没有设置过预算，则新增一条预算信息
        await mysql.query(
          `INSERT INTO budget VALUES (NULL,'${userId}', '${budget}',1)`
        );
        res.send({
          code: 200,
          msg: "设置预算成功",
        });
      } else {
        //如果查询到用户id，则说明设置过预算，更新该预算信息
        await mysql.query(`UPDATE budget SET budget='${budget}'`);
        res.send({
          code: 200,
          msg: "更新预算成功",
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
module.exports = Router;
