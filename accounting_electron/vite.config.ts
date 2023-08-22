import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

import AutoImport from "unplugin-auto-import/vite";
import Components from "unplugin-vue-components/vite";
import { ElementPlusResolver } from "unplugin-vue-components/resolvers";

import * as path from "path";
import electron from "vite-plugin-electron";
import electronRenderer from "vite-plugin-electron/renderer";
import polyfillExports from "vite-plugin-electron/polyfill-exports";

export default defineConfig({
  plugins: [
    vue(),
    electron({
      main: {
        entry: "electron-main/index.ts", // 主进程文件
      },
      preload: {
        input: path.join(__dirname, "./electron-preload/index.js"), // 预加载文件
      },
    }),
    electronRenderer(),
    polyfillExports(),
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
  ],
  build: {
    emptyOutDir: false, // 默认情况下，若 outDir 在 root 目录下，则 Vite 会在构建时清空该目录
  },
  resolve: {
    dedupe: ["vue"],
    alias: {
      "@": path.join(__dirname, "src"),
    },
  },
  server: {
    proxy: {
      //配置代理服务器
      "/api": {
        target: "http://43.138.195.96:9999",
        // target: "http://8.130.71.186:3300", //服务器url
        // target: "http://localhost:3300", //本地url
        changeOrigin: true, //允许跨域
        rewrite: (path) => path.replace(/^\/api/, ""), //重写路径,替换/api
      },
    },
  },
});
