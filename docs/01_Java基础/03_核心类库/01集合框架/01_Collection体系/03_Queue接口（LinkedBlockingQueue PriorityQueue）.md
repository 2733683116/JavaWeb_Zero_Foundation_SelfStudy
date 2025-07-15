# 一、Queue 接口概述
Queue是 Java 集合框架中**用于存储有序元素**的接口，继承自Collection接口，核心特性是**遵循特定的元素处理顺序**（通常为 FIFO，即 “先进先出”，PriorityQueue除外）。其设计目标是提供 “生产 - 消费” 模式的元素管理能力，支持在队列头部移除元素，在尾部添加元素。

Queue接口定义了一组核心操作方法，按功能可分为两类（区别在于对异常的处理）：
|操作类型	|新增元素	|移除元素|	查看头部元素|
|-|-|-|-|
|抛出异常	|add(E e)	|remove()	|element()|
|返回特殊值|	offer(E e)	|poll()|	peek()|

![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/8981a97ce3824e2dbf5547cce1795dc4.png#pic_center)

# 二、LinkedBlockingQueue 详解
## 2.1 底层结构与核心原理
LinkedBlockingQueue是Queue接口的**阻塞队列实现类**，底层基于**单向链表**实现，是一种**有界队列**（默认容量为Integer.MAX_VALUE，可通过构造器指定容量上限）。其核心原理是：
- 链表节点存储元素，通过head（头节点）和last（尾节点）指针维护队列顺序，遵循**FIFO（先进先出）** 规则。
- 线程安全通过**两把重入锁**（takeLock和putLock）实现：读操作（出队）共享takeLock，写操作（入队）共享putLock，读写分离提高并发效率。
## 2.2 核心特点
|特点	|说明|
|-|-|
|**线程安全**	|通过分离锁（takeLock和putLock）保证多线程并发安全，支持生产者 - 消费者模型|
|**阻塞特性**|	当队列满时，put()方法会阻塞入队线程；当队列空时，take()方法会阻塞出队线程|
|**FIFO 顺序**|	元素出队顺序与入队顺序一致（链表结构天然支持 FIFO）|
|**有界性**	|可指定容量上限（new LinkedBlockingQueue(int capacity)），默认容量极大（几乎无界）|
|**高效并发**	|读写分离锁设计，允许同时进行入队和出队操作（单生产者 / 单消费者场景性能优异）|
|**支持超时操作**|	提供offer(E e, long timeout, TimeUnit unit)和poll(long timeout, TimeUnit unit)等超时方法，避免永久阻塞|
## 2.3 常用方法与示例
**核心方法分类**：
- **入队操作**：add(E e)（满时抛异常）、offer(E e)（满时返回false）、put(E e)（满时阻塞）。
- **出队操作**：remove()（空时抛异常）、poll()（空时返回null）、take()（空时阻塞）。
- **查看操作**：element()（空时抛异常）、peek()（空时返回null）。

**生产者 - 消费者模型示例**：

```java
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingQueueDemo {
    // 定义容量为3的阻塞队列
    private static final LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(3);

    public static void main(String[] args) {
        // 生产者线程：向队列中添加元素
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    System.out.println("生产者准备放入：" + i);
                    queue.put(i); // 队列满时阻塞
                    System.out.println("生产者放入成功：" + i + "，当前队列大小：" + queue.size());
                    TimeUnit.SECONDS.sleep(1); // 模拟生产耗时
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 消费者线程：从队列中取出元素
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    TimeUnit.SECONDS.sleep(2); // 模拟消费耗时（慢于生产）
                    int value = queue.take(); // 队列空时阻塞
                    System.out.println("消费者取出：" + value + "，当前队列大小：" + queue.size());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
    }
}
```
**输出结果（关键过程）**：

```powershell
生产者准备放入：1
生产者放入成功：1，当前队列大小：1
生产者准备放入：2
生产者放入成功：2，当前队列大小：2
生产者准备放入：3
生产者放入成功：3，当前队列大小：3
生产者准备放入：4  // 队列满，put()阻塞
消费者取出：1，当前队列大小：2  // 消费后队列有空间
生产者放入成功：4，当前队列大小：3  // 阻塞解除，继续入队
...
```
![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/ca7822cd70934189969c05125ab1e0b1.png#pic_center)

## 2.4 适用场景
- **多线程并发的生产者 - 消费者模型**（如任务调度、消息传递）。
- 需要**线程安全且有界**的队列场景（避免内存溢出）。
- 需阻塞等待资源的场景（如等待任务队列中有新任务时处理）。
# 三、PriorityQueue 详解
## 3.1 底层结构与核心原理
PriorityQueue是Queue接口的**优先级队列实现类**，底层基于**优先级堆**（通常为 “小顶堆”，即堆顶元素为最小元素）实现。其核心原理是：
- 元素存储在数组中，通过堆的上浮 / 下沉操作维护优先级顺序，不保证入队顺序与出队顺序一致。
- 元素优先级由**自然排序**（元素类实现Comparable接口）或**定制排序**（构造器传入Comparator接口）决定，出队时始终返回优先级最高的元素。
## 3.2 核心特点
|特点	|说明|
|-|-|
|**无序性（入队）**	|元素入队顺序与存储位置无关（堆结构决定存储位置）|
|**有序性（出队）**	|元素出队顺序按优先级排序（小顶堆默认升序，可通过比较器定制为降序）|
|**非线程安全**	|无同步机制，多线程并发操作需额外同步（如Collections.synchronizedQueue()）|
|**无界性**	|容量可自动扩容（初始容量为 11，扩容时通常变为原容量的 1.5 倍），无固定上限|
|**不允许 null 元素**	|存储null会抛出NullPointerException（无法比较优先级）|
|**性能**	|入队（offer()）和出队（poll()）操作时间复杂度为O(log n)（堆调整开销）|
## 3.3 排序方式与示例
1. **自然排序（元素类实现Comparable）**

```java
// 自定义元素类，实现Comparable接口定义优先级（按年龄升序）
class Person implements Comparable<Person> {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // 按年龄升序排序（年龄小的优先级高）
    @Override
    public int compareTo(Person o) {
        return this.age - o.age;
    }

    @Override
    public String toString() {
        return name + "(" + age + ")";
    }
}
```
2. **定制排序（构造器传入Comparator）**

```java
// 定制排序：按年龄降序（年龄大的优先级高）
Comparator<Person> ageDescComparator = (p1, p2) -> p2.age - p1.age;
Queue<Person> pq = new PriorityQueue<>(ageDescComparator);
```
3. **优先级出队示例**

```java
import java.util.PriorityQueue;

public class PriorityQueueDemo {
    public static void main(String[] args) {
        // 自然排序：小顶堆（默认升序）
        PriorityQueue<Person> pq = new PriorityQueue<>();

        // 入队（无序）
        pq.offer(new Person("Alice", 25));
        pq.offer(new Person("Bob", 20));
        pq.offer(new Person("Charlie", 30));

        // 出队（按优先级升序）
        while (!pq.isEmpty()) {
            System.out.println("出队：" + pq.poll());
        }
    }
}
```
**输出结果**：

```powershell
出队：Bob(20)  // 年龄最小，优先级最高
出队：Alice(25)
出队：Charlie(30)
```
![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/197cd24d6e1a4587ba1c232070c1f6fa.png#pic_center)

## 3.4 扩容机制与注意事项
- **扩容规则**：当元素数量超过当前容量时，若容量小于 64，则扩容为原容量 + 2；若容量≥64，则扩容为原容量的1.5倍。
- **null元素禁止**：存储null会抛出NullPointerException，因无法比较优先级。
- **遍历无序**：通过iterator()遍历队列时，元素顺序不保证有序（需通过poll()方法依次出队才能获得有序序列）。
## 3.5 适用场景
- **需要按优先级处理元素的场景**（如任务调度器中优先级高的任务先执行）。
- 单线程环境下的**TopN 问题**（如获取前 K 个最大 / 最小元素）。
- 需自定义排序规则的队列场景（如按对象属性排序）。
# 四、LinkedBlockingQueue 与 PriorityQueue 对比
|维度	|LinkedBlockingQueue	|PriorityQueue|
|-|-|-|
|**底层结构**	|单向链表|	优先级堆（数组实现）|
|**线程安全**	|是（分离锁）	|否（需额外同步）|
|**有序性**|	FIFO（入队顺序 = 出队顺序）|	优先级排序（入队顺序≠出队顺序）|
|**容量特性**|	有界（可指定容量）	|无界（自动扩容）|
|**核心操作**|性能	入队 / 出队O(1)（链表操作）|	入队 / 出队O(log n)（堆调整）|
|**阻塞特性**	|支持阻塞（put()/take()）|	不支持阻塞（空时poll()返回null）|
|**元素要求**	|无特殊要求（可存任意对象）	|需支持排序（Comparable或Comparator）|
|**典型应用**|	多线程生产者 - 消费者模型|	单线程优先级任务处理|

# 五、使用注意事项
## 5.1 LinkedBlockingQueue 注意事项
- **容量设置**：默认容量极大（Integer.MAX_VALUE），实际使用时建议指定合理容量（避免内存溢出）。
- **阻塞与超时**：优先使用offer()/poll()的超时重载方法（如offer(e, timeout, unit)），避免永久阻塞。
- **公平性**：默认不保证线程公平性（阻塞线程唤醒顺序不确定），无公平锁实现，需公平性可考虑PriorityBlockingQueue。
## 5.2 PriorityQueue 注意事项
- **线程安全**：多线程并发使用时，需通过Collections.synchronizedQueue(new PriorityQueue<>())包装，或使用PriorityBlockingQueue（线程安全的优先级队列）。
- **排序一致性**：排序规则（Comparable/Comparator）需与equals()保持逻辑一致（避免优先级相同但equals()不同的元素被误判）。
- **遍历无序**：直接遍历队列无法获得有序序列，需通过poll()方法依次出队获取优先级顺序。
# 六、总结
Queue接口的两大实现类各有侧重，选择需结合场景需求：
- LinkedBlockingQueue：聚焦**线程安全与阻塞特性**，适用于多线程并发的生产者 - 消费者模型，保证 FIFO 顺序和有界容量。
- PriorityQueue：聚焦**优先级排序**，适用于单线程环境下按规则排序的场景，通过堆结构高效维护元素优先级。

理解两者的底层结构（链表 vs 堆）和核心特性（阻塞 vs 优先级），是正确选择队列实现类的关键，可显著提升程序性能和可靠性。
