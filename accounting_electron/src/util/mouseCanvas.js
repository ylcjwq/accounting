class Utils {
    static randomDoubleFromRange(low, high) {      //随机位置
        return Math.random() * (high - low + 1) + low;
    }

    static randomColors(colors) {       //随机颜色
        return colors[Math.floor(Math.random() * colors.length)];
    }
}

class Ball {
    constructor(obj) {
        this.x = obj.x;                //圆心坐标
        this.y = obj.y;                //圆心坐标
        this.radius = obj.radius;      //半径
        this.color = obj.color;        //颜色
        this.canvas = obj.canvas;      //canvas元素
        this.ctx = obj.ctx;            //绘画上下文
        this.mouse = obj.mouse;        //鼠标位置对象
        this.theta = Utils.randomDoubleFromRange(0, Math.PI * 2);     //旋转角度
        this.speed = 0.05;             //旋转速度
        this.dragSpeed = 0.05;         //拖动速度
        this.distance = Utils.randomDoubleFromRange(70, 100);   //移动的距离
        this.lastMouse = { x: obj.x, y: obj.y };                //上一个鼠标的位置
    }

    draw(lastPosition) {
        this.ctx.beginPath();                      //开始绘制路径
        var gradient = this.ctx.createLinearGradient(0, 0, this.canvas.width, this.canvas.height);    //路径颜色渐变
        gradient.addColorStop("0", "magenta");
        gradient.addColorStop("0.5", "blue");
        gradient.addColorStop("1.0", "red");
        this.ctx.strokeStyle = gradient;
        this.ctx.lineWidth = this.radius;
        this.ctx.moveTo(lastPosition.x, lastPosition.y);     //路径起始位置
        this.ctx.lineTo(this.x, this.y);                     //路径结束位置
        this.ctx.stroke();                                   //绘制
        this.ctx.closePath();                                //结束路径
    }

    update() {
        let lastPosition = {
            x: this.x,
            y: this.y,
        };

        this.lastMouse.x += (this.mouse.x - this.lastMouse.x) * this.dragSpeed;
        this.lastMouse.y += (this.mouse.y - this.lastMouse.y) * this.dragSpeed;

        this.x = this.lastMouse.x + Math.cos(this.theta) * this.distance;
        this.y = this.lastMouse.y + Math.sin(this.theta) * this.distance;
        this.theta += this.speed;
        this.draw(lastPosition);
    }
}

export class RotationBall {
    constructor() {
        this.canvas = document.getElementById("canvas");
        console.log(this.canvas);
        this.ctx = this.canvas.getContext("2d");
        this.canvas.width = innerWidth;
        this.canvas.height = innerHeight;
        this.balls = [];
        this.ball;

        // 颜色
        this.colorArray = ["#FF0000", "#FF7F00", "#FFFF00", "#00FF00", "#0000FF", "#4B0082", "#9400D3"];

        // 鼠标位置
        this.mouse = {
            x: innerWidth / 2,
            y: innerHeight / 2,
        };

        this.eventFn();
        this.init();
        this.animate();
    }

    init() {

        let color = Utils.randomColors(this.colorArray);
        this.balls.push(
            new Ball({
                x: this.canvas.width / 2,
                y: this.canvas.height / 2,
                radius: 3,
                color,
                canvas: this.canvas,
                ctx: this.ctx,
                mouse: this.mouse,
            })
        );

    }

    eventFn() {
        addEventListener("mousemove", (event) => {
            this.mouse.x = event.clientX;
            this.mouse.y = event.clientY;
        });

        addEventListener("resize", () => {
            this.canvas.width = innerWidth;
            this.canvas.height = innerHeight;
            // this.init();
        });
    }

    animate() {
        requestAnimationFrame(() => this.animate());
        this.ctx.fillStyle = "rgba(255, 255, 255, 0.1)";
        this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
        for (let ball of this.balls) {
            ball.update();
        }
    }
}
