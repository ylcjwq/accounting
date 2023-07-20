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

//修改用户基本信息接口
Router.post('/changePassword', async (req, res) => {
    try {
        const { id, oldPassword, newPassword } = req.body
        const row = await mysql.query(`SELECT * FROM user WHERE id='${id}'`) //查询数据库中该用户是否存在
        if (row.length <= 0) {
            res.send({
                code: 401,
                msg: "该用户不存在，请联系管理员"
            })
        } else {
            const verify = await mysql.query(`SELECT * FROM user WHERE password='${oldPassword}' AND id='${id}'`) //查询该用户的密码是否正确
            if (verify.length <= 0) {
                res.send({
                    code: 403,
                    msg: "旧密码错误，请确定后重新输入"
                })
            } else {
                const revise = await mysql.query(`UPDATE user SET password='${newPassword}' WHERE id='${id}'`)
                res.send({
                    code: 200,
                    msg: '修改成功'
                })
            }
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