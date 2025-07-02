# 一、访问修饰符概述
访问修饰符（Access Modifiers）是 Java 中用于控制类、属性、方法和构造器访问范围的关键字。它们决定了在不同位置（同一类、同一包、不同包的子类、不同包的非子类）能否访问目标成员，是封装特性的核心实现手段。

Java 提供四种访问修饰符，按访问权限从大到小排序为：
- public（公共的）
- protected（受保护的）
- default（默认的，无关键字）
- private（私有的）

![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/6bc58926c2564045af4637a4b5df8bf0.png#pic_center)

# 二、四种访问修饰符的权限范围
## 2.1 权限范围总览
|修饰符	|同一类中	|同一包中（非子类）	|不同包的子类中	|不同包的非子类中|
|-|-|-|-|-|
|public	|✔️	|✔️	|✔️	|✔️|
|protected	|✔️	|✔️	|✔️	|❌|
|default	|✔️	|✔️	|❌	|❌|
|private	|✔️	|❌	|❌	|❌|
## 2.2 详细说明
### 2.2.1 private（私有的）
- **权限范围**：仅在**当前类内部**可访问，其他任何位置（包括同包类、子类）都无法直接访问。
- **适用场景**：类的内部实现细节（如私有属性、辅助方法），仅通过类内部的公共方法暴露功能，实现封装。

示例：

```java
public class Person {
    // 私有属性：仅Person类内部可访问
    private String idCard;
    
    // 私有方法：仅Person类内部可调用
    private void checkIdFormat() {
        // 验证身份证格式的逻辑
    }
    
    // 公共方法：对外暴露访问私有属性的接口
    public String getMaskedId() {
        checkIdFormat(); // 类内部可调用私有方法
        return idCard.substring(0, 6) + "********" + idCard.substring(14);
    }
}

// 同一包中的其他类
class PersonTest {
    public static void main(String[] args) {
        Person p = new Person();
        // System.out.println(p.idCard); // 编译错误：private属性不可访问
        // p.checkIdFormat(); // 编译错误：private方法不可调用
        System.out.println(p.getMaskedId()); // 合法：通过公共方法访问
    }
}
```
### 2.2.2 default（默认的，无关键字）
- **权限范围**：仅在**当前类**和**同一包中的类**（无论是否为子类）可访问，不同包中无法访问（包括子类）。
- **适用场景**：仅需在当前包内共享的成员，避免对外暴露。

示例：

```java
// 包：com.example.entity
package com.example.entity;

public class Student {
    // 默认权限属性：仅同包类可访问
    String school; // 无修饰符，即default
    
    // 默认权限方法：仅同包类可调用
    void showSchool() {
        System.out.println(school);
    }
}

// 同一包中的类（可访问）
package com.example.entity;

class StudentTest {
    public static void main(String[] args) {
        Student s = new Student();
        s.school = "阳光小学"; // 合法：同包可访问default属性
        s.showSchool(); // 合法：同包可调用default方法
    }
}

// 不同包的类（不可访问）
package com.example.service;
import com.example.entity.Student;

class StudentService {
    public static void main(String[] args) {
        Student s = new Student();
        // s.school = "阳光小学"; // 编译错误：不同包不可访问default属性
        // s.showSchool(); // 编译错误：不同包不可调用default方法
    }
}
```
### 2.2.3 protected（受保护的）
- **权限范围**：在**当前类**、**同一包中的类（无论是否为子类）**、**不同包的子类**中可访问；**不同包的非子类**中不可访问。
- **适用场景**：需要被子类继承和重写的成员，同时限制非子类的访问。

示例：

```java
// 父类所在包：com.example.base
package com.example.base;

public class Animal {
    // 受保护属性
    protected String name;
    
    // 受保护方法
    protected void eat() {
        System.out.println(name + "在吃东西");
    }
}

// 同一包中的非子类（可访问）
package com.example.base;

class AnimalTest {
    public static void main(String[] args) {
        Animal a = new Animal();
        a.name = "小狗"; // 合法：同包可访问protected属性
        a.eat(); // 合法：同包可调用protected方法
    }
}

// 不同包的子类（可访问）
package com.example.sub;
import com.example.base.Animal;

public class Dog extends Animal {
    public void bark() {
        name = "旺财"; // 合法：子类可访问父类的protected属性
        eat(); // 合法：子类可调用父类的protected方法
    }
}

// 不同包的非子类（不可访问）
package com.example.service;
import com.example.base.Animal;

class AnimalService {
    public static void main(String[] args) {
        Animal a = new Animal();
        // a.name = "小猫"; // 编译错误：不同包非子类不可访问
        // a.eat(); // 编译错误：不同包非子类不可调用
    }
}
```
### 2.2.4 public（公共的）
- **权限范围**：在**所有位置**（同一类、同一包、不同包的子类和非子类）均可访问，无任何限制。
- **适用场景**：需要被广泛访问的类、接口或公共方法（如工具类的静态方法、接口的抽象方法）。

示例：

```java
// 公共类所在包：com.example.util
package com.example.util;

public class MathTool {
    // 公共静态方法
    public static int max(int a, int b) {
        return a > b ? a : b;
    }
}

// 不同包的非子类（可访问）
package com.example.app;
import com.example.util.MathTool;

public class App {
    public static void main(String[] args) {
        int result = MathTool.max(10, 20); // 合法：public方法可跨包访问
        System.out.println(result); // 输出20
    }
}
```
# 三、访问修饰符的适用对象
访问修饰符可用于修饰类、属性、方法和构造器，但存在部分限制：

|修饰符	|类	|属性	|方法	|构造器|
|-|-|-|-|-|
|public	|✔️	|✔️	|✔️	|✔️|
|protected	|❌（外部类不可用）	|✔️	|✔️	|✔️|
|default	|✔️	|✔️	|✔️	|✔️|
|private	|❌（外部类不可用）	|✔️	|✔️	|✔️|

- **类的修饰限制**：外部类只能用public或default修饰（内部类可使用所有修饰符）。
- **构造器的修饰**：通过修饰构造器可控制类的实例化权限（如private构造器使类无法在外部实例化，常用于单例模式）。
# 四、使用访问修饰符的最佳实践
1. **最小权限原则**：给成员分配尽可能小的权限，仅暴露必要的功能，减少外部依赖和误操作。
- 示例：属性通常设为private，通过public的getter/setter方法访问，便于控制读写逻辑。
2. **封装属性**：优先将属性声明为private，避免直接暴露给外部，如需访问则提供public或protected的方法。

```java
public class User {
    private String username; // 私有属性
    
    // 公共getter方法
    public String getUsername() {
        return username;
    }
    
    // 公共setter方法，可添加校验逻辑
    public void setUsername(String username) {
        if (username != null && username.length() <= 20) {
            this.username = username;
        }
    }
}
```
3. **控制继承范围**：如需允许子类重写方法，用protected；如需禁止重写，结合final（如public final void method()）。
4. **避免滥用public**：仅将需要跨包广泛访问的成员设为public，其他成员根据范围选择protected或default。
5. **构造器权限控制**：通过private构造器限制类的实例化（如工具类），通过protected构造器限制子类的实例化范围。
# 五、常见错误与注意事项
1. **混淆default和protected**：protected在不同包的子类中可访问，而default不可，需根据是否允许子类跨包访问选择。
2. **过度使用public**：将所有成员设为public会破坏封装，导致类的内部实现暴露，增加维护难度。
3. **忽略包的影响**：default和protected的权限与包密切相关，跨包访问时需注意类的导入和继承关系。
4. **内部类的修饰符**：内部类可使用所有修饰符，但其权限范围仍遵循上述规则（如内部类的private成员仅内部类可见）。
# 六、总结
访问修饰符是 Java 实现封装和权限控制的核心机制，其核心作用是**控制成员的访问范围**，确保代码的安全性和可维护性：
- private：仅当前类可见，用于隐藏内部实现。
- default：仅同一包可见，用于包内共享。
- protected：同一包或不同包的子类可见，用于允许继承。
- public：全局可见，用于广泛共享的功能。

合理使用访问修饰符，遵循最小权限原则，能有效降低代码耦合度，提高系统的健壮性。