/**
 * Java变量作用域完整演示
 * 涵盖局部变量、成员变量、静态变量的作用域特性及冲突处理
 */
public class VariableScopeDemo {
    // ===================== 成员变量（实例变量）=====================
    private int memberVar1 = 100; // private修饰，仅本类可见
    int memberVar2 = 200;         // 默认修饰符，同包可见
    protected int memberVar3 = 300; // protected修饰，本类、同包、子类可见

    // ===================== 静态变量（类变量）=====================
    public static String staticVar = "我是静态变量";
    private static int staticCount = 0;

    public static void main(String[] args) {
        // 访问静态变量（类变量）：通过类名访问（推荐）
        System.out.println("main方法中访问静态变量：" + VariableScopeDemo.staticVar);
        VariableScopeDemo.staticCount++;

        // 创建实例对象，访问成员变量
        VariableScopeDemo instance = new VariableScopeDemo();
        System.out.println("访问private成员变量：" + instance.memberVar1);
        System.out.println("访问默认修饰符成员变量：" + instance.memberVar2);

        // 调用方法演示局部变量作用域
        instance.demoLocalVariable();
        instance.demoScopeConflict();
        instance.demoNestedScope();
        instance.demoArrayScope(); // 新增数组作用域演示
    }

    // ===================== 局部变量作用域演示 =====================
    public void demoLocalVariable() {
        System.out.println("\n=== 局部变量作用域演示 ===");
        
        // 方法内的局部变量：作用域为整个方法
        int methodVar = 50;
        System.out.println("方法内局部变量：" + methodVar);

        // if代码块内的局部变量：作用域仅限该代码块
        if (methodVar > 0) {
            int blockVar = 10;
            System.out.println("if块内访问blockVar：" + blockVar); // 合法
            System.out.println("if块内访问方法变量：" + methodVar); // 合法（外部变量）
        }

        // 以下代码会编译错误：blockVar超出作用域
        // System.out.println("方法内访问if块变量：" + blockVar);

        // for循环中的局部变量：作用域仅限循环体
        for (int loopVar = 0; loopVar < 2; loopVar++) {
            System.out.println("for循环内的loopVar：" + loopVar);
        }

        // 以下代码会编译错误：loopVar超出作用域
        // System.out.println("循环外访问loopVar：" + loopVar);
    }

    // ===================== 变量重名冲突处理 =====================
    public void demoScopeConflict() {
        System.out.println("\n=== 变量重名冲突演示 ===");
        
        int memberVar1 = 500; // 局部变量与成员变量同名（就近原则）
        System.out.println("局部变量memberVar1：" + memberVar1); // 访问局部变量
        System.out.println("成员变量memberVar1（通过this）：" + this.memberVar1); // 访问成员变量

        // 静态变量与局部变量同名
        String staticVar = "我是局部变量";
        System.out.println("局部变量staticVar：" + staticVar);
        System.out.println("静态变量staticVar（通过类名）：" + VariableScopeDemo.staticVar);
    }

    // ===================== 嵌套代码块作用域 =====================
    public void demoNestedScope() {
        System.out.println("\n=== 嵌套代码块作用域演示 ===");
        
        int x = 10; // 外部代码块变量
        System.out.println("外部代码块x：" + x);

        { // 内部代码块
            int innerX = 20; // 修改变量名，避免与外部变量冲突
            System.out.println("内部代码块innerX：" + innerX);

            int y = 30; // 内部块独有变量
            System.out.println("内部代码块y：" + y);
        }

        // 外部代码块仍可访问自己的x
        System.out.println("外部代码块x（未受内部影响）：" + x);

        // 以下代码会编译错误：y超出作用域
        // System.out.println("外部访问内部块y：" + y);
    }

    // ===================== 静态方法访问限制 =====================
    public static void staticMethodDemo() {
        System.out.println("\n=== 静态方法访问限制演示 ===");
        
        // 静态方法可直接访问静态变量
        System.out.println("静态方法访问静态变量：" + staticCount);

        // 以下代码会编译错误：静态方法不能直接访问非静态成员变量
        // System.out.println("静态方法访问成员变量：" + memberVar1);

        // 静态方法需通过对象访问成员变量
        VariableScopeDemo instance = new VariableScopeDemo();
        System.out.println("静态方法通过对象访问成员变量：" + instance.memberVar1);
    }

    // ===================== 数组作用域演示 =====================
    public void demoArrayScope() {
        System.out.println("\n=== 数组作用域演示 ===");
        
        // 局部数组（方法内定义）
        int[] localArray = {1, 2, 3};
        System.out.print("局部数组元素：");
        for (int num : localArray) {
            System.out.print(num + " ");
        }

        // 代码块内的数组
        {
            String[] blockArray = {"a", "b", "c"};
            System.out.print("\n块内数组元素：");
            for (String s : blockArray) {
                System.out.print(s + " ");
            }
        }

        // 以下代码会编译错误：blockArray超出作用域
        // System.out.println("访问块外的blockArray：" + blockArray[0]);
    }
}