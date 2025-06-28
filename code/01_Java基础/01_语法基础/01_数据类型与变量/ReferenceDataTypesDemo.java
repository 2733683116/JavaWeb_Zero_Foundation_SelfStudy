/**
 * Java引用数据类型演示
 * 展示类、接口、数组、String等引用数据类型的声明与使用
 */
public class ReferenceDataTypesDemo {
    public static void main(String[] args) {
        // ===================== 类与对象 =====================
        System.out.println("=== 类与对象演示 ===");
        
        // 创建Person类的对象
        Person person = new Person("张三", 25);
        System.out.println("创建的Person对象：" + person.getName() + "，年龄：" + person.getAge());
        
        // 调用对象的方法
        person.sayHello();
        person.setAge(26);
        System.out.println("更新后的年龄：" + person.getAge());
        
        // ===================== 接口 =====================
        System.out.println("\n=== 接口演示 ===");
        
        // 创建实现接口的对象
        Rectangle rectangle = new Rectangle(5, 10);
        System.out.println("矩形面积：" + rectangle.calculateArea());
        System.out.println("矩形周长：" + rectangle.calculatePerimeter());
        
        // ===================== 数组 =====================
        System.out.println("\n=== 数组演示 ===");
        
        // 创建和初始化数组
        int[] intArray = new int[5];
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = i * 10;
        }
        
        // 访问数组元素
        System.out.println("数组的第三个元素：" + intArray[2]);
        
        // 使用增强for循环遍历数组
        System.out.print("数组全部元素：");
        for (int num : intArray) {
            System.out.print(num + " ");
        }
        System.out.println();
        
        // 引用类型数组
        Person[] personArray = new Person[2];
        personArray[0] = new Person("李四", 30);
        personArray[1] = new Person("王五", 35);
        
        System.out.println("第一个人的姓名：" + personArray[0].getName());
        
        // ===================== String类 =====================
        System.out.println("\n=== String类演示 ===");
        
        // 创建String对象
        String str1 = "Hello";
        String str2 = new String("World");
        
        // 字符串连接
        String str3 = str1 + " " + str2;
        System.out.println("连接后的字符串：" + str3);
        
        // 字符串常用方法
        System.out.println("字符串长度：" + str3.length());
        System.out.println("是否包含'World'：" + str3.contains("World"));
        System.out.println("转换为大写：" + str3.toUpperCase());
        
        // 字符串不可变性演示
        String original = "Java";
        String modified = original.concat(" Programming");
        System.out.println("原始字符串：" + original);
        System.out.println("修改后的字符串：" + modified);
    }
}

// 定义一个简单的类
class Person {
    private String name;
    private int age;
    
    // 构造方法
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // Getter和Setter方法
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    // 实例方法
    public void sayHello() {
        System.out.println("你好，我是" + name + "，今年" + age + "岁。");
    }
}

// 定义接口
interface Shape {
    double calculateArea();
    double calculatePerimeter();
}

// 实现接口的类
class Rectangle implements Shape {
    private double length;
    private double width;
    
    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }
    
    @Override
    public double calculateArea() {
        return length * width;
    }
    
    @Override
    public double calculatePerimeter() {
        return 2 * (length + width);
    }
}