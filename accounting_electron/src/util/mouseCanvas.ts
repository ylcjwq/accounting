class Utils {
    static randomDoubleFromRange(low: number, high: number): number {    //绕圆心的随机起始位置
        return Math.random() * (high - low + 1) + low;
    }
}
class Ball {
    x: number;                // 圆心坐标
    y: number;                // 圆心坐标
    radius: number;           // 半径
    color: string;            // 颜色
    canvas: HTMLCanvasElement;      // canvas元素
    ctx: CanvasRenderingContext2D; // 绘画上下文
    mouse: { x: number, y: number }; // 鼠标位置对象
    theta: number;          // 旋转角度
    speed: number;          // 旋转速度
    dragSpeed: number;      // 拖动速度
    distance: number;       // 旋转半径
    lastMouse: { x: number, y: number };   // 上一个鼠标的位置

    constructor(obj: {
        x: number,
        y: number,
        radius: number,
        color: string,
        canvas: HTMLCanvasElement,
        ctx: CanvasRenderingContext2D,
        mouse: { x: number, y: number }
    }) {
        this.x = obj.x;
        this.y = obj.y;
        this.radius = obj.radius;
        this.color = obj.color;
        this.canvas = obj.canvas;
        this.ctx = obj.ctx;
        this.mouse = obj.mouse;
        this.theta = Utils.randomDoubleFromRange(0, Math.PI * 2);
        this.speed = 0.05;
        this.dragSpeed = 0.05;
        this.distance = 50
        this.lastMouse = { x: obj.x, y: obj.y };
    }

    draw(lastPosition: { x: number, y: number }): void {
        this.ctx.beginPath();                      // 开始绘制路径
        var gradient = this.ctx.createLinearGradient(0, 0, this.canvas.width, this.canvas.height);    // 路径颜色渐变
        gradient.addColorStop(0, 'rgb(255,209,224)');
        gradient.addColorStop(0.2, 'rgb(255,140,0)');
        gradient.addColorStop(0.4, 'rgb(255,140,140)');
        gradient.addColorStop(0.6, 'rgb(246,50,245)');
        gradient.addColorStop(0.8, 'rgb(149,0,234)');
        gradient.addColorStop(1.0, 'rgb(255,209,224)');
        this.ctx.strokeStyle = gradient;
        this.ctx.lineWidth = this.radius;
        this.ctx.moveTo(lastPosition.x, lastPosition.y);     // 路径起始位置
        this.ctx.lineTo(this.x, this.y);                     // 路径结束位置
        this.ctx.stroke();                                   // 绘制
        this.ctx.closePath();                                // 结束路径
    }

    update(): void {
        let lastPosition = {
            x: this.x,
            y: this.y,
        };

        this.lastMouse.x += (this.mouse.x - this.lastMouse.x) * this.dragSpeed;     //更新圆心位置
        this.lastMouse.y += (this.mouse.y - this.lastMouse.y) * this.dragSpeed;

        this.x = this.lastMouse.x + Math.cos(this.theta) * this.distance;          //更新路径位置
        this.y = this.lastMouse.y + Math.sin(this.theta) * this.distance;
        this.theta += this.speed;
        this.draw(lastPosition);
    }
}

export class RotationBall {
    canvas: HTMLCanvasElement;    // canvas元素
    ctx: CanvasRenderingContext2D;   // 绘画上下文
    ball: Ball                         //小球
    mouse: { x: number, y: number };   // 鼠标位置

    constructor() {
        this.canvas = document.getElementById("canvas") as HTMLCanvasElement;
        this.ctx = this.canvas.getContext("2d") as CanvasRenderingContext2D;
        this.canvas.width = innerWidth;
        this.canvas.height = innerHeight;
        this.mouse = {
            x: innerWidth / 2,
            y: innerHeight / 2,
        };

        this.eventFn();
        // this.init();
        this.ball = this.init()
        this.animate();
    }

    init(): Ball {  //生成小球
        let color = "#fff"
        let ball = new Ball({
            x: this.canvas.width / 2,
            y: this.canvas.height / 2,
            radius: 4,
            color,
            canvas: this.canvas,
            ctx: this.ctx,
            mouse: this.mouse,
        })
        return ball
    }

    eventFn(): void {
        addEventListener("mousemove", (event) => {   //更新鼠标坐标
            this.mouse.x = event.clientX;
            this.mouse.y = event.clientY;
        });

        addEventListener("resize", () => {         //更新canvas画布宽高
            this.canvas.width = innerWidth;
            this.canvas.height = innerHeight;
        });
    }

    animate(): void {
        requestAnimationFrame(() => this.animate());    //递归调用animate方法实现动画效果
        this.ctx.fillStyle = "rgba(255, 255, 255, 0.1)";
        this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);   //每次更新后清除上一次的路径
        this.ball.update();
    }
}
