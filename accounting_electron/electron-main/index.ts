import { app, BrowserWindow, session } from "electron";
import path from "path";

const createWindow = () => {
    const win = new BrowserWindow({
        webPreferences: {
            contextIsolation: false,  //是否隔离上下文
            nodeIntegration: true, //渲染进程使用Node API
            preload: path.join(__dirname, "../../electron-preload/index.js"), //需要引用的js文件,electron还不能识别ts
        }
    })

    //如果打包了，渲染index.html
    if (app.isPackaged) {
        win.loadFile(path.join(__dirname, "../../index.html"))
    } else {
        let url = "http://localhost:3000"
        win.loadURL(url)
    }
}

app.whenReady().then(() => {
    createWindow()   //创建窗口
    app.on("activate", () => {
        if (BrowserWindow.getAllWindows().length === 0) createWindow();
    })
})

app.once("ready", async () => {   //给electron添加vue调试工具
    let extensionPath = "C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Extensions\\nhdogjmejiglipccpnnnanhbledajbpd\\6.5.0_0"
    // BrowserWindow.addDevToolsExtension(extensionPath)
    try {
        await session.defaultSession.loadExtension(extensionPath)
    } catch (error) {
        console.log(error);
    }

})

//关闭窗口
app.on("window-all-closed", () => {
    if (process.platform !== "darwin") {
        app.quit()
    }
})