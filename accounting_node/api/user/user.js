const express = require("express")
let Router = express.Router()
let mysql = require("../sql")

module.express = Router
Router.use(express.json(), express.urlencoded())

//修改用户基本信息接口
Router.post('/userMessage', async (req, res) => {
    try {
        const { id, name, gender } = req.body
        console.log(id, name, gender);
        const row = await mysql.query(`SELECT * FROM user WHERE id='${id}'`) //查询数据库中该用户是否存在
        if (row.length <= 0) {
            res.send({
                code: 401,
                msg: "该用户不存在，请联系管理员"
            })
        } else {
            const inset = await mysql.query(`UPDATE user SET name='${name}',gender='${gender}' WHERE id='${id}'`) //修改指定的用户名称
            res.send({
                code: 200,
                msg: "修改用户基本信息成功"
            })
        }
    } catch (err) {
        console.log(err);
        res.send({
            code: 500,
            msg: '服务端内部错误'
        })
    }
})
module.exports = Router