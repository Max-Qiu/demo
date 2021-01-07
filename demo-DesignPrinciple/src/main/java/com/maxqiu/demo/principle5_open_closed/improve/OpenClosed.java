package com.maxqiu.demo.principle5_open_closed.improve;

/**
 * @author Max_Qiu
 */
public class OpenClosed {

    public static void main(String[] args) {
        GraphicEditor graphicEditor = new GraphicEditor();
        graphicEditor.drawShape(new Rectangle());
        graphicEditor.drawShape(new Circle());
        graphicEditor.drawShape(new Triangle());
    }

}

// 这是一个用于绘图的类 [使用方]
class GraphicEditor {
    // 接收Shape对象，调用draw方法
    public void drawShape(Shape s) {
        s.draw();
    }
}

// Shape类，基类
abstract class Shape {
    int type;

    // 抽象方法
    public abstract void draw();
}

class Rectangle extends Shape {
    Rectangle() {
        super.type = 1;
    }

    @Override
    public void draw() {
        System.out.println(" 绘制矩形 ");
    }
}

class Circle extends Shape {
    Circle() {
        super.type = 2;
    }

    @Override
    public void draw() {
        System.out.println(" 绘制圆形 ");
    }
}

// 新增画三角形
class Triangle extends Shape {
    Triangle() {
        super.type = 3;
    }

    @Override
    public void draw() {
        System.out.println(" 绘制三角形 ");
    }
}
