# 一、方法重写的基本概念
方法重写（Override）是指**子类中定义一个与父类同名、同参数列表、同返回值类型的方法**，用于覆盖父类方法的实现，使子类能根据自身需求修改父类的行为。
- **核心作用**：实现 “子类特化”，即子类在继承父类共性的基础上，定制自己的具体实现（如 “动物” 类的move()方法，被 “鸟” 类重写为 “飞”，被 “鱼” 类重写为 “游”）。
- **与重载的区别**：重写是子类与父类之间的方法关系（方法签名完全相同），重载是同一类中方法名相同但参数不同的关系。

![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/583a345ed2b84d9db0123a80e5d219b4.png#pic_center)

# 二、方法重写的核心规则
## 2.1 方法签名必须完全相同
**方法签名**包括方法名、参数列表（参数类型、个数、顺序），子类重写的方法必须与父类方法的签名完全一致，否则不构成重写（可能是重载）。

**示例（正确）**：

```java
// 父类
class Parent {
    public void printInfo(String name, int age) {
        System.out.println("姓名：" + name + "，年龄：" + age);
    }
}

// 子类（正确重写：签名完全相同）
class Child extends Parent {
    @Override
    public void printInfo(String name, int age) { // 方法名、参数列表与父类一致
        System.out.println("【子类】姓名：" + name + "，年龄：" + age);
    }
}
```
**示例（错误，非重写）**：

```java
class Parent {
    public void add(int a, int b) { ... }
}

class Child extends Parent {
    // 错误：参数列表不同（参数顺序改变且类型不同），不构成重写（是重载）
    public void add(double b, int a) { ... } 
}
```
## 2.2 返回值类型需兼容
子类重写方法的返回值类型必须与父类方法的返回值类型**相同**，或为父类返回值类型的**子类**（称为 “协变返回类型”）。

**示例（正确）**：

```java
// 父类
class Parent {
    public Number calculate() { // 返回值类型为Number
        return 0;
    }
}

// 子类（正确：返回值为Number的子类Integer）
class Child extends Parent {
    @Override
    public Integer calculate() { // Integer是Number的子类，兼容返回值
        return 100;
    }
}
```
**示例（错误）**：

```java
class Parent {
    public String getName() { ... }
}

class Child extends Parent {
    // 错误：返回值类型String与int不兼容
    @Override
    public int getName() { ... } 
}
```
## 2.3 访问权限不能严于父类
子类重写方法的访问权限（修饰符）必须**大于或等于**父类方法的访问权限，不能更严格（否则会缩小方法的可访问范围）。

Java 访问权限从宽到严排序：public > protected > default（无修饰符） > private。

**示例（正确）**：

```java
// 父类（protected权限）
class Parent {
    protected void method() { ... }
}

// 子类（正确：访问权限更宽public）
class Child extends Parent {
    @Override
    public void method() { ... } // public ≥ protected，合法
}
```
**示例（错误）**：

```java
// 父类（public权限）
class Parent {
    public void method() { ... }
}

// 子类（错误：访问权限更严格protected < public）
class Child extends Parent {
    @Override
    protected void method() { ... } // 编译错误
}
```
## 2.4 不能重写父类的私有方法
父类的private方法对子类完全隐藏（子类无法继承），因此子类中定义的与父类私有方法同名的方法，不构成重写（仅是子类的新方法）。

**示例**：

```java
class Parent {
    // 父类私有方法
    private void privateMethod() {
        System.out.println("父类私有方法");
    }
}

class Child extends Parent {
    // 不是重写：子类无法继承父类的private方法，此方法是子类的新方法
    public void privateMethod() { 
        System.out.println("子类的方法");
    }
}
```
## 2.5 不能重写被final修饰的方法
父类中被final修饰的方法是 “最终方法”，不能被子类重写（final的作用是禁止重写，保护方法实现不被修改）。

**示例（错误）**：

```java
class Parent {
    // 父类final方法，禁止重写
    public final void finalMethod() { ... }
}

class Child extends Parent {
    // 错误：不能重写final方法
    @Override
    public void finalMethod() { ... } // 编译错误
}
```
## 2.6 不能重写被static修饰的方法
静态方法属于类，不属于对象，子类中定义与父类同名的静态方法，不构成重写（而是 “隐藏” 父类的静态方法）。

**示例（不构成重写）**：

```java
class Parent {
    public static void staticMethod() {
        System.out.println("父类静态方法");
    }
}

class Child extends Parent {
    // 不是重写：静态方法不能被重写，此方法隐藏父类静态方法
    public static void staticMethod() {
        System.out.println("子类静态方法");
    }
}
```
- 调用时，静态方法的执行版本取决于**引用类型**（父类引用调用父类静态方法，子类引用调用子类静态方法），与对象实际类型无关（区别于重写的动态绑定）。
## 2.7 异常声明不能更宽泛
子类重写方法声明的**受检异常**（Checked Exception）必须是父类方法声明异常的**子类**，或不声明异常；不能声明父类方法没有的更宽泛的受检异常。

**示例（正确）**：

```java
import java.io.IOException;
import java.io.FileNotFoundException;

// 父类（声明IOException）
class Parent {
    public void readFile() throws IOException { ... }
}

// 子类（正确：声明IOException的子类FileNotFoundException）
class Child extends Parent {
    @Override
    public void readFile() throws FileNotFoundException { ... } // 合法
}
```
**示例（错误）**：

```java
class Parent {
    public void method() throws ExceptionA { ... }
}

class ExceptionB extends Exception { ... } // 与ExceptionA同级的异常

class Child extends Parent {
    // 错误：声明了父类没有的更宽泛异常ExceptionB
    @Override
    public void method() throws ExceptionB { ... } // 编译错误
}
```
# 三、@Override 注解的作用
@Override是 Java 的元注解，用于**标记方法为父类方法的重写方法**，编译器会对其进行校验，若不满足重写规则则报错，帮助开发者避免低级错误。

**示例**：

```java
class Parent {
    public void test() { ... }
}

class Child extends Parent {
    // 使用@Override注解，编译器会校验重写规则
    @Override
    public void test() { ... } // 正确重写
}
```
- **建议强制使用**：即使不使用@Override，符合规则的方法也能构成重写，但使用注解可显式表明意图，并利用编译器校验避免错误（如拼写错误导致方法名不一致）。
# 四、方法重写的最佳实践
1. **遵循 “里氏替换原则”**：子类重写方法后，通过父类引用调用该方法时，不应破坏程序的正确性（即 “子类对象可以替换父类对象，且程序行为不变”）。
2. **避免过度重写**：仅重写需要定制的方法，保留父类合理的默认实现，减少代码冗余。
3. **明确重写意图**：使用@Override注解，清晰标记重写方法，提高代码可读性。
4. **谨慎处理异常**：重写方法时，避免声明过多异常，或捕获父类方法的异常后进行更合理的处理（如转换为业务异常）。
# 五、总结
方法重写是子类定制父类行为的核心机制，必须严格遵循以下规则：
- 方法签名（名称、参数列表）完全相同；
- 返回值类型兼容（相同或为子类）；
- 访问权限不严于父类；
- 不能重写private、final、static方法；
- 异常声明不能更宽泛。

合理使用方法重写，能使子类在继承父类共性的同时，灵活定制自身特性，是 Java 面向对象中 “多态” 特性的基础。