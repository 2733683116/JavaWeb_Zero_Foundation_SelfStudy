# 一、List 接口概述
List是 Java 集合框架中**有序、可重复**的集合接口，继承自Collection接口，允许存储null元素。其核心特点是**元素有索引**（支持通过下标访问），可精确控制元素的插入位置和顺序。

List接口定义了一系列操作索引的方法，是日常开发中最常用的集合类型之一，主要实现类包括ArrayList、LinkedList和Vector，它们在底层结构、性能和适用场景上各有差异。

![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/b30875db531547c49c3e72cbe62b69b3.png#pic_center)


# 二、ArrayList 详解
## 2.1 底层结构与核心原理
ArrayList是List接口的**动态数组实现**，底层通过**可扩容的数组**存储元素。其核心特点是：
- 初始容量默认为10（JDK 8+），当元素数量超过当前容量时，自动触发扩容（默认扩容为原容量的1.5倍）。
- 元素按顺序存储在数组中，通过**索引**（下标）快速访问，索引从0开始。

![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/41bf8c1f86404159b97e3dee51840513.png#pic_center)

## 2.2 核心特点
|特点	|说明|
|-|-|
|**有序性**	|元素按插入顺序存储，索引对应插入顺序|
|**可重复性**	|允许存储重复元素，索引不同即可|
|**查询高效**	|通过索引访问元素（get(int index)），时间复杂度为O(1)|
|**增删低效**	|中间 / 头部增删元素需移动后续元素（如add(int index, E e)），时间复杂度O(n)|
|**线程不安全**	|非同步实现，多线程并发修改可能导致数据异常（如ConcurrentModificationException）|
|**扩容机制**|	容量不足时，默认扩容为原容量的 1.5 倍（newCapacity = oldCapacity + (oldCapacity >> 1)）|

## 2.3 常用方法示例

```java
import java.util.ArrayList;
import java.util.List;

public class ArrayListDemo {
    public static void main(String[] args) {
        // 创建ArrayList
        List<String> list = new ArrayList<>();
        
        // 添加元素（尾部）
        list.add("Java");
        list.add("Python");
        list.add("C++");
        System.out.println("初始列表：" + list); // [Java, Python, C++]
        
        // 指定索引插入元素
        list.add(1, "JavaScript");
        System.out.println("插入后：" + list); // [Java, JavaScript, Python, C++]
        
        // 访问元素（通过索引）
        String element = list.get(2);
        System.out.println("索引2的元素：" + element); // Python
        
        // 修改元素
        list.set(3, "Go");
        System.out.println("修改后：" + list); // [Java, JavaScript, Python, Go]
        
        // 删除元素（通过索引）
        list.remove(1);
        System.out.println("删除后：" + list); // [Java, Python, Go]
        
        // 遍历元素
        for (String s : list) {
            System.out.println(s);
        }
    }
}
```
## 2.4 适用场景
- 查询操作频繁的场景（如数据展示、排行榜）。
- 增删操作主要在尾部的场景（如日志收集、数据追加）。
- 不需要线程安全的单线程环境。
# 三、LinkedList 详解
## 3.1 底层结构与核心原理
LinkedList是List接口的**双向链表实现**，底层通过**节点（Node）** 存储元素。每个节点包含三部分：
- prev：指向前一个节点的引用
- data：当前节点存储的元素
- next：指向后一个节点的引用

链表的头节点first和尾节点last分别标记链表的开始和结束，无需连续的内存空间。

![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/b8f0688d3d594b3fafee7ee72a65328a.png#pic_center)

## 3.2 核心特点
|特点	|说明|
|-|-|
|**有序性**|	元素按插入顺序存储，通过节点指针维护顺序|
|**可重复性**	|允许存储重复元素，节点独立存储|
|**查询低效**|	访问指定索引元素需从头 / 尾遍历（get(int index)），时间复杂度O(n)|
|**增删高效**|	中间增删元素只需修改节点指针（如add(int index, E e)），时间复杂度O(1)（已知前驱节点时）|
|**线程不安全**	|非同步实现，多线程并发修改可能导致数据异常|
|**内存开销**	|每个元素需额外存储前后指针，内存开销比ArrayList大|
## 3.3 常用方法示例

```java
import java.util.LinkedList;
import java.util.List;

public class LinkedListDemo {
    public static void main(String[] args) {
        List<String> list = new LinkedList<>();
        
        // 添加元素（尾部）
        list.add("Apple");
        list.add("Banana");
        System.out.println("初始列表：" + list); // [Apple, Banana]
        
        // 头部添加元素（高效）
        list.add(0, "Orange");
        System.out.println("头部添加后：" + list); // [Orange, Apple, Banana]
        
        // 尾部添加元素（高效）
        ((LinkedList<String>) list).addLast("Grape");
        System.out.println("尾部添加后：" + list); // [Orange, Apple, Banana, Grape]
        
        // 访问元素（需遍历，效率较低）
        String element = list.get(2);
        System.out.println("索引2的元素：" + element); // Banana
        
        // 移除头部元素（高效）
        ((LinkedList<String>) list).removeFirst();
        System.out.println("移除头部后：" + list); // [Apple, Banana, Grape]
    }
}
```
## 3.4 适用场景
- 增删操作频繁的场景（如队列、栈、链表结构）。
- 首尾操作多的场景（如实现FIFO队列或LIFO栈）。
- 数据量较大且无需频繁随机访问的场景。
# 四、Vector 详解
## 4.1 底层结构与核心原理
Vector是 Java 早期的List实现类，底层基于**动态数组**（与ArrayList类似），但核心区别是**线程安全**—— 其方法通过 synchronized修饰，保证多线程环境下的操作安全性。
Vector的初始容量默认为10，扩容机制与ArrayList不同：默认扩容为原容量的2倍（可通过构造器指定扩容增量）。
![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/392d1c569d8c4e839d18b8f05f6214b6.png#pic_center)

## 4.2 核心特点
|特点	|说明|
|-|-|
|**有序性**|	与ArrayList一致，元素按索引存储|
|**可重复性**|	允许存储重复元素和null值|
|**查询高效**|	同数组特性，索引访问时间复杂度O(1)|
|**增删低效**	|中间增删需移动元素，时间复杂度O(n)，且同步操作进一步降低效率|
|**线程安全**|	所有方法通过synchronized修饰，支持多线程并发操作|
|**扩容机制**|	默认扩容为原容量的 2 倍（newCapacity = oldCapacity * 2），可自定义增量|
|**效率较低**|	同步操作带来额外开销，单线程环境下性能不如ArrayList|
## 4.3 适用场景
- 多线程并发访问且需要线程安全的场景（如旧系统维护）。
- 对性能要求不高，但需保证数据一致性的场景。
> 注意：现代开发中，更推荐使用Collections.synchronizedList(new ArrayList<>())或CopyOnWriteArrayList替代Vector（兼顾线程安全和性能）。
# 五、ArrayList、LinkedList、Vector 对比
|维度	|ArrayList|	LinkedList	|Vector|
|-|-|-|-|
|**底层结构**|	动态数组|	双向链表|	动态数组（同步）|
|**线程安全性**	|不安全	|不安全	|安全（synchronized修饰方法）|
|**查询性能**|	高（O(1)）|	低（O(n)）|	高（O(1)，但同步开销大）|
|**增删性能（中间）**|	低（O(n)，需移动元素）|	高（O(1)，仅改指针）|	低（O(n)+ 同步开销）|
|**增删性能（首尾）**|	尾部高（O(1)），头部低|	首尾均高（O(1)）	|尾部高，头部低 + 同步开销|
|**扩容机制**	|1.5 倍扩容	|无需扩容（链表无固定容量）|	默认 2 倍扩容（可自定义）|
|**内存开销**|	低（仅存储元素）|	高（节点需存储前后指针）	|低（同数组，但同步元数据开销）|
|**现代开发推荐度**|	高（单线程首选）|	中（增删频繁场景）|	低（建议用替代方案）|

# 六、常用方法与性能差异
## 6.1 核心方法对比
|方法	|ArrayList 性能	|LinkedList 性能	|Vector 性能|
|-|-|-|-|
|get(int index)|	最优|	最差（需遍历）|	优（同步开销）|
|add(E e)（尾部）|	优（扩容时差）|	优|	优（同步开销）|
|add(int index, E e)|	差（移动元素）	|中（需先定位）|	差（移动 + 同步）|
|remove(int index)|	差（移动元素）|	中（需先定位）|	差（移动 + 同步）|
|size()|	优（直接返回变量）|	优（直接返回变量）|	优（同步开销）|
## 6.2 选择建议
1. 优先考虑业务场景：
	- 查多改少 → ArrayList
	- 改多查少（尤其首尾操作） → LinkedList
	- 多线程安全需求 → 避免Vector，用CopyOnWriteArrayList或同步包装器
2. 规避性能陷阱：
	- ArrayList：初始化时指定容量（new ArrayList<>(initialCapacity)），减少扩容次数。
	- LinkedList：避免用for循环按索引遍历（改用迭代器Iterator或增强for循环）。
	- Vector：非必要不使用，优先选择更高效的线程安全集合。
# 七、总结
List接口的三大实现类各有侧重：
- ArrayList以动态数组为核心，平衡查询和尾部增删性能，是单线程场景的首选。
- LinkedList基于双向链表，适合频繁增删（尤其是首尾操作）的场景。
- Vector作为早期实现，以同步方法保证线程安全，但性能较低，现代开发中需谨慎使用。

选择时需结合**线程安全需求、操作类型（查询 / 增删） 和数据规模**，优先考虑ArrayList和LinkedList，线程安全场景推荐更高效的替代方案。理解三者的底层原理，才能在实际开发中做出最优选择。