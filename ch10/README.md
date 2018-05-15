# 10 特质 [L1]
## 10.1 为什么没有多重继承？
## 10.2 当作接口使用的特质
## 10.3 带有具体实现的特质
## 10.4 带有特质的对象
## 10.5 叠加在一起的特质
## 10.6 在特质中重写抽象方法
## 10.7 当作富接口使用的特质
## 10.8 特质中的具体字段
## 10.9 特质中的抽象字段
## 10.10 特质构造顺序
## 10.11 初始化特质中的字段
## 10.12 扩展类的特质
## 10.13 自身类型 [L2]
## 10.14 背后发生了什么
## 练习
3. Look at the BitSet class, and make a diagram of all its superclasses and traits.  
   Ignore the type parameters (everything inside the [...]). Then give the  
   linearization of the traits.  

1) The superclass constructor is called first.  
2) Trait constructors are executed after the superclass constructor but before the
class constructor.  
3) Traits are constructed left-to-right.  
4) Within each trait, the parents get constructed first.  
5) If multiple traits share a common parent, and that parent has already been
constructed, it is not constructed again.  
6) After all traits are constructed, the subclass is constructed.  

```
lin(BitSet) = BitSet >> lin(BitSetLike) >> lin(SortedSet) >> lin(BitSetFactory)
            = BitSet >> (BitSetLike >> SortedSetLike) >> (SortedSet >> SortedSetLike) >> (BitSetFactory >> BitSetLike)
            = BitSet >> SortedSet >> SortedSetLike >> BitSetFactory >> BitSetLike
```

7. Construct an example where a class needs to be recompiled when one of the  
   mixins changes. Start with class SavingsAccount extends Account with ConsoleLogger.  
   Put each class and trait in a separate source file. Add a field to Account. In Main  
   (also in a separate source file), construct a SavingsAccount and access the new  
   field. Recompile all files except for SavingsAccount and verify that the program  
   works. Now add a field to ConsoleLogger and access it in Main. Again, recompile  
   all files except for SavingsAccount. What happens? Why?
   
12. Using javap -c -private, analyze how the call super.log(msg) is translated to Java.  
    How does the same call invoke two different methods, depending on the  
    mixin order?
```
scalac Account.scala
scalac Logger.scala
scalac ConsoleLogger.scala
scalac SavingsAccount.scala
javap -c -private SavingsAccount
```

```
Compiled from "SavingsAccount.scala"
public class SavingsAccount extends Account implements ConsoleLogger {
  private java.lang.String name;

  public void log(java.lang.String);
    Code:
       0: aload_0
       1: aload_1
       2: invokestatic  #19                 // InterfaceMethod ConsoleLogger.log$:(LConsoleLogger;Ljava/lang/String;)V
       5: return

  public java.lang.String name();
    Code:
       0: aload_0
       1: getfield      #24                 // Field name:Ljava/lang/String;
       4: areturn

  public void name_$eq(java.lang.String);
    Code:
       0: aload_0
       1: aload_1
       2: putfield      #24                 // Field name:Ljava/lang/String;
       5: return

  public void withdraw(double);
    Code:
       0: dload_1
       1: aload_0
       2: invokevirtual #33                 // Method balance:()D
       5: dcmpl
       6: ifle          18
       9: aload_0
      10: ldc           #35                 // String Insufficient funds
      12: invokevirtual #37                 // Method log:(Ljava/lang/String;)V
      15: goto          28
      18: aload_0
      19: aload_0
      20: invokevirtual #33                 // Method balance:()D
      23: dload_1
      24: dsub
      25: invokevirtual #40                 // Method balance_$eq:(D)V
      28: return

  public SavingsAccount();
    Code:
       0: aload_0
       1: invokespecial #45                 // Method Account."<init>":()V
       4: aload_0
       5: invokestatic  #49                 // InterfaceMethod ConsoleLogger.$init$
:(LConsoleLogger;)V
       8: return
}
```