# 一、循环语句概述
循环语句用于重复执行一段代码块，直到满足终止条件，是程序控制流的重要组成部分。Java 提供四种主要循环语句：
- **for 循环**：适合已知循环次数的场景
- **for-each 循环**：简化数组和集合的遍历
- **while 循环**：适合循环次数不确定，依赖条件判断的场景
- **do-while 循环**：至少执行一次的循环场景

合理使用循环可以避免代码冗余，实现批量处理、迭代计算等功能。
# 二、for 循环
## 2.1 基本语法

```java
for (初始化语句; 循环条件; 迭代语句) {
    // 循环体（需要重复执行的代码）
}
```
- **初始化语句**：循环开始前执行一次，通常用于声明循环变量（如int i = 0）
- **循环条件**：布尔表达式，为true时执行循环体，为false时终止循环
- **迭代语句**：每次循环体执行后执行，通常用于更新循环变量（如i++）
## 2.2 执行流程
![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/13d0dd00ee2e40249d245ed230d054d0.png#pic_center)

执行步骤：
1. 执行初始化语句（仅一次）
2. 判断循环条件：
	- 若为true，执行循环体
	- 若为false，退出循环
3. 循环体执行完毕后，执行迭代语句
4. 重复步骤 2-3
## 2.3 代码示例

```java
// 基本用法：打印1-5的数字
for (int i = 1; i <= 5; i++) {
    System.out.println(i);
}

// 嵌套for循环：打印5x5乘法表
for (int row = 1; row <= 5; row++) {
    for (int col = 1; col <= row; col++) {
        System.out.print(col + "x" + row + "=" + (col*row) + "\t");
    }
    System.out.println();
}

// 省略部分语句（不推荐，降低可读性）
int i = 0;
for (; i < 5; ) {
    System.out.println(i);
    i++;
}
```
## 2.4 注意事项
1. **循环变量的作用域**：在 for 循环中声明的变量仅在循环体内有效

```java
for (int i = 0; i < 3; i++) { ... }
// System.out.println(i); // 编译错误：i超出作用域
```
2. **死循环风险**：若循环条件始终为true且无 break，会导致无限循环

```java
// 死循环示例（谨慎使用）
for (;;) { // 省略所有语句，条件默认为true
    // 需在循环体内通过break手动终止
    if (someCondition) break;
}
```
3. **多重循环的性能**：嵌套循环层数越多，性能消耗越大，建议控制在 3 层以内
# 三、for-each 循环（增强 for 循环）
## 3.1 基本语法

```java
for (元素类型 变量名 : 数组或集合) {
    // 循环体：变量名代表当前遍历的元素
}
```
## 3.2 适用范围
- 数组（int[]、String[]等）
- 实现Iterable接口的集合（List、Set等）
## 3.3 执行流程
![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/8d251dd7294e4f4890c214529dee2585.png#pic_center)

执行步骤：
1. 依次从数组或集合中获取元素，赋值给变量
2. 执行循环体
3. 重复步骤 1-2，直到所有元素遍历完毕
## 3.4 代码示例

```java
// 遍历数组
int[] numbers = {1, 2, 3, 4, 5};
for (int num : numbers) {
    System.out.println(num); // 依次输出数组元素
}

// 遍历集合
List<String> fruits = Arrays.asList("苹果", "香蕉", "橙子");
for (String fruit : fruits) {
    System.out.println(fruit); // 依次输出集合元素
}

// 遍历字符串（本质是字符数组）
String str = "hello";
for (char c : str.toCharArray()) {
    System.out.println(c); // 依次输出每个字符
}
```
## 3.5 特点与限制
- **优点**：语法简洁，无需关心索引，减少数组越界风险
- **限制**：
	- 无法获取元素索引（如需索引需用普通 for 循环）
	- 不能在循环中添加 / 删除集合元素（会抛出ConcurrentModificationException）
	- 不能修改数组 / 集合的元素（基本类型修改的是副本，引用类型可修改对象属性）
```java
for (int num : numbers) {
    num = 100; // 无效：仅修改临时变量，原数组不变
}
```
# 四、while 循环
## 4.1 基本语法

```java
while (循环条件) {
    // 循环体
}
```
## 4.2 执行流程
![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/89c3f5c77649425f86204c08980ce4b6.png#pic_center)

执行步骤：
1.	判断循环条件：
	- 若为true，执行循环体
	- 若为false，退出循环
2. 循环体执行完毕后，回到步骤 1 重复判断
## 4.3 代码示例

```java
// 基本用法：计算1-100的和
int sum = 0;
int i = 1;
while (i <= 100) {
    sum += i;
    i++; // 必须手动更新循环变量，否则会死循环
}
System.out.println("1-100的和：" + sum);

// 读取用户输入直到合法
Scanner scanner = new Scanner(System.in);
int age;
while (true) {
    System.out.print("请输入年龄（1-120）：");
    age = scanner.nextInt();
    if (age >= 1 && age <= 120) {
        break; // 输入合法，退出循环
    }
    System.out.println("年龄不合法，请重新输入！");
}
```
## 4.4 注意事项
1. **循环变量的初始化**：需在循环外手动初始化（区别于 for 循环）
2. **迭代语句的位置**：必须在循环体内包含更新循环变量的语句，否则易导致死循环

```java
// 死循环示例（缺少迭代语句）
int j = 0;
while (j < 5) {
    System.out.println(j);
    // 缺少j++，j始终为0，循环永不结束
}
```
# 五、do-while 循环
## 5.1 基本语法

```java
do {
    // 循环体
} while (循环条件);
```
## 5.2 执行流程
![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/a6f6915dddae4c998bbea98ea1217df6.png#pic_center)

执行步骤：
1. 执行循环体（至少执行一次）
2. 判断循环条件：
	- 若为true，回到步骤 1 继续执行
	- 若为false，退出循环
## 5.3 代码示例

```java
// 基本用法：至少执行一次的场景
int input;
do {
    System.out.print("请输入0退出：");
    input = scanner.nextInt();
} while (input != 0);

// 计算阶乘（n! = 1*2*...*n）
int n = 5;
int factorial = 1;
int k = 1;
do {
    factorial *= k;
    k++;
} while (k <= n);
System.out.println(n + "的阶乘：" + factorial);
```
## 5.4 与 while 循环的区别
* **执行次数**：do-while 至少执行一次循环体，while 可能一次都不执行
* **适用场景**：do-while 适合 "先执行后判断" 的场景（如用户输入验证、菜单选择）

```java
// while循环：条件不满足则一次都不执行
int a = 5;
while (a < 5) {
    System.out.println("while循环执行"); // 不会执行
}

// do-while循环：无论条件如何，至少执行一次
int b = 5;
do {
    System.out.println("do-while循环执行"); // 会执行一次
} while (b < 5);
```
# 六、循环控制语句（break、continue）
## 6.1 break 语句
* **作用**：立即终止当前循环，跳出循环体
* **适用场景**：for、for-each、while、do-while 循环，以及 switch-case

```java
// 找到第一个偶数就退出
int[] nums = {1, 3, 4, 5, 6};
for (int num : nums) {
    if (num % 2 == 0) {
        System.out.println("第一个偶数：" + num);
        break; // 找到后立即退出循环
    }
}

// 嵌套循环中跳出指定循环（需配合标签）
outer: for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 3; j++) {
        if (i == 1 && j == 1) {
            break outer; // 跳出outer标签对应的外层循环
        }
        System.out.println(i + "," + j);
    }
}
```
## 6.2 continue 语句
* **作用**：跳过当前循环体的剩余部分，直接进入下一次循环
* **适用场景**：仅用于循环语句

```java
// 打印1-10中的奇数
for (int i = 1; i <= 10; i++) {
    if (i % 2 == 0) {
        continue; // 跳过偶数，进入下一次循环
    }
    System.out.println(i);
}

// 配合标签使用
outer: for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 3; j++) {
        if (j == 1) {
            continue outer; // 跳过外层循环的当前迭代，进入下一次
        }
        System.out.println(i + "," + j);
    }
}
```
# 七、四种循环的对比与选择
|循环类型	|语法特点|	适用场景|	优势|	局限性|
|-|-|-|-|-|
|for 循环|	初始化、条件、迭代集中定义|	已知循环次数（如遍历前 n 个元素）|	结构清晰，循环变量作用域可控|	语法相对繁琐|
for-each 循环|	简化的遍历语法|	数组或集合的遍历（无需索引）|	代码简洁，无越界风险|	无法获取索引，不能修改元素|
|while 循环	|仅包含循环条件|	循环次数不确定（依赖动态条件）|	灵活，适合复杂条件判断	|需手动管理循环变量|
|do-while 循环	|先执行后判断|	至少执行一次的场景（如输入验证）|	保证循环体至少执行一次	|适用场景有限|

## 7.1 选择建议
* 遍历数组 / 集合且无需索引 → 优先用 for-each 循环
* 已知循环次数或需要索引 → 用 for 循环
* 循环次数不确定且可能不执行 → 用 while 循环
* 循环次数不确定但必须执行一次 → 用 do-while 循环
# 八、常见错误与最佳实践
## 8.1 常见错误
1. **死循环**：循环条件始终为true且无 break

```java
// 错误示例：i始终为0，条件永远成立
int i = 0;
while (i < 5) {
    System.out.println(i);
    // 缺少i++，导致死循环
}
```
2. **循环变量更新位置错误**

```java
// 错误示例：迭代语句在continue之后，导致i不更新
for (int i = 0; i < 5; ) {
    if (i == 2) {
        continue; // 跳过迭代语句
    }
    System.out.println(i);
    i++;
}
```
3. **数组越界**：循环变量超出数组索引范围

```java
int[] arr = {1, 2, 3};
// 错误：arr.length是3，索引最大为2，i <= 3会越界
for (int i = 0; i <= arr.length; i++) {
    System.out.println(arr[i]); // 抛出ArrayIndexOutOfBoundsException
}
```
## 8.2 最佳实践
1. **控制循环复杂度**：循环体代码不宜过长，复杂逻辑建议提取为方法
2. **避免深度嵌套**：嵌套循环不超过 3 层，超过则考虑重构（如拆分循环）
3. **明确循环终止条件**：确保循环在有限步骤内可以终止
4. **使用合理的循环变量名**：如i、j用于简单循环，index、count用于有特定含义的场景
5. **优先使用 for-each 遍历**：简化代码，减少出错概率
# 九、总结
Java 循环语句是实现重复操作的核心工具，四种循环各有适用场景：
* for 循环适合已知次数的迭代，结构严谨
* for-each 循环简化了数组和集合的遍历，提高可读性
* while 循环适合依赖动态条件的循环场景
* do-while 循环保证循环体至少执行一次

掌握循环的选择原则和控制语句（break、continue）的使用，能有效提高代码质量，避免常见的循环错误（如死循环、越界）。在实际开发中，应根据具体场景选择最合适的循环类型，平衡代码简洁性和可读性。