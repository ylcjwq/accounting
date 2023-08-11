let mysql = require("../../api/sql");

const userExistsInDatabase = async (id) => {
  const row = await mysql.query(`SELECT * FROM user WHERE id='${id}'`); //查询数据库中该用户是否存在
  if (row.length <= 0) {
    return false;
  } else {
    return true;
  }
};

module.exports = {
  userExistsInDatabase,
};
