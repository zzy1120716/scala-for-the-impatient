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
5. 不要使用JavaBeans的getters and setters，
使用scala提供的def foo和def foo_=替代，
除非要实现带getFoo和setFoo方法签名风格的接口，
或者从Java调用，不方便使用类似foo_$eq方法名的时候
```
scalac Person.scala
javap -private Person

Compiled from "Student.scala"
public class Student {
  private java.lang.String name;
  private long id;
  public java.lang.String name();
  public void name_$eq(java.lang.String);
  public long id();
  public void id_$eq(long);
  public long getId();
  public java.lang.String getName();
  public void setId(long);
  public void setName(java.lang.String);
  public Student();
}
```

9. Car.java