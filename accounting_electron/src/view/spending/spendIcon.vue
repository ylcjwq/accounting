<template>
    <div class="spendIcon">
        <div class="outBorder">
            <ul class="icon_lists dib-box">
                <li class="dib" v-for="item in spendIcon" :key="item.name">
                    <svg class="icon svg-icon" aria-hidden="true">
                        <use :xlink:href="item.href"></use>
                    </svg>
                    <div class="name">{{ item.name }}</div>
                </li>
            </ul>
        </div>
    </div>
</template>

<script setup lang='ts'>
import { spendIcon } from "@/util/iconArray/icon"
</script>

<style lang='scss' scoped>
.spendIcon {
    width: 100%;
    height: 400px;
    display: flex;
    justify-content: center;
    align-items: center;

    .outBorder {
        width: 96%;
        height: 96%;
        border: 1px solid aquamarine;
        border-radius: 20px;

        .dib-box {
            display: flex;
            flex-wrap: wrap;

            .dib {
                width: 12.5%;
                height: 100px;

                .icon {
                    width: 100%;
                    height: 60px;
                    vertical-align: -0.15em;
                    fill: currentColor;
                    overflow: hidden;
                }

                .name {
                    text-align: center;
                }
            }

        }
    }
}
</style>
