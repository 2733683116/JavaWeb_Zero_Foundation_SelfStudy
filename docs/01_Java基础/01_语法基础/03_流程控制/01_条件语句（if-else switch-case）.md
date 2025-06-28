# 一、条件语句概述
条件语句是程序控制流的核心组成部分，用于根据不同条件执行不同的代码块。Java 提供两种主要的条件语句：
* **if-else 语句**：基于布尔表达式的真假进行分支判断，灵活性高
* **switch-case 语句**：基于变量的值进行多分支匹配，适合固定值判断

合理使用条件语句可以让程序根据不同场景做出响应，实现复杂的业务逻辑。
# 二、if-else 语句
## 2.1 基本语法
if-else 语句有三种形式：单分支 if、双分支 if-else、多分支 if-else if-else。
### 单分支 if

```java
if (条件表达式) {
    // 条件为true时执行的代码块
}
```
### 双分支 if-else

```java
if (条件表达式) {
    // 条件为true时执行
} else {
    // 条件为false时执行
}
```
### 多分支 if-else if-else

```java
if (条件1) {
    // 条件1为true时执行
} else if (条件2) {
    // 条件1为false且条件2为true时执行
} else if (条件3) {
    // 前面条件都为false且条件3为true时执行
} else {
    // 所有条件都为false时执行（可选）
}
```
## 2.2 执行流程
以多分支为例，执行流程为：
1. 依次判断条件 1、条件 2、条件 3...
2. 第一个为 true 的条件对应的代码块会被执行
3. 执行后跳出整个 if-else 结构，后续条件不再判断
4. 若所有条件都为 false，执行 else 代码块（若存在）
## 2.3 代码示例

```java
// 单分支示例：判断是否成年
int age = 18;
if (age >= 18) {
    System.out.println("已成年");
}

// 双分支示例：判断奇偶
int num = 7;
if (num % 2 == 0) {
    System.out.println("偶数");
} else {
    System.out.println("奇数");
}

// 多分支示例：成绩评级
int score = 85;
if (score >= 90) {
    System.out.println("优秀");
} else if (score >= 80) {
    System.out.println("良好");
} else if (score >= 60) {
    System.out.println("及格");
} else {
    System.out.println("不及格");
}
```
## 2.4 注意事项
1. **条件表达式必须是 boolean 类型**

```java
// 错误示例：条件表达式是int类型
if (10) { ... } // 编译错误

// 正确示例：条件表达式是boolean类型
if (10 > 5) { ... } // 正确
```
2. **代码块的省略与风险**
当代码块只有一行语句时，可省略{}，但建议始终保留以避免逻辑错误：

```java
// 不推荐：易误解执行范围
if (score >= 60)
    System.out.println("及格");
    System.out.println("继续努力"); // 无论条件是否成立都会执行

// 推荐：明确代码块范围
if (score >= 60) {
    System.out.println("及格");
    System.out.println("继续努力");
}
```
3. **悬空 else 问题**
else 始终与最近的未配对 if 匹配：

```java
// 容易误解的写法
if (a > b)
    if (b > c)
        System.out.println("a > b > c");
else // 实际与内层if配对
    System.out.println("a <= b");

// 清晰的写法
if (a > b) {
    if (b > c) {
        System.out.println("a > b > c");
    }
} else {
    System.out.println("a <= b");
}
```
# 三、switch-case 语句
## 3.1 基本语法
switch-case 用于基于变量的离散值进行多分支判断，语法：

```java
switch (表达式) {
    case 值1:
        // 表达式等于值1时执行
        break; // 可选，用于跳出switch
    case 值2:
        // 表达式等于值2时执行
        break;
    // ... 更多case
    default:
        // 所有case都不匹配时执行（可选）
}
```
## 3.2 执行流程
执行流程：
1. 计算 switch 后表达式的值（类型有限制）
2. 依次与 case 后的值比较，寻找匹配的 case
3. 执行匹配 case 后的代码，直到遇到 break 或 switch 结束
4. 若没有匹配的 case，执行 default 代码块（若存在）
## 3.3 表达式类型与 case 值要求
**支持的表达式类型**
* 基本类型：byte、short、int、char
* 包装类型：Byte、Short、Integer、Character
* 枚举类型（JDK 5+）
* 字符串类型（JDK 7+）

**case 值要求**
* 必须是常量或常量表达式（如10、'A'、"abc"）
* 不能重复（同一 switch 中 case 值唯一）
* 类型必须与 switch 表达式类型兼容

## 3.4 代码示例

```java
// 整数类型示例
int day = 3;
switch (day) {
    case 1:
        System.out.println("星期一");
        break;
    case 2:
        System.out.println("星期二");
        break;
    case 3:
        System.out.println("星期三");
        break;
    default:
        System.out.println("未知星期");
}

// 字符串类型示例（JDK 7+）
String season = "spring";
switch (season) {
    case "spring":
        System.out.println("春天");
        break;
    case "summer":
        System.out.println("夏天");
        break;
    default:
        System.out.println("其他季节");
}

// 省略break的穿透效果
int month = 2;
switch (month) {
    case 12:
    case 1:
    case 2:
        System.out.println("冬季");
        break;
    case 3:
    case 4:
    case 5:
        System.out.println("春季");
        break;
    // ... 其他季节
}
```
## 3.5 注意事项
1. **break 的作用**：没有 break 会导致 case 穿透（继续执行下一个 case 的代码）

```java
int num = 2;
switch (num) {
    case 1:
        System.out.println("1");
    case 2:
        System.out.println("2"); // 执行
    case 3:
        System.out.println("3"); // 穿透执行
        break;
    default:
        System.out.println("default");
}
// 输出：2  3
```
2. **default 的位置**：default 可放在任意位置，不影响 case 匹配，但通常放在最后

```java
switch (num) {
    default:
        System.out.println("default");
        break;
    case 1:
        System.out.println("1");
}
```
3. **字符串 case 的性能**：字符串 switch 本质是通过 hashCode 和 equals 实现，性能略低于整数 switch
# 四、if-else 与 switch-case 的对比
|特性|if-else|switch-case|
|--|--|--|
| 判断依据 | 布尔表达式（可复杂） |	变量值（离散值） |
 |分支数量 |	适合任意数量分支	 |适合多分支（3 个以上更具优势） |
 |灵活性	 |高（支持范围、逻辑组合等） |	低（仅支持等值匹配） |
 |执行效率 |	分支越多效率可能越低（依次判断） |	效率较高（通过跳转表实现） |
 |适用场景 |	范围判断、复杂逻辑组合 |	固定值匹配（如菜单选择、状态码） |

## 4.1 选择建议
* 当判断条件是**范围或复杂逻辑**时，用 if-else：

```java
// 适合if-else：范围判断
if (score >= 0 && score <= 100) { ... }
```

* 当判断条件是**多个离散值**时，用 switch-case：

```java
// 适合switch-case：固定值匹配
switch (statusCode) {
    case 200: ...; break;
    case 404: ...; break;
    case 500: ...; break;
}
```
# 五、常见错误与最佳实践
## 5.1 常见错误
1. **if-else 的条件顺序错误**

```java
// 错误示例：条件1包含条件2，条件2永远不会执行
if (score >= 60) {
    System.out.println("及格");
} else if (score >= 80) {
    System.out.println("良好"); // 永远不会执行
}
```
**解决方案**：按条件范围从大到小或从小到大排序

```java
// 正确：从高到低排序
if (score >= 80) { ... }
else if (score >= 60) { ... }
```
2. **switch-case 缺少 break 导致穿透**

```java
// 错误示例：缺少break导致错误输出
switch (num) {
    case 1:
        System.out.println("1");
    case 2:
        System.out.println("2");
}
// 当num=1时，输出"1 2"（非预期）
```
**解决方案**：每个 case 后添加 break（除了有意利用穿透的场景）

3. **过度嵌套导致可读性差**

```java
// 不推荐：嵌套过深
if (a > b) {
    if (b > c) {
        if (c > d) {
            // ...
        }
    }
}
```
**解决方案**：合并条件或提取为方法

```java
// 推荐：合并条件
if (a > b && b > c && c > d) {
    // ...
}
```
## 5.2 最佳实践
1. **保持分支互斥**：确保分支条件不重叠，避免逻辑混乱
2. **控制嵌套深度**：嵌套不超过 3 层，超过则拆分代码
3. **优先使用常量**：switch 的 case 值使用常量定义，提高可维护性

```java
public static final int STATUS_OK = 200;
public static final int STATUS_ERROR = 500;

switch (status) {
    case STATUS_OK: ...; break;
    case STATUS_ERROR: ...; break;
}
```
4. **default 分支处理**：始终添加 default 分支处理异常情况（除非确定所有情况都被覆盖）
# 六、总结
Java 条件语句是控制程序流程的核心工具：
* if-else通过布尔表达式实现灵活的分支判断，适合范围和复杂逻辑
* switch-case通过离散值实现多分支匹配，适合固定值判断
* 合理选择条件语句类型可提高代码可读性和效率
* 注意条件顺序、break 使用和嵌套深度，避免常见逻辑错误

掌握条件语句的使用是编写逻辑清晰、可靠程序的基础，在实际开发中需根据具体场景选择合适的条件控制结构。