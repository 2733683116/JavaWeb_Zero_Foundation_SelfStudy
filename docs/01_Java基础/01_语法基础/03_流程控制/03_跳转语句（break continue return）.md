# 一、跳转语句概述
跳转语句用于改变程序的执行流程，实现代码块间的跳转。Java 提供三种主要跳转语句：
* break：终止当前循环或 switch 语句
* continue：跳过循环体剩余部分，进入下一次循环
* return：结束当前方法的执行并返回结果

跳转语句能帮助我们更灵活地控制程序流程，如提前终止循环、跳过无效迭代、结束方法执行等。
# 二、break 语句
## 2.1 功能与语法
break用于**立即终止**当前所在的循环（for、while、do-while）或 switch-case 语句，跳出该代码块继续执行后续代码。
**语法**：

> break;
## 2.2 在循环中的应用
![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/825894e170e74593a9988e9fc8e7c801.png#pic_center)

**基本用法**

```java
// 查找数组中的目标元素，找到后立即终止循环
int[] nums = {10, 20, 30, 40};
int target = 30;
for (int num : nums) {
    if (num == target) {
        System.out.println("找到目标元素：" + num);
        break; // 找到后跳出循环
    }
    System.out.println("当前元素：" + num);
}
// 输出：
// 当前元素：10
// 当前元素：20
// 找到目标元素：30
```
**嵌套循环中的 break**
break默认只终止**最内层**的循环：

```java
// 嵌套循环中的break
for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 3; j++) {
        if (i == 1 && j == 1) {
            break; // 仅终止内层循环
        }
        System.out.println(i + "," + j);
    }
}
// 输出：
// 0,0  0,1  0,2
// 1,0
// 2,0  2,1  2,2
```
**带标签的 break（终止外层循环）**
通过标签（label:）可指定终止外层循环：

```java
// 带标签的break
outer: for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 3; j++) {
        if (i == 1 && j == 1) {
            break outer; // 终止outer标签对应的外层循环
        }
        System.out.println(i + "," + j);
    }
}
// 输出：
// 0,0  0,1  0,2
// 1,0
```
## 2.3 在 switch-case 中的应用
break在 switch 中用于终止 case 分支，避免穿透：

```java
int day = 2;
switch (day) {
    case 1:
        System.out.println("星期一");
        break; // 终止case 1
    case 2:
        System.out.println("星期二");
        break; // 终止case 2
    default:
        System.out.println("其他");
}
```
## 2.4 特点总结
* 可用于循环（for、while、do-while）和 switch-case
* 默认只影响当前所在的最内层代码块
* 配合标签可终止外层循环
* 执行后立即跳出当前代码块，继续执行后续代码
# 三、continue 语句
## 3.1 功能与语法
continue用于**跳过当前循环体的剩余部分**，直接进入下一次循环（仅用于循环语句）。
**语法**：
> continue;
## 3.2 执行流程
![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/4a44f4e7be23415dae91292def840a16.png#pic_center)

执行步骤：
1. 执行循环体到continue语句
2. 跳过循环体中continue之后的代码
3. 进入下一次循环（执行迭代语句或直接判断循环条件）
## 3.3 代码示例
**基本用法（跳过当前迭代）**

```java
// 打印1-10中的奇数（跳过偶数）
for (int i = 1; i <= 10; i++) {
    if (i % 2 == 0) {
        continue; // 跳过偶数的打印
    }
    System.out.println(i);
}
// 输出：1 3 5 7 9
```
**在 while 循环中使用**

```java
// 计算1-20中所有不能被3整除的数的和
int sum = 0;
int num = 1;
while (num <= 20) {
    num++;
    if (num % 3 == 0) {
        continue; // 跳过能被3整除的数
    }
    sum += num;
}
System.out.println("和为：" + sum);
```
**带标签的 continue（控制外层循环）**

```java
// 打印3x3矩阵中，行索引不等于列索引的元素
outer: for (int row = 0; row < 3; row++) {
    for (int col = 0; col < 3; col++) {
        if (row == col) {
            continue outer; // 跳过当前行的剩余列，直接进入下一行
        }
        System.out.print(row + "," + col + "  ");
    }
    System.out.println();
}
// 输出：
// 0,1  0,2  
// 1,0  
// 2,0  2,1  
```
## 3.4 与 break 的区别
| 语句 | 作用 | 适用场景 | 对循环的影响 |
|--|--|--|--|
| break | 终止当前循环 | 需要提前结束循环 | 完全退出循环 |
| continue | 跳过当前迭代 | 需要忽略部分迭代 | 继续下一次循环 |
## 3.5 注意事项
* continue只能用于循环语句（for、while、do-while），不能用于 switch-case
* 在 for 循环中，continue后会执行迭代语句（如i++）
* 在 while 循环中，需手动更新循环变量，避免continue跳过更新导致死循环

```java
// 错误示例：continue跳过i++，导致i始终为1
int i = 1;
while (i <= 5) {
    if (i == 2) {
        continue; // 跳过i++
    }
    System.out.println(i);
    i++;
}
```
# 四、return 语句
## 4.1 功能与语法
return用于**终止当前方法的执行**，并可选地返回一个值给调用者。
语法：

```java
// 无返回值（用于void方法）
return;

// 有返回值（用于非void方法）
return 表达式;
```
## 4.2 执行流程
![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/e005f9bea89344d48b277e6c6fa286fa.png#pic_center)

执行步骤：
1. 执行方法到return语句
2. 计算返回值表达式（若有）
3. 终止当前方法的执行
4. 将返回值传递给方法调用者
## 4.3 代码示例
**无返回值（void 方法）**

```java
public static void printPositive(int num) {
    if (num <= 0) {
        System.out.println("非正数，不打印");
        return; // 终止方法执行
    }
    System.out.println("正数：" + num);
}

// 调用
printPositive(-5); // 输出"非正数，不打印"
```
**有返回值（非 void 方法）**

```java
public static int max(int a, int b) {
    if (a > b) {
        return a; // 返回a并终止方法
    } else {
        return b; // 返回b并终止方法
    }
}

// 调用
int result = max(10, 20); // result = 20
```
**在循环中使用 return**

```java
public static boolean contains(int[] arr, int target) {
    for (int num : arr) {
        if (num == target) {
            return true; // 找到目标，立即终止方法
        }
    }
    return false; // 未找到目标
}
```
## 4.4 特点与注意事项
* return作用于整个方法，而非循环或代码块
* 一个方法中可以有多个return语句，但最多执行一个
* 非 void 方法的所有分支必须有 return 语句，否则编译错误

```java
// 错误示例：缺少return语句
public static int divide(int a, int b) {
    if (b != 0) {
        return a / b;
    }
    // 编译错误：存在无return的分支
}
```
* return后的代码不会执行（不可达代码）

```java
public static void test() {
    return;
    // System.out.println("这里不会执行"); // 编译错误：不可达代码
}
```

# 五、三种跳转语句的对比
| 语句 | 作用范围 | 功能 | 适用场景 | 典型使用位置 |
|--|--|--|--|--|
|break	|循环或 switch-case|	终止当前代码块	|提前结束循环或 switch 分支	|循环体内、switch 的 case 中|
|continue	|仅循环语句|	跳过当前迭代，进入下一次循环	|忽略无效数据，只处理符合条件的迭代|	循环体内|
|return	|整个方法|	终止当前方法并返回结果|	满足条件时提前结束方法|	方法内任意位置|

## 5.1 核心区别
* **作用域**：break/continue 作用于代码块（循环 /switch），return 作用于方法
* **执行结果**：
	* break：跳出当前代码块，继续执行方法后续代码
	* continue：继续循环的下一次迭代
	* return：完全终止方法，不再执行方法内任何代码

# 六、常见错误与最佳实践
## 6.1 常见错误
1. **混淆 break 和 continue 的作用**

```java
// 错误示例：想用continue跳过偶数，却用了break导致循环终止
for (int i = 1; i <= 5; i++) {
    if (i % 2 == 0) {
        break; // 错误：遇到偶数就终止循环
    }
    System.out.println(i);
}
// 实际输出：1（预期输出：1 3 5）
```

2. **return 在 void 方法中遗漏分号**

```java
public static void test() {
    if (true) {
        return // 编译错误：缺少分号
    }
}
```

3. **带标签的跳转语句使用不当**

```java
// 错误示例：标签位置错误
outer: for (int i = 0; i < 3; i++) {
}
for (int j = 0; j < 3; j++) {
    break outer; // 编译错误：outer标签不在当前作用域
}
```

4. **return 后的代码不可达**

```java
public static int add(int a, int b) {
    return a + b;
    System.out.println("相加完成"); // 编译错误：不可达代码
}
```

## 6.2 最佳实践
1. 控制跳转语句的使用频率：过度使用会导致代码逻辑混乱，降低可读性
2. 优先使用条件判断替代跳转：简单场景下，调整条件表达式可避免使用 break/continue
3. 标签跳转谨慎使用：带标签的 break/continue 会增加代码复杂度，尽量通过重构避免
4. return 提前终止方法：在参数校验、边界条件判断时使用 return，减少嵌套层级

```java
// 推荐：提前return减少嵌套
public static void process(String str) {
    if (str == null) return;
    if (str.isEmpty()) return;
    // 核心逻辑（无需嵌套）
}
```
# 七、总结
Java 的三种跳转语句各有明确用途：
* **break**：终止循环或 switch，适用于提前结束代码块的场景
* **continue**：跳过当前循环迭代，适用于过滤无效数据的场景
* **return**：终止方法并返回结果，适用于满足条件时提前结束方法的场景

理解三者的区别和适用场景，能帮助我们编写逻辑清晰、执行高效的代码。在实际开发中，应优先考虑代码可读性，避免过度使用跳转语句导致逻辑混乱。