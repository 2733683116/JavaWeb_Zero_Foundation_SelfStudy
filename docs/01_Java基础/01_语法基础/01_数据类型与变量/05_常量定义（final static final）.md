# 一、常量概述
常量是指在程序运行过程中**值不能被修改**的量。在 Java 中，常量通过final关键字定义，根据作用范围和使用场景，可分为**实例常量**（final修饰）和**类常量**（static final修饰）。常量的主要作用是：
- 提高代码可读性（用有意义的名称代替魔法值）
- 保证数据安全性（防止意外修改）
- 便于维护（集中修改常量值）
# 二、final 关键字与实例常量
## 2.1 实例常量的定义
实例常量是用final修饰的变量，属于对象实例，每个对象可以有不同的常量值（但初始化后不可修改）。

```java
public class Person {
    // 实例常量（每个Person对象可拥有不同的id，但初始化后不可修改）
    private final String id;
    private String name;
    
    // 必须在构造方法中初始化final变量
    public Person(String id, String name) {
        this.id = id; // 初始化实例常量
        this.name = name;
    }
    
    // 错误示例：无法修改final变量
    public void setId(String newId) {
        // this.id = newId; // 编译错误：final变量不能被重新赋值
    }
}
```
## 2.2 实例常量的特性
1. **必须初始化**：声明时未初始化的final变量，必须在构造方法或初始化块中完成初始化，否则编译错误。

```java
public class Demo {
    final int a; // 声明时未初始化
    final int b = 10; // 声明时初始化
    
    // 初始化块中初始化
    {
        a = 20;
    }
    
    // 或在构造方法中初始化
    // public Demo() {
    //     a = 20;
    // }
}
```
2. **不可修改**：一旦初始化完成，final变量的值不能被重新赋值（基本类型值不可变，引用类型地址不可变）。

```java
final int num = 10;
// num = 20; // 编译错误

final int[] arr = {1, 2, 3};
arr[0] = 100; // 合法：引用地址未变，仅修改数组内容
// arr = new int[5]; // 编译错误：引用地址不能修改
```
3. **对象级别的常量**：每个对象的final变量独立存在，可拥有不同值。

```java
Person p1 = new Person("001", "Alice");
Person p2 = new Person("002", "Bob");
// p1和p2的id不同，但均不可修改
```
# 三、static final 与类常量
## 3.1 类常量的定义
类常量是用static final共同修饰的变量，属于类本身，被所有对象共享，且值全局唯一（程序运行期间不可修改）。

```java
public class MathUtils {
    // 类常量（全类共享，值不可修改）
    public static final double PI = 3.1415926535;
    public static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
}
```
## 3.2 类常量的特性
1. **类级别的常量**：属于类，不依赖对象存在，可通过类名.常量名直接访问。

```java
System.out.println(MathUtils.PI); // 直接通过类名访问
```
2. **必须显式初始化**：声明时必须初始化（或在静态初始化块中初始化），且初始化值必须是**编译期可知的常量表达式**。

```java
public class Constants {
    // 声明时初始化（推荐）
    public static final String VERSION = "1.0.0";
    
    public static final int MAX_NUM;
    
    // 静态初始化块中初始化
    static {
        MAX_NUM = 1000;
    }
}
```
3. **全局唯一且不可修改**：所有对象共享同一值，且任何时候都不能被重新赋值。

```java
// MathUtils.PI = 3.14; // 编译错误：static final变量不可修改
```
4. **命名规范**：通常使用**全大写字母**，多个单词用下划线分隔（如MAX_VALUE）。
## 3.3 类常量的典型应用场景
1. **定义全局常量**：如数学常数、配置参数等。

```java
public class Config {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    public static final int TIMEOUT = 3000;
}
```
2. **枚举替代**：在没有枚举（JDK 1.5 前）的场景下，用类常量表示固定选项。

```java
public class Season {
    public static final int SPRING = 1;
    public static final int SUMMER = 2;
    public static final int AUTUMN = 3;
    public static final int WINTER = 4;
}
```
3. **避免魔法值**：用常量名替代代码中的直接数值或字符串，提高可读性。

```java
// 不推荐：魔法值难以理解
if (status == 200) { ... }

// 推荐：用常量替代
public static final int SUCCESS_STATUS = 200;
if (status == SUCCESS_STATUS) { ... }
```
# 四、final 与 static final 的对比
|特性|final（实例常量）|static final（类常量）|
|--|--|--|
|所属对象|属于单个对象实例|属于类，被所有对象共享|
|访问方式|通过对象实例访问（对象.常量名）|通过类名访问（类名.常量名，推荐）|
|初始化时机|声明时、构造方法或初始化块|声明时或静态初始化块|
|内存存储|每个对象拥有独立副本，存储在堆中|全局唯一，存储在方法区的静态区|
|赋值限制|每个对象可赋不同值，但初始化后不可改|全局唯一值，编译期确定后不可改|
|典型用途|对象的不可变属性（如 ID）|全局共享的常量（如配置参数）|
# 五、使用常量的注意事项
## 5.1 final 修饰引用类型的特殊性
final修饰引用类型时，**仅保证引用地址不变**，但对象内部的属性仍可修改：

```java
class User {
    private String name;
    // getter和setter省略
}

public class Test {
    public static void main(String[] args) {
        final User user = new User();
        user.setName("Alice"); // 合法：可修改对象内部属性
        // user = new User(); // 编译错误：不可修改引用地址
    }
}
```
## 5.2 避免 final 变量的不合理使用
* 不要将所有变量都声明为final，仅用于确实需要不可修改的场景
* 局部变量使用final可提高代码可读性（明确表示该变量不会被修改）

```java
public void printInfo(final String message) {
    // message = "new"; // 编译错误：final参数不可修改
    System.out.println(message);
}
```
## 5.3 static final 与编译期常量
static final变量若初始化值为**编译期常量表达式**（如字面量、简单运算），会被编译器直接替换到引用处，即 “常量折叠”：

```java
public class Constants {
    public static final int NUM = 10 + 20; // 编译期可计算
}

// 引用处会被编译器替换为30
System.out.println(Constants.NUM); // 实际执行：System.out.println(30);
```

> 注意：若常量值需要运行时计算（如new Date().getTime()），则不会发生常量折叠。

## 5.4 接口中的常量
接口中定义的变量默认是public static final（接口常量），通常用于定义与接口相关的常量：

```java
public interface Shape {
    // 默认为public static final
    double PI = 3.14159;
    int TYPE_CIRCLE = 1;
    int TYPE_RECTANGLE = 2;
}
```
# 六、最佳实践
1. **优先使用 static final 定义全局常量**：如配置参数、固定值等，便于集中管理和访问。
2. **实例常量用于对象的不可变属性**：如用户 ID、订单编号等一旦创建就不应修改的属性。
3. **遵循命名规范**：类常量用全大写 + 下划线（MAX_SIZE），实例常量首字母小写（final int maxCount）。
4. **避免在常量中使用可变对象**：如static final List<String> LIST = new ArrayList<>();，虽然引用不可变，但集合内容可修改，易引发线程安全问题。
5. **JDK 1.5 + 推荐使用枚举**：固定选项场景（如季节、状态）优先使用枚举，而非多个static final常量。
通过合理使用final和static final，可以提高代码的安全性、可读性和可维护性，是 Java 编程中的重要实践。