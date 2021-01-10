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
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    // 抽象方法
    public abstract void draw();
}

class Rectangle extends Shape {
    public Rectangle() {
        setType(1);
    }

    @Override
    public void draw() {
        System.out.println(" 绘制矩形 ");
    }
}

class Circle extends Shape {
    public Circle() {
        setType(2);
    }

    @Override
    public void draw() {
        System.out.println(" 绘制圆形 ");
    }
}

// 新增画三角形
class Triangle extends Shape {
    public Triangle() {
        setType(3);
    }

    @Override
    public void draw() {
        System.out.println(" 绘制三角形 ");
    }
}
