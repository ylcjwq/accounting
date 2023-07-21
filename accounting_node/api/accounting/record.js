const express = require("express")
let Router = express.Router()
let mysql = require("../sql")
var dayjs = require('dayjs')

module.express = Router
Router.use(express.json(), express.urlencoded())

//记录收入支出接口
Router.post('/record', async (req, res) => {
    try {
        const { form: { region, number, remark }, dialogType, userId } = req.body
        console.log(req.body);
        const time = dayjs().format('YYYY-MM-DD HH:mm:ss');
        const row = await mysql.query(`INSERT INTO record  VALUES (NULL,'${userId}','${dialogType}', '${region}','${number}','${remark}','${time}')`)
        // id、用户id、支出/收入、支出/收入方式、金额、备注、时间
        res.send({
            code: 200,
            msg: "记录成功"
        })
    } catch (err) {
        console.log(err);
        res.send({
            code: 500,
            msg: '服务端内部错误'
        })
    }
})
module.exports = Router