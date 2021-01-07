package com.maxqiu.demo.principle5_open_closed.demo;

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

// 这是一个用于绘图的类[使用方]
class GraphicEditor {
    // 接收Shape对象，然后根据类型，来绘制不同的图形
    public void drawShape(Shape s) {
        if (s.type == 1) {
            drawRectangle(s);
        } else if (s.type == 2) {
            drawCircle(s);
        }
        // 新增三角形类型判断
        else if (s.type == 3) {
            drawTriangle(s);
        }
    }

    // 绘制矩形
    public void drawRectangle(Shape r) {
        System.out.println(" 绘制矩形 ");
    }

    // 绘制圆形
    public void drawCircle(Shape r) {
        System.out.println(" 绘制圆形 ");
    }

    // 新增绘制三角形
    public void drawTriangle(Shape r) {
        System.out.println(" 绘制三角形 ");
    }
}

// Shape类，基类
class Shape {
    int type;
}

class Rectangle extends Shape {
    Rectangle() {
        super.type = 1;
    }
}

class Circle extends Shape {
    Circle() {
        super.type = 2;
    }
}

// 新增画三角形
class Triangle extends Shape {
    Triangle() {
        super.type = 3;
    }
}
