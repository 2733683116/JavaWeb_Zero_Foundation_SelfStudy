# 一、三元运算符概述
三元运算符（也称为条件运算符）是 Java 中唯一需要三个操作数的运算符，语法格式为：

> 条件表达式 ? 表达式1 : 表达式2;

其功能是根据**条件表达式**的结果（true或false），返回**表达式 1**或**表达式 2**的值。三元运算符可视为简化的if-else语句，用于根据条件选择不同的结果。
## 1.1 基本特性
* 属于双目运算符的扩展形式，需三个操作数
* 结果类型由表达式 1 和表达式 2 的类型决定（需兼容）
* 优先级低于关系运算符和算术运算符，高于赋值运算符
* 结合性为从右到左
# 二、三元运算符的语法与使用
## 2.1 基本用法

```java
// 语法：条件 ? 结果1 : 结果2
int age = 18;
String result = age >= 18 ? "成年" : "未成年";
System.out.println(result); // 输出"成年"
```
## 2.2 执行流程
1. 计算条件表达式的值（必须是boolean类型）
2. 若结果为true，执行并返回表达式 1的值
3. 若结果为false，执行并返回表达式 2的值
## 2.3 示例：数值比较

```java
int a = 10;
int b = 20;
int max = a > b ? a : b; // 比较a和b，返回较大值
System.out.println(max); // 输出20
```
## 2.4 示例：嵌套使用
三元运算符支持嵌套（但建议嵌套层数不超过 2 层，避免可读性下降）：

```java
int score = 85;
String grade = score >= 90 ? "优秀" : 
               score >= 60 ? "及格" : "不及格";
System.out.println(grade); // 输出"及格"
```
# 三、三元运算符与 if-else 的对比
|特性|三元运算符|if-else 语句|
|--|--|--|
| 返回值 | 有返回值，可直接参与表达式 | 无返回值，需通过变量存储结果 |
| 用途 | 简单条件判断，直接返回结果 | 复杂条件判断，可包含多条语句 |
| 语法简洁性 | 更简洁，一行代码完成 | 	较繁琐，需多行代码 |
| 执行逻辑 | 只能执行单个表达式 | 可执行代码块（多条语句） |
| 适用场景 | 	简单的二选一逻辑 | 复杂分支或需执行多步操作 |
## 3.1 等价转换示例

```java
// 三元运算符
int max1 = a > b ? a : b;

// 等价的if-else
int max2;
if (a > b) {
    max2 = a;
} else {
    max2 = b;
}
```
## 3.2 各自优势场景
* **三元运算**符适合简单赋值场景：

```java
String msg = isSuccess ? "操作成功" : "操作失败";
```
* **if-else**适合复杂逻辑场景：

```java
if (isSuccess) {
    log.info("操作成功");
    return 200;
} else {
    log.error("操作失败");
    return 500;
}
```
# 四、三元运算符的类型兼容性
## 4.1 表达式类型需兼容
表达式 1 和表达式 2 的类型必须兼容（可自动转换或强制转换），否则编译错误：

```java
// 合法：int和double兼容（int自动转为double）
double result1 = 10 > 5 ? 20 : 3.14;

// 合法：String和Object兼容（String是Object子类）
Object result2 = flag ? "Hello" : new Object();

// 编译错误：int和String无法兼容
// String result3 = flag ? 100 : "error";
```
## 4.2 类型转换规则
当表达式 1 和表达式 2 类型不同时，结果类型为两者的**共同父类型**：

```java
int i = 10;
long l = 20;
// 结果类型为long（int自动转为long）
long result = i > 5 ? i : l;
```

# 五、使用注意事项
## 5.1 避免过度嵌套
嵌套层数过多会严重降低可读性，建议不超过 2 层：

```java
// 不推荐：嵌套过深，难以理解
String level = score >= 90 ? "A" : 
               score >= 80 ? "B" : 
               score >= 60 ? "C" : "D";

// 推荐：改用if-else
String level;
if (score >= 90) {
    level = "A";
} else if (score >= 80) {
    level = "B";
} else if (score >= 60) {
    level = "C";
} else {
    level = "D";
}
```
## 5.2 条件表达式必须是 boolean 类型
条件表达式的结果必须为boolean（true或false），不能用其他类型替代：

```java
// 编译错误：条件表达式必须是boolean类型
// int result = (a = 10) ? 20 : 30;
```
## 5.3 避免在表达式中包含副作用操作
表达式 1 或表达式 2 中包含自增、自减等修改变量的操作时，可能导致逻辑混乱：

```java
int a = 5;
int b = 10;
// 不推荐：表达式中包含自增操作
int result = a > b ? a++ : b++;
System.out.println(a); // 输出5（a未自增）
System.out.println(b); // 输出11（b自增）
```
## 5.4 注意 null 值处理
当表达式可能返回null时，需避免空指针异常：

```java
String str = flag ? "Hello" : null;
int length = str != null ? str.length() : 0; // 安全处理
```
# 六、常见错误与解决方案
## 6.1 类型不兼容错误
**错误示例**：

```java
// 编译错误：int和String类型不兼容
String result = isPositive ? 1 : "negative";
```
**解决方案**：统一表达式类型

```java
String result = isPositive ? "1" : "negative"; // 均为String类型
```
## 6.2 条件表达式非 boolean 类型
**错误示例**：

```java
// 编译错误：条件表达式是int类型
int x = 10;
int y = x ? 20 : 30;
```
**解决方案**：改为 boolean 条件

```java
int y = x > 0 ? 20 : 30; // 条件为boolean类型
```
## 6.3 过度嵌套导致可读性差
**错误示例**：

```java
int result = a > b ? (c > d ? c : d) : (e > f ? e : f);
```
**解决方案**：拆分或改用 if-else

```java
int temp = c > d ? c : d;
int result = a > b ? temp : (e > f ? e : f);
```
# 七、最佳实践
1. 控制嵌套层数：最多嵌套 1 层，超过则改用 if-else
2. 保持表达式简洁：表达式 1 和表达式 2 尽量为简单表达式（避免包含复杂运算）
3. 优先考虑可读性：当三元运算符不能提升可读性时，果断使用 if-else
4. 统一类型：确保表达式 1 和表达式 2 类型兼容，必要时显式转换
5. 避免副作用：不在表达式中执行修改变量、调用耗时方法等操作
# 八、总结
三元运算符（condition ? expr1 : expr2）是 Java 中简洁的条件判断工具，核心特点：
* 语法简洁，适合简单的二选一逻辑
* 有返回值，可直接参与表达式运算
* 表达式 1 和表达式 2 需类型兼容
* 可视为简化的 if-else，但不适合复杂逻辑

合理使用三元运算符能简化代码、提高效率，但需注意可读性和类型兼容性。在实际开发中，应根据场景选择三元运算符或 if-else，平衡简洁性与可维护性。