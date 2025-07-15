# 一、迭代器概述
迭代器（Iterator）是 Java 集合框架中用于**遍历集合元素**的工具接口，它提供了一种统一的遍历方式，屏蔽了不同集合底层数据结构的差异（如数组、链表、哈希表等）。其核心思想是 “**迭代器负责遍历，集合负责存储**”，通过迭代器可以安全、高效地访问集合中的元素。

Java 中主要的迭代器接口包括：
- Iterator：基础迭代器，支持单向遍历和元素删除。
- ListIterator：Iterator的子接口，仅适用于List集合，支持双向遍历、元素添加和修改。

迭代器解决了传统for循环遍历的两个核心问题：
- 避免暴露集合底层实现（无需通过索引访问，适配所有集合类型）。
- 提供遍历过程中的元素修改安全性（通过迭代器自身的remove()方法，避免并发修改异常）。

![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/d95aa0d76a18480d8159b8835027c609.png#pic_center)

# 二、Iterator 详解
## 2.1 核心定义与作用
Iterator是所有迭代器的基础接口，定义在java.util包中，仅支持**单向遍历**（从集合头部到尾部），适用于所有Collection接口的实现类（如List、Set、Queue等）。

通过集合的iterator()方法可获取迭代器实例，核心流程为：
1. 调用hasNext()判断是否有下一个元素。
2. 调用next()获取下一个元素（并移动迭代器指针）。
3. （可选）调用remove()删除当前元素（需在next()之后调用）。
## 2.2 核心方法
|方法定义	|功能说明|
|-|-|
|boolean hasNext()|	判断集合中是否还有未遍历的元素，有则返回true，否则返回false。|
|E next()	|返回集合中的下一个元素，并将迭代器指针向后移动一位。若没有下一个元素，抛出NoSuchElementException。|
|void remove()	|删除next()方法最后返回的元素（需在next()之后调用，否则抛出IllegalStateException）。|
## 2.3 使用示例

```powershell
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");

        // 获取迭代器
        Iterator<String> iterator = list.iterator();

        // 遍历集合
        while (iterator.hasNext()) {
            String element = iterator.next(); // 获取下一个元素
            System.out.println(element);

            // 示例：删除"Banana"
            if ("Banana".equals(element)) {
                iterator.remove(); // 仅能通过迭代器删除，避免并发修改异常
            }
        }

        System.out.println("删除后集合：" + list); // 输出：[Apple, Cherry]
    }
}
```
## 2.4 核心特点
|特点	|说明|
|-|-|
|**单向遍历**	|仅支持从集合头部到尾部的顺序遍历（hasNext()+next()），无法向前遍历。|
|**通用性**	|适用于所有Collection实现类（List、Set、Queue等），兼容性强。|
|**元素删除**	|支持通过remove()方法删除当前元素，但需在next()之后调用（依赖next()记录当前元素位置）。|
|**并发安全**|	遍历过程中通过迭代器的remove()修改集合，不会触发ConcurrentModificationException（直接修改集合会报错）。|
|**无添加 / 修改能力**|	仅能遍历和删除元素，无法向集合中添加或修改元素。|

![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/1d5f25160b2846ee81edc14236070fd6.png#pic_center)

# 三、ListIterator 详解
## 3.1 核心定义与作用
ListIterator是Iterator的子接口，仅适用于List接口的实现类（如ArrayList、LinkedList等），扩展了Iterator的功能，支持**双向遍历**和**元素添加 / 修改**，是List集合特有的迭代器。

通过List的listIterator()方法可获取实例，若传入索引参数（如listIterator(int index)），迭代器指针会从指定索引位置开始遍历，进一步增强了遍历的灵活性。
## 3.2 核心方法（扩展自 Iterator）
|方法定义|	功能说明|
|-|-|
|boolean hasPrevious()	|判断集合中是否有上一个未遍历的元素（向前遍历），有则返回true。|
|E previous()	|返回集合中的上一个元素，并将迭代器指针向前移动一位。若没有上一个元素，抛出NoSuchElementException。|
|void add(E e)	|在当前指针位置插入元素（插入后元素位于next()返回元素之前，previous()返回元素之后）。|
|void set(E e)|	修改next()或previous()方法最后返回的元素（需在next()/previous()之后调用）。|
|int nextIndex()	|返回下一个元素的索引（若没有下一个元素，返回集合大小）。|
|int previousIndex()	|返回上一个元素的索引（若没有上一个元素，返回-1）。|
## 3.3 使用示例

```powershell
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ListIteratorDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        // 获取ListIterator（从索引1开始遍历）
        ListIterator<String> lit = list.listIterator(1);

        System.out.println("向后遍历：");
        while (lit.hasNext()) {
            int index = lit.nextIndex(); // 获取下一个元素索引
            String element = lit.next();
            System.out.println("索引" + index + "：" + element); // 输出：索引1:B，索引2:C
        }

        System.out.println("向前遍历：");
        while (lit.hasPrevious()) {
            int index = lit.previousIndex(); // 获取上一个元素索引
            String element = lit.previous();
            System.out.println("索引" + index + "：" + element); // 输出：索引1:B，索引0:A
        }

        // 在当前位置（索引0后）插入元素
        lit.add("D");
        System.out.println("插入后集合：" + list); // 输出：[A, D, B, C]

        // 修改下一个元素（当前指针指向索引1的"D"）
        lit.next(); // 移动到"D"
        lit.set("D-Updated");
        System.out.println("修改后集合：" + list); // 输出：[A, D-Updated, B, C]
    }
}
```
## 3.4 核心特点
|特点	|说明|
|-|-|
|**双向遍历**	|支持向前（hasPrevious()+previous()）和向后（hasNext()+next()）遍历，灵活性更高。|
|**元素操作扩展**|	除删除外，支持add()插入元素和set()修改元素，丰富了集合的修改方式。|
|**索引定位**	|可通过nextIndex()/previousIndex()获取当前指针位置索引，便于精准操作。|
|**起始位置可控**|	支持从指定索引开始遍历（listIterator(int index)），无需从头遍历。|
|**仅适用于 List**	|依赖List的索引特性，无法用于Set、Queue等非 List 集合。|

![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/d14857ab2c49468591e69cac17b750e9.png#pic_center)

# 四、Iterator 与 ListIterator 对比
|维度	|Iterator	|ListIterator|
|-|-|-|
|继承关系	|顶层接口，无父接口	|继承Iterator接口，是子接口|
|适用集合	|所有Collection实现类（List、Set、Queue等）|	仅List实现类（ArrayList、LinkedList等）|
|遍历方向	|单向（仅向后：hasNext()+next()）	|双向（向后 + 向前：hasNext()/hasPrevious()）|
核心操作|	遍历（hasNext()/next()）、删除（remove()）	|遍历、删除、添加（add()）、修改（set()）|
|索引支持	|无索引相关方法	|有nextIndex()/previousIndex()获取索引|
|起始位置控制	|仅能从集合头部开始遍历	|可通过listIterator(int index)指定起始索引|
|并发修改安全性	|支持通过remove()安全删除	|支持remove()/add()/set()安全修改|
## 5.1 并发修改异常（ConcurrentModificationException）
遍历过程中若直接通过集合的方法（如add()、remove()）修改集合结构，迭代器会检测到并发修改，抛出ConcurrentModificationException。**正确做法是通过迭代器自身的方法修改集合**：
- Iterator：仅能通过remove()删除元素。
- ListIterator：可通过remove()/add()/set()修改元素。

**错误示例（直接修改集合）**：

```java
List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));
Iterator<String> it = list.iterator();

while (it.hasNext()) {
    String elem = it.next();
    if ("B".equals(elem)) {
        list.remove(elem); // 直接调用集合的remove()，抛出异常
    }
}
```
**正确示例（通过迭代器修改）**：

```java
// 使用Iterator的remove()
while (it.hasNext()) {
    String elem = it.next();
    if ("B".equals(elem)) {
        it.remove(); // 迭代器删除，安全无异常
    }
}

// 使用ListIterator的add()
ListIterator<String> lit = list.listIterator();
while (lit.hasNext()) {
    if ("C".equals(lit.next())) {
        lit.add("D"); // 迭代器添加，安全无异常
    }
}
```
## 5.2 迭代器状态依赖
- Iterator的remove()必须在next()之后调用（需先通过next()获取元素，迭代器记录当前元素位置），否则抛出IllegalStateException。
- ListIterator的set()必须在next()或previous()之后调用（需先获取待修改元素），add()无此限制，但add()后调用remove()会报错（add()插入的元素未被next()/previous()记录）。
## 5.3 性能考量
- Iterator遍历Set集合时性能最优（无需索引支持）。
- ListIterator在LinkedList中双向遍历性能优于通过索引遍历（避免LinkedList索引访问的O(n)开销）。
# 六、适用场景总结
|场景需求	|推荐迭代器	|原因分析|
|-|-|-|
|遍历Set或Queue集合|	Iterator	|这些集合无索引，Iterator是唯一通用遍历方式。|
|单向遍历List集合，仅需读取|	Iterator|	功能足够，无需额外扩展，性能略优。|
|双向遍历List集合|	ListIterator	|支持向前 / 向后遍历，满足复杂遍历需求（如从中间位置开始遍历）。|
|遍历List时需插入 / 修改元素|	ListIterator|	提供add()/set()方法，是List集合修改元素的安全方式。|
|多线程环境下遍历集合|	Iterator/ListIterator|	迭代器自身的修改方法（remove()/add()）可避免并发修改异常。|

# 七、总结
迭代器是 Java 集合遍历的核心工具，Iterator提供了基础的单向遍历和删除能力，适用于所有集合；ListIterator作为List特有的迭代器，扩展了双向遍历和元素添加 / 修改功能，灵活性更强。

使用时需根据集合类型和需求选择合适的迭代器：通用场景选Iterator，List的复杂遍历和修改场景选ListIterator。同时需注意遍历过程中通过迭代器自身方法修改集合，避免并发修改异常，确保遍历的安全性和高效性。

掌握迭代器的使用不仅能统一集合遍历方式，更能深入理解 Java 集合框架的设计思想 ——“接口抽象，实现分离”，提升代码的通用性和可维护性。
