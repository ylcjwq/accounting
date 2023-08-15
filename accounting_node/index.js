let express = require("express");
let app = express();
const cors = require("cors");
const JWT = require("jsonwebtoken");
const { userExistsInDatabase } = require("./util/sql/user");
app.use(
  cors({
    origin: ["http://localhost:8080", "http://127.0.0.1:5500"], //可设置多个跨域域名
    credentials: true, //允许客户端携带验证信息
  })
);
app.use(express.urlencoded({ extended: true, limit: "10mb" })); //允许携带最大信息增加为10mb
app.use(express.json({ extended: true, limit: "10mb" }));
app.use(express.static("./api"));

const SECRET_KEY = "hello"; // 与生成token的密钥要一致!
const parseJwt = (req, res, next) => {
  if (
    req.path === "/login/login" ||
    req.path === "/login/enroll" ||
    req.path.startsWith("/images/")
  ) {
    // 登录注册页和头像资源无需校验
    return next();
  }
  const token = req.headers.authorization?.split(" ")[1]; // 从请求头中提取 Token
  if (token) {
    try {
      const decoded = JWT.verify(token, SECRET_KEY);
      // 进一步检查数据库中是否存在用户信息的逻辑，例如查询数据库
      if (userExistsInDatabase(decoded.id)) {
        req.user = decoded; // 将解码后的信息存储在 req.user 中
      } else {
        return res.send({
          code: 403,
          msg: "用户信息不存在,请重新登录",
        });
      }
      next();
    } catch (err) {
      console.log(err);
      res.send({
        code: 401,
        msg: "登录信息错误，请重新登录",
      });
    }
  } else {
    res.send({
      code: 401,
      msg: "登陆失效，请重新登录",
    });
  }
};
app.use(parseJwt);

//错误处理中间件，处理token验证的错误
app.use(function (err, req, res, next) {
  if (err.name === "UnauthorizedError") {
    res.send({
      code: 401,
      msg: "登录状态过期，请重新登录",
    });
  } else {
    next();
  }
});

//允许images下的图片被访问
app.get("/images/*", function (req, res) {
  res.sendFile(__dirname + "/" + req.url);
});

// 导入模块
//登录相关模块
let login = require("./api/user/login");
app.use("/login", login);

//用户信息相关模块
let user = require("./api/user/user");
app.use("/user", user);

//记账相关模块
let record = require("./api/accounting/record");
app.use("/record", record);

app.listen(3300, () => {
  console.log("running port is 3300(client)");
});
