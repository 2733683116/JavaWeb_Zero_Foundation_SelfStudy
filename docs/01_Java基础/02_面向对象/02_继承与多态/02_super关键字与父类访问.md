# 一、super 关键字的基本概念
super是 Java 中的一个特殊关键字，与this类似，但其作用是**引用当前对象的父类实例**。通过super，子类可以访问父类中被隐藏的成员（属性、方法）或调用父类的构造器，是连接子类与父类的重要桥梁。

super的核心作用是**明确指定访问父类的成员**，尤其在子类与父类存在同名成员时，用于区分两者的成员，避免歧义。

![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/104a48c657a84948b0c9eabf08ebfc3a.png#pic_center)


# 二、super 关键字的核心使用场景
## 2.1 场景 1：访问父类的属性（super. 属性名）
当子类的属性与父类的属性同名时，使用super.属性名明确访问父类的属性，避免被子类的同名属性 “隐藏”。

**示例代码**：

```java
// 父类
class Animal {
    protected String name = "动物"; // 父类属性
}

// 子类
class Dog extends Animal {
    private String name = "狗"; // 子类属性（与父类同名）
    
    public void showNames() {
        System.out.println("子类的name：" + name); // 访问子类属性 → 输出“狗”
        System.out.println("父类的name：" + super.name); // 访问父类属性 → 输出“动物”
    }
}

// 测试
public class Test {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.showNames();
    }
}
```
**说明**：
- 子类的name属性会 “隐藏” 父类的同名属性，直接使用name访问的是子类自己的属性。
- super.name强制访问父类的name属性，解决同名属性的冲突问题。
## 2.2 场景 2：调用父类的方法（super. 方法名 (参数)）
当子类重写了父类的方法后，使用super.方法名(参数)可以调用父类中被重写的方法，而非子类的重写版本。

**示例代码**：

```java
// 父类
class Animal {
    public void eat() { // 父类方法
        System.out.println("动物在吃东西");
    }
}

// 子类
class Dog extends Animal {
    // 重写父类的eat()方法
    @Override
    public void eat() {
        System.out.println("狗在吃骨头");
    }
    
    public void showParentEat() {
        super.eat(); // 调用父类被重写的eat()方法 → 输出“动物在吃东西”
    }
}

// 测试
public class Test {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.eat(); // 调用子类重写的方法 → 输出“狗在吃骨头”
        dog.showParentEat(); // 调用父类的方法 → 输出“动物在吃东西”
    }
}
```
**说明**：
- 子类重写父类方法后，默认情况下调用的是子类的版本。
- super.eat()强制调用父类的eat()方法，保留父类的原始逻辑。
## 2.3 场景 3：调用父类的构造器（super (参数)）
子类的构造器中，使用super(参数列表)可以调用父类的指定构造器，用于初始化父类的成员，是子类构造器中常见的操作。

**示例代码**：

```java
// 父类
class Person {
    protected String name;
    protected int age;
    
    // 父类的有参构造器
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

// 子类
class Student extends Person {
    private String school;
    
    // 子类的构造器
    public Student(String name, int age, String school) {
        // 调用父类的有参构造器，初始化name和age
        super(name, age); 
        this.school = school; // 初始化子类自己的属性
    }
    
    public void showInfo() {
        System.out.println("姓名：" + name + "，年龄：" + age + "，学校：" + school);
    }
}

// 测试
public class Test {
    public static void main(String[] args) {
        Student student = new Student("张三", 18, "一中");
        student.showInfo(); // 输出“姓名：张三，年龄：18，学校：一中”
    }
}
```
**说明**：
- 子类构造器中，super(name, age)调用父类的Person(String, int)构造器，完成父类属性的初始化。
- **注意**：super(...)必须放在子类构造器的**第一行**（否则编译错误），确保父类成员先于子类成员初始化。
## 2.4 场景 4：默认调用父类的无参构造器
如果子类构造器中没有显式使用super(...)，编译器会**自动添加super()**，即默认调用父类的无参构造器。

**示例代码**：

```java
// 父类
class Animal {
    // 父类的无参构造器
    public Animal() {
        System.out.println("父类无参构造器被调用");
    }
}

// 子类
class Cat extends Animal {
    // 子类构造器（未显式调用super()）
    public Cat() {
        // 编译器自动添加：super();
        System.out.println("子类构造器被调用");
    }
}

// 测试
public class Test {
    public static void main(String[] args) {
        Cat cat = new Cat();
        // 输出：
        // 父类无参构造器被调用
        // 子类构造器被调用
    }
}
```
**注意事项**：
- 若父类**没有无参构造器**（如只定义了有参构造器），子类构造器中必须**显式调用父类的有参构造器**（super(参数)），否则编译错误。

**错误示例**：
```java
// 父类（只有有参构造器，无默认无参构造器）
class Animal {
    public Animal(String name) {
        // 父类有参构造器
    }
}

// 子类（编译错误）
class Cat extends Animal {
    public Cat() {
        // 错误：父类没有无参构造器，编译器无法自动添加super()
    }
}
```
**解决方法（显式调用父类有参构造器）**：

```java
class Cat extends Animal {
    public Cat() {
        super("小猫"); // 显式调用父类的有参构造器
    }
}
```
# 三、super 与 this 的对比
super和this都用于引用对象成员，但作用对象不同，对比如下：
|关键字|	引用对象	|主要用途	|调用构造器的位置|
|-|-|-|-|
|this|	当前对象|	访问当前对象的属性、方法；调用当前类的其他构造器（this(...)）|	必须放在构造器第一行|
|super|	当前对象的父类实例	|访问父类的属性、方法；调用父类的构造器（super(...)）|	必须放在构造器第一行|

**注意**：同一构造器中，this(...)和super(...)不能同时使用（两者都要求放在第一行，存在冲突）。
# 四、使用 super 的注意事项
1. **只能在非静态方法和构造器中使用**：super依赖于具体的对象实例，静态方法中不能使用（静态方法属于类，不关联对象）。
2. **不能访问父类的私有成员**：父类的private属性和方法对子类完全隐藏，即使使用super也无法访问（需通过父类的public/protected方法间接访问）。
**示例**：

```java
class Parent {
    private int id; // 私有属性
}

class Child extends Parent {
    public void test() {
        // super.id; // 错误：无法访问父类的private属性
    }
}
```
3. **避免过度依赖 super**：子类应尽量通过重写父类方法扩展功能，而非频繁使用super调用父类方法，否则会增加子类与父类的耦合度。
# 五、总结
super关键字是子类访问父类成员的核心工具，主要使用场景包括：
- 访问父类的属性（super.属性名），解决与子类同名属性的冲突。
- 调用父类的方法（super.方法名(参数)），尤其是调用被重写的父类方法。
- 调用父类的构造器（super(参数)），完成父类成员的初始化，是子类构造器的必要操作。

理解super的使用规则，能帮助开发者正确处理子类与父类的继承关系，避免成员访问冲突，确保继承体系的正确性和灵活性。
