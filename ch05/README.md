# 5 类
## 5.1 简单类和无参方法
## 5.2 带getter和setter的属性
```
scalac Person.scala
javap -private Person

Compiled from "Person.scala"
public class Person {
  private int age;
  public int age();
  public void age_$eq(int);
  public Person();
}
```
## 5.3 只带getter的属性
## 5.4 对象私有字段
## 5.5 Bean属性
## 5.6 辅助构造器
## 5.7 主构造器
## 5.8 嵌套类
## 练习