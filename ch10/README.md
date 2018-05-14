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