<template>
  <div>
    <slot />
  </div>
</template>

<script setup lang="ts"></script>

<style lang="scss" scoped>
div {
  text-align: center;
  width: 120px;
  position: relative;
  padding: 10px 30px;
  margin: 50px 0;
  color: skyblue;
  text-decoration: none;
  font-size: 20px;
  transition: 0.1s;
  overflow: hidden;
  cursor: pointer;
}

div:hover::before {
  width: 100%;
  height: 100%;
  transition: 1s;
}

div::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 10px;
  height: 10px;
  border-top: 2px solid skyblue;
  border-left: 2px solid skyblue;
  transition: 1s;
}

div:hover::after {
  width: 100%;
  height: 100%;
  transition: 1s;
}

div::after {
  content: "";
  position: absolute;
  bottom: 0;
  right: 0;
  width: 10px;
  height: 10px;
  border-bottom: 2px solid skyblue;
  border-right: 2px solid skyblue;
  transition: 1s;
}

div:hover {
  background: skyblue;
  color: #111;
  box-shadow: 0 0 50px skyblue;
  transition: 1s;
  border-radius: 20px;
}
</style>
