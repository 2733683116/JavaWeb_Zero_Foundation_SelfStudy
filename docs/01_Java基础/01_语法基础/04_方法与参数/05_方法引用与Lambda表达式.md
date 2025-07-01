# 一、概述
Java 8 引入了**Lambda 表达式**和**方法引用**，两者均用于简化函数式编程，尤其在处理集合、多线程等场景中能显著减少代码冗余。
- **Lambda 表达式**：一种匿名函数，可作为参数传递，用于简化函数式接口的实现。
- **方法引用**：Lambda 表达式的简化形式，当 Lambda 体仅调用一个已存在的方法时，可通过方法引用进一步简化代码。
![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/6fa2d0a7fdac4265869533368ed0d06b.png#pic_center)

# 二、Lambda 表达式
## 2.1 定义与核心作用
Lambda 表达式是**没有名称的匿名函数**，其核心作用是**简化函数式接口的实现代码**，使代码更简洁、紧凑。
- **函数式接口**：仅包含一个抽象方法的接口（可包含默认方法或静态方法），如Runnable（run()）、Comparator（compare(T o1, T o2)）。Lambda 表达式本质上是函数式接口抽象方法的实现。
## 2.2 语法结构
Lambda 表达式的基本语法：

> (参数列表) -> { 方法体 }

- **参数列表**：省略参数类型（编译器可推断），多个参数用逗号分隔；无参数时用()表示。
- **箭头操作符（->）**：连接参数列表和方法体，读作 “goes to”。
- **方法体**：若只有一条语句，可省略{}和return；若有多条语句，需用{}包裹并显式使用return。
## 2.3常见语法示例
|场景	|Lambda 表达式	|说明|
|-|-|-|
|无参数，无返回值	|() -> System.out.println("Hello")	|对应接口方法：void method()|
|单参数，无返回值	|s -> System.out.println(s)	|对应接口方法：void method(String s)（参数类型可省略）|
|多参数，有返回值	|(a, b) -> a + b	|对应接口方法：int method(int a, int b)（单语句省略{}和return）|
|多参数，复杂逻辑	|(x, y) -> { int max = x > y ? x : y; return max; }	|多语句需{}和return|

## 2.4 应用场景
**场景 1：线程创建（Runnable接口）**
```java
// 传统：匿名内部类
new Thread(new Runnable() {
    @Override
    public void run() {
        System.out.println("线程运行中");
    }
}).start();

// Lambda简化
new Thread(() -> System.out.println("线程运行中")).start();
```
**场景 2：集合排序（Comparator接口）**
```java
List<String> list = Arrays.asList("apple", "banana", "cherry");

// 传统：匿名内部类排序
Collections.sort(list, new Comparator<String>() {
    @Override
    public int compare(String s1, String s2) {
        return s1.length() - s2.length(); // 按长度排序
    }
});

// Lambda简化
Collections.sort(list, (s1, s2) -> s1.length() - s2.length());
```
**场景 3：自定义函数式接口**

```java
// 定义函数式接口（仅一个抽象方法）
@FunctionalInterface // 注解校验是否为函数式接口
interface Calculator {
    int calculate(int a, int b);
}

// 使用Lambda实现
public static void main(String[] args) {
    Calculator add = (a, b) -> a + b;
    System.out.println(add.calculate(3, 5)); // 输出8
}
```
## 2.5 注意事项
1. **仅适用于函数式接口**：Lambda 表达式只能赋值给函数式接口类型的变量，或作为参数传递给接收函数式接口的方法。
2. **变量捕获**：Lambda 可访问外部的final或 “事实上 final”（未声明final但未修改）的变量。

```java
int num = 10; // 事实上final（未修改）
Calculator mul = (a, b) -> a * b + num; // 可访问num
```
3. **可读性权衡**：过于复杂的 Lambda 体（多语句）会降低可读性，此时建议改用普通方法。
# 三、方法引用
## 3.1 定义与核心作用
方法引用是**Lambda 表达式的简化形式**，当 Lambda 体仅调用一个已存在的方法（无其他逻辑）时，可通过方法引用直接引用该方法，使代码更简洁。

> 语法：类名/对象名::方法名（::为方法引用运算符）

## 3.2 方法引用的类型及示例
根据引用的方法类型，方法引用可分为 4 类：
### 3.2.1 静态方法引用
**语法**：类名::静态方法名
**适用场景**：Lambda 体调用的是某类的静态方法。
示例：

```java
// 函数式接口：接收两个整数，返回整数
interface IntOperation {
    int operation(int a, int b);
}

// 静态方法：计算两数之和
class MathUtil {
    public static int add(int a, int b) {
        return a + b;
    }
}

// 使用：静态方法引用替代Lambda
public static void main(String[] args) {
    // Lambda写法：(a, b) -> MathUtil.add(a, b)
    IntOperation addOp = MathUtil::add; // 静态方法引用
    System.out.println(addOp.operation(2, 3)); // 输出5
}
```
### 3.2.2 实例方法引用（对象的实例方法）
**语法**：对象名::实例方法名
**适用场景**：Lambda 体调用的是某个对象的实例方法。
示例：

```java
// 函数式接口：接收字符串，返回其长度
interface StringFunc {
    int getLength(String s);
}

public static void main(String[] args) {
    // 创建字符串对象
    String str = "hello";
    // Lambda写法：s -> str.length()（但此处s未使用，因length()无参数）
    StringFunc lenFunc = str::length; // 实例方法引用
    System.out.println(lenFunc.getLength("world")); // 输出5（实际调用str.length()，参数"world"未被使用）
}
```

> ==**注：若实例方法有参数，Lambda 的参数需与方法参数匹配。例如：
> String::equals 对应 Lambda (s1, s2) -> s1.equals(s2)**==
### 3.2.3 类的实例方法引用（任意对象的实例方法）
**语法**：类名::实例方法名
**适用场景**：Lambda 的第一个参数是方法的调用者，后续参数是方法的参数。
示例：

```java
// 函数式接口：接收两个字符串，返回布尔值
interface StringCompare {
    boolean compare(String s1, String s2);
}

public static void main(String[] args) {
    // Lambda写法：(s1, s2) -> s1.startsWith(s2)
    StringCompare startWith = String::startsWith; // 类的实例方法引用
    System.out.println(startWith.compare("apple", "app")); // 输出true（"apple".startsWith("app")）
}
```
> ==**原理：String::startsWith 中，第一个参数s1是方法的调用者（s1.startsWith(...)），第二个参数s2是方法的参数。**==
### 3.2.4 构造器引用
**语法**：类名::new
**适用场景**：Lambda 体用于创建对象（调用构造器）。
示例：

```java
// 函数式接口：接收整数，返回String对象（创建指定长度的字符串）
interface StringCreator {
    String create(int length);
}

public static void main(String[] args) {
    // Lambda写法：length -> new String(new char[length])
    StringCreator creator = String::new; // 构造器引用（匹配String(char[] value)）
    System.out.println(creator.create(5).length()); // 输出5（创建长度为5的空字符串）
}
```
> ==**注：构造器引用会根据函数式接口的参数列表匹配对应的构造器（如无参构造器、单参构造器等）。**==

## 3.3 方法引用与 Lambda 表达式的转换
方法引用是 Lambda 的简化形式，两者可相互转换。例如：
|场景|	Lambda 表达式|	方法引用|
|-|-|-|
|静态方法调用	(a, b)| -> Integer.compare(a, b)|	Integer::compare|
|实例方法调用（对象）|	s -> System.out.println(s)|	System.out::println|
|类的实例方法调用|	(s1, s2) -> s1.contains(s2)|	String::contains|
|构造器调用|	() -> new ArrayList<>()|	ArrayList::new|
# 四、Lambda 表达式与方法引用的对比
|特性|	Lambda 表达式|	方法引用|
|-|-|-|
|本质|	匿名函数，包含完整逻辑|	简化的 Lambda，仅引用现有方法|
|语法复杂度|	较高（需写参数和方法体）|	较低（直接引用方法）|
|适用场景|	有自定义逻辑（多语句或复杂计算）|	无自定义逻辑，仅调用现有方法|
|可读性|	简单逻辑可读性好，复杂逻辑差|	引用的方法名能直观表达逻辑，可读性更好|

**示例对比**

```java
// 集合排序：按字符串长度升序
List<String> list = Arrays.asList("apple", "banana", "cherry");

// 传统匿名内部类
list.sort(new Comparator<String>() {
    @Override
    public int compare(String s1, String s2) {
        return s1.length() - s2.length();
    }
});

// Lambda表达式
list.sort((s1, s2) -> s1.length() - s2.length());

// 方法引用（结合String类的实例方法）
list.sort(Comparator.comparingInt(String::length)); // 更简洁
```
# 五、应用场景总结
## 5.1 集合操作（Stream API）
Lambda 和方法引用是Stream（Java 8 集合处理工具）的核心，用于过滤、映射、排序等操作：

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// 过滤偶数并求和（使用Lambda）
int sumEven = numbers.stream()
    .filter(n -> n % 2 == 0) // Lambda：过滤偶数
    .mapToInt(n -> n)
    .sum();

// 打印所有元素（使用方法引用）
numbers.stream()
    .forEach(System.out::println); // 方法引用：简化输出逻辑
```
## 5.2 多线程与并发
简化线程创建、Runnable/Callable接口实现：

```java
// 线程创建（Lambda）
new Thread(() -> {
    for (int i = 0; i < 3; i++) {
        System.out.println("线程运行：" + i);
    }
}).start();

// 线程池任务（方法引用）
ExecutorService executor = Executors.newSingleThreadExecutor();
executor.submit(new Task()::run); // 引用Task对象的run()方法
```
## 5.3 事件监听
简化 GUI 或框架中的事件处理器（如按钮点击事件）：

```java
// 模拟按钮点击事件（函数式接口）
interface OnClickListener {
    void onClick();
}

class Button {
    public void setOnClickListener(OnClickListener listener) {
        listener.onClick();
    }
}

// 使用方法引用
public static void main(String[] args) {
    Button btn = new Button();
    btn.setOnClickListener(new EventHandler()::handleClick); // 引用EventHandler的实例方法
}

class EventHandler {
    public void handleClick() {
        System.out.println("按钮被点击");
    }
}
```
# 六、注意事项
1. 函数式接口是前提：Lambda 和方法引用仅能用于函数式接口（需确保接口只有一个抽象方法）。
2. 方法引用的匹配性：方法引用的参数列表和返回值必须与函数式接口的抽象方法一致，否则编译错误。
3. 可读性优先：
	- 简单逻辑用方法引用（更简洁）；
	- 复杂逻辑用 Lambda（需多语句）；
	- 极复杂逻辑改用普通方法（避免 Lambda / 方法引用可读性下降）。
4. 版本兼容性：需 Java 8 及以上版本支持。

# 七、总结
- Lambda 表达式：通过(参数) -> 逻辑简化函数式接口的实现，适合有自定义逻辑的场景。
- 方法引用：通过类名/对象名::方法名进一步简化 Lambda，适合仅调用现有方法的场景。
- 两者均基于函数式接口，是 Java 函数式编程的核心，能显著简化集合操作、多线程等场景的代码。

合理使用 Lambda 和方法引用，可在保证可读性的前提下，大幅提升代码的简洁性和开发效率。