/**
 * Java基本数据类型演示
 * 展示int、float、boolean等基本数据类型的声明与使用
 */
public class BasicDataTypesDemo {
    public static void main(String[] args) {
        // ===================== 整数类型 =====================
        System.out.println("=== 整数类型演示 ===");
        
        // byte类型：1字节，范围-128~127
        byte byteVar = 100;
        System.out.println("byte变量：" + byteVar);
        
        // short类型：2字节，范围-32768~32767
        short shortVar = 30000;
        System.out.println("short变量：" + shortVar);
        
        // int类型：4字节，范围-2147483648~2147483647，整数默认类型
        int intVar = 100000;
        System.out.println("int变量：" + intVar);
        
        // long类型：8字节，范围-9223372036854775808~9223372036854775807
        // 声明long类型需在数字后加L（推荐大写，避免与数字1混淆）
        long longVar = 9999999999L;
        System.out.println("long变量：" + longVar);
        
        // 整数运算演示
        int sum = intVar + 50000;
        System.out.println("int加法运算：" + intVar + " + 50000 = " + sum);
        
        // ===================== 浮点类型 =====================
        System.out.println("\n=== 浮点类型演示 ===");
        
        // float类型：4字节，单精度，声明需加f/F
        float floatVar = 3.14f;
        System.out.println("float变量：" + floatVar);
        
        // double类型：8字节，双精度，浮点数默认类型
        double doubleVar = 3.141592653589793;
        System.out.println("double变量：" + doubleVar);
        
        // 浮点运算注意精度问题
        double precisionIssue = 0.1 + 0.2;
        System.out.println("0.1 + 0.2 = " + precisionIssue); // 输出0.30000000000000004
        
        // ===================== 字符类型 =====================
        System.out.println("\n=== 字符类型演示 ===");
        
        // char类型：2字节，存储单个Unicode字符，用单引号声明
        char charVar = 'A';
        System.out.println("char变量：" + charVar);
        
        // 字符的Unicode码点
        int charCode = '中'; // 汉字"中"的Unicode码点
        System.out.println("汉字'中'的Unicode码点：" + charCode);
        
        // 转义字符
        char newLine = '\n'; // 换行符
        char tab = '\t';     // 制表符
        System.out.println("使用转义字符：" + newLine + "这是换行后的内容" + tab + "这是制表符分隔的内容");
        
        // 字符运算：基于Unicode码点进行算术操作
        char nextChar = (char) (charVar + 1);
        System.out.println(charVar + "的下一个字符：" + nextChar);
        
        // ===================== 布尔类型 =====================
        System.out.println("\n=== 布尔类型演示 ===");
        
        // boolean类型：1字节，值为true或false
        boolean isTrue = true;
        boolean isFalse = false;
        System.out.println("布尔变量isTrue：" + isTrue);
        System.out.println("布尔变量isFalse：" + isFalse);
    }
}