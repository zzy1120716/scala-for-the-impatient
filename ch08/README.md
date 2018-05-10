# 8 继承
## 8.1 扩展类
## 8.2 重写方法
## 8.3 类型检查和转换
## 8.4 受保护字段和方法
## 8.5 超类的构造
## 8.6 重写字段
## 8.7 匿名子类
## 8.8 抽象类
## 8.9 抽象字段
## 8.10 构造顺序和提前定义
## 8.11 Scala继承层级
`PrintAnyAndUnit.scala`
## 8.12 对象相等性
## 8.13 Value Classes
`MilTime.scala`  
`Author.scala` 和 `Title.scala`
## 练习
8.
`Person.scala` 和 `SecretAgent.scala`
```
scalac Person.scala
javap -c Person

Compiled from "Person.scala"
public class Person {
  private final java.lang.String name;

  public java.lang.String name();
    Code:
       0: aload_0
       1: getfield      #13                 // Field name:Ljava/lang/String;
       4: areturn

  public java.lang.String toString();
    Code:
       0: new           #18                 // class java/lang/StringBuilder
       3: dup
       4: ldc           #19                 // int 7
       6: invokespecial #23                 // Method java/lang/StringBuilder."<init>":(I)V
       9: aload_0
      10: invokevirtual #27                 // Method getClass:()Ljava/lang/Class;
      13: invokevirtual #32                 // Method java/lang/Class.getName:()Ljava/lang/String;
      16: invokevirtual #36                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      19: ldc           #38                 // String [name=
      21: invokevirtual #36                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      24: aload_0
      25: invokevirtual #40                 // Method name:()Ljava/lang/String;
      28: invokevirtual #36                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      31: ldc           #42                 // String ]
      33: invokevirtual #36                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      36: invokevirtual #44                 // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
      39: areturn

  public Person(java.lang.String);
    Code:
       0: aload_0
       1: aload_1
       2: putfield      #13                 // Field name:Ljava/lang/String;
       5: aload_0
       6: invokespecial #48                 // Method java/lang/Object."<init>":()V
       9: return
}
```
```
javap -c SecretAgent

Compiled from "SecretAgent.scala"
public class SecretAgent extends Person {
  public java.lang.String name();
    Code:
       0: aload_0
       1: getfield      #14                 // Field name:Ljava/lang/String;
       4: areturn

  public java.lang.String toString();
    Code:
       0: aload_0
       1: getfield      #18                 // Field toString:Ljava/lang/String;
       4: areturn

  public SecretAgent(java.lang.String);
    Code:
       0: aload_0
       1: aload_1
       2: invokespecial #23                 // Method Person."<init>":(Ljava/lang/String;)V
       5: aload_0
       6: ldc           #25                 // String secret
       8: putfield      #14                 // Field name:Ljava/lang/String;
      11: aload_0
      12: ldc           #25                 // String secret
      14: putfield      #18                 // Field toString:Ljava/lang/String;
      17: return
}
```
```
javap -private Person

Compiled from "Person.scala"
public class Person {
  private final java.lang.String name;
  public java.lang.String name();
  public java.lang.String toString();
  public Person(java.lang.String);
}
```
```
javap -private SecretAgent

Compiled from "SecretAgent.scala"
public class SecretAgent extends Person {
  private final java.lang.String name;
  private final java.lang.String toString;
  public java.lang.String name();
  public java.lang.String toString();
  public SecretAgent(java.lang.String);
}
```

9. 只替换父类中的val，测试结果不变，数组长度被限定在val range的值0  
   子类中也将val替换为def，数组长度变为我们想要的值

10. The file scala/collection/immutable/Stack.scala contains the definition
        class Stack protected (protected val elems: List)
    Explain the meanings of the protected keywords. (Hint: Review the discussion
    of private constructors in Chapter 5.)
    protected构造器只能由子类调用，有此限定词的类自身只能使用辅助构造器。  
    或者在类中的辅助构造器方法体内调用。
    
11. `Point.scala`