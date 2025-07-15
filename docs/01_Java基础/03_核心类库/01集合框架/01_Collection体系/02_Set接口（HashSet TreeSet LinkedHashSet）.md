# 一、Set 接口概述
Set是 Java 集合框架中**无序、不可重复**的集合接口，继承自Collection接口。其核心特性是：
- **不可重复性**：集合中不允许存储重复元素（通过equals()方法判断是否重复）。
- **无序性**：元素存储顺序与插入顺序无关（TreeSet和LinkedHashSet除外，有特殊有序性）。
- 允许存储null元素，但最多只能有一个null（因不可重复）。

Set接口没有提供额外的方法，所有方法均继承自Collection，主要实现类包括HashSet、TreeSet和LinkedHashSet，三者在底层结构、有序性和性能上差异显著。

![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/611940c981134593bd27f971aaaff68a.png#pic_center)

# 二、HashSet 详解
## 2.1 底层结构与核心原理
HashSet是Set接口最常用的实现类，底层基于**哈希表**（HashMap） 实现（JDK 8 + 中哈希表为 “数组 + 链表 / 红黑树” 结构）。其核心原理是：
- 元素存储在HashMap的key位置（value为固定常量），利用HashMap的key不可重复特性实现去重。
- 通过元素的hashCode()方法计算哈希值，确定元素在哈希表中的存储位置；通过equals()方法判断元素是否重复。
## 2.2 核心特点
|特点	|说明|
|-|-|
|**无序性**|	元素存储顺序与插入顺序无关（哈希值决定存储位置，不保证顺序）|
|**不可重复性**|	依赖hashCode()和equals()方法去重：若两元素hashCode()相等且equals()返回true，则视为重复|
|**性能优异**|	新增（add()）、查询（contains()）、删除（remove()）操作的平均时间复杂度为O(1)|
|**允许 null**|	最多存储一个null元素（因不可重复）|
|**线程不安全**|	非同步实现，多线程并发修改可能导致数据异常|

## 2.3 去重原理与关键方法重写
HashSet的去重逻辑依赖元素的hashCode()和equals()方法，必须同时重写这两个方法才能保证去重正确性：
- 第一步：计算元素的hashCode()，确定在哈希表中的存储桶位置。
- 第二步：若桶中已有元素，通过equals()方法逐一比较，若返回true则视为重复，不存储；否则存入桶中（哈希冲突时以链表或红黑树形式存储）。

**示例：自定义对象去重**

```java
import java.util.HashSet;
import java.util.Objects;

// 自定义类（需重写hashCode和equals）
class Student {
    private String id;
    private String name;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // 重写hashCode：以id作为哈希计算依据（id相同则哈希值相同）
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // 重写equals：id相同则视为同一对象
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return Objects.equals(id, student.id);
    }
}

public class HashSetDemo {
    public static void main(String[] args) {
        HashSet<Student> set = new HashSet<>();
        set.add(new Student("001", "张三"));
        set.add(new Student("001", "李四")); // id重复，视为重复元素，不存储
        set.add(new Student("002", "王五"));

        System.out.println(set.size()); // 输出：2（去重后保留2个元素）
    }
}
```
![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/1ef178872d2043c6ac1995eae4375527.png#pic_center)

## 2.4 适用场景
- 需快速去重且对元素顺序无要求的场景（如存储用户 ID、标签集合）。
- 新增、查询、删除操作频繁的场景（依赖哈希表的高效性能）。
# 三、LinkedHashSet 详解
## 3.1 底层结构与核心原理
LinkedHashSet是HashSet的子类，底层基于**哈希表（HashMap）+ 双向链表**实现。其核心改进是：
- 继承HashSet的哈希表结构，保证去重和查询性能。
- 通过双向链表维护元素的插入顺序，使集合迭代时按插入顺序返回元素。
链表的每个节点记录前一个和后一个元素的引用，因此迭代遍历速度比HashSet更快（无需遍历哈希表中的空桶）。
## 3.2 核心特点
|特点	|说明|
|-|-|
|**有序性**|	元素按插入顺序存储和迭代（链表维护顺序，哈希表保证去重）|
|**不可重复性**|	同HashSet，依赖hashCode()和equals()方法去重|
|**性能**|	新增、删除性能略低于HashSet（需额外维护链表指针），迭代性能更高|
|**内存开销**|	比HashSet大（需存储链表节点的前后引用）|
|**线程不安全**|	同HashSet，非同步实现|
## 3.3 与 HashSet 的对比
|维度	|HashSet	|LinkedHashSet|
|-|-|-|
|顺序|	无序（哈希值决定位置）|	有序（插入顺序，链表维护）|
|迭代速度|	较慢（可能遍历空桶）|	较快（链表顺序迭代，无空桶）|
|内存占用	|较低（仅哈希表）|	较高（哈希表 + 链表）|
|适用场景|	无顺序要求的去重|	需保留插入顺序的去重|

![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/6732fe8b187b44e0b2536694fdb3d9bd.png#pic_center)

## 3.4 适用场景
- 需去重且需保留元素插入顺序的场景（如日志记录、历史操作轨迹）。
- 频繁迭代遍历的场景（链表迭代效率更高）。
# 四、TreeSet 详解
## 4.1 底层结构与核心原理
TreeSet是Set接口中唯一可排序的实现类，底层基于**红黑树（TreeMap）** 实现。其核心特性是：
- 元素存储在TreeMap的key位置，利用红黑树的自平衡排序特性保证元素有序。
- 元素需按指定规则排序（自然排序或定制排序），不允许存储无法比较的元素。
## 4.2 排序方式
1. 自然排序（默认）
元素所属类实现Comparable接口，重写compareTo(T o)方法定义排序规则：

```java
class User implements Comparable<User> {
    private String name;
    private int age;

    // 按年龄升序排序，年龄相同则按姓名排序
    @Override
    public int compareTo(User o) {
        if (this.age != o.age) {
            return this.age - o.age; // 年龄升序
        }
        return this.name.compareTo(o.name); // 姓名字典序
    }
}
```
2. 定制排序（外部比较器）
创建TreeSet时传入Comparator接口实现类，定义排序规则（无需元素类实现Comparable）：

```java
// 按年龄降序排序的比较器
Comparator<User> ageDescComparator = (u1, u2) -> u2.age - u1.age;

// 使用定制排序创建TreeSet
Set<User> treeSet = new TreeSet<>(ageDescComparator);
```
## 4.3 核心特点
|特点	|说明|
|-|-|
|**有序性**|	元素按排序规则存储（非插入顺序），默认升序，可通过比较器定制|
|**不可重复性**|	通过比较器结果去重：若compareTo()或compare()返回0，视为重复元素|
|**性能**	|新增、删除、查询时间复杂度为O(log n)（红黑树平衡操作开销）|
|**无 null 元素**|	不允许存储null（排序时无法比较null，会抛出NullPointerException）|
|**线程不安全**	|非同步实现，多线程需额外同步处理|

![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/4cd06e8510534659a7d9093781ba1bb5.png#pic_center)

## 4.4 适用场景
- 需对元素排序的场景（如排行榜、成绩排序、区间查询）。
- 需按自定义规则去重和排序的场景（如按对象属性去重 + 排序）。
# 五、HashSet、TreeSet、LinkedHashSet 对比
|维度	|HashSet|	TreeSet	|LinkedHashSet|
|-|-|-|-|
|**底层结构**	|哈希表（数组 + 链表 / 红黑树）|	红黑树|	哈希表 + 双向链表|
|**有序性**	|无序（哈希值决定）|	有序（排序规则）	|有序（插入顺序）|
|**去重依据**	|hashCode()+equals()	|compareTo()/compare()	|hashCode()+equals()|
|**新增 / 删除性能**	|最高（O(1)）|	中（O(log n)）|	中（略低于 HashSet）|
|**迭代性能**|	中（可能遍历空桶）	|中（红黑树中序遍历）|	最高（链表顺序迭代）|
|**是否允许 null**|	允许（最多 1 个）|	不允许|	允许（最多 1 个）|
|**典型应用场景**	|快速去重，无序需求|	排序场景，区间查询	|去重 + 保留插入顺序|

# 六、使用注意事项
1. **重写hashCode()和equals()的规范（针对 HashSet/LinkedHashSet）**：
	- 若两元素equals()返回true，则hashCode()必须相等（否则哈希表会视为不同元素，导致重复存储）。
	- 若两元素hashCode()相等，equals()可返回false（哈希冲突，通过链表 / 红黑树存储）。
2. **TreeSet 的排序一致性**：
	- 排序规则（compareTo()/compare()）需与equals()保持一致：若compareTo()返回0，则equals()应返回true（否则会出现 “逻辑重复但未去重” 的矛盾）。
3. **线程安全性**：
	- 三者均为线程不安全集合，多线程并发修改需通过Collections.synchronizedSet()包装，或使用ConcurrentHashMap（JDK 8+）的相关实现。
4. **性能优化**：
	- HashSet初始化时指定容量（避免频繁扩容）。
	- 避免在TreeSet中存储无法比较的元素（会抛出ClassCastException）。
# 七、总结
Set接口的三大实现类各有侧重，选择时需结合**顺序需求、性能需求和去重规则**：
- **HashSet**：优先选择，适用于无序、快速去重场景，性能最优。
- **LinkedHashSet**：需保留插入顺序时使用，迭代性能优于 HashSet，内存开销略高。
- **TreeSet**：需排序或区间查询时使用，依赖比较器实现有序性和去重。

理解三者的底层结构（哈希表 / 红黑树 / 链表）是掌握其特性的核心，合理选择可显著提升集合操作
的效率和代码可读性。