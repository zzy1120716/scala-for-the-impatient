# 7 包和引入
## 7.1 包
`Employee.scala` 和 `Manager.scala`
## 7.2 作用域规则
`Employee.scala`
## 7.3 串联式包语句
`Person.scala`
## 7.4 文件顶部标记法
`Doctor.scala`
## 7.5 包对象
`Person.scala`
## 7.6 包可见性
分别在Person.scala中调用和class Company中调用
## 7.7 引入
## 7.8 任何地方都可以声明引入
## 7.9 重命名和隐藏方法
## 7.10 隐式引入
## 练习
1. Write an example program to demonstrate that  
       package com.horstmann.impatient  
   is not the same as  
       package com  
       package horstmann  
       package impatient  
```
package com {
    def fun1(){}
    package zzy {
        def fun2(){}
        package impatient {
            def fun3(){}
        }
    }
}
```
第一种引用方式是无法调用到fun1和fun2的  
而第二种引用方式可以调用到所有com包下的函数

3. Random.scala

4. Scala语言“一切皆对象”，若把变量和方法放入包中，就会偏离这一准则

5. 含义是：除了com包可以访问，giveRaise方法对于其他包都不可见  
因为com很常见，并且经常作为顶级包名，所以这样写并没有什么用==

7. ExercisesTest.scala

8. 引入java和javax下所有的子包, 不是个好主意, 引入的作用域太大, 容易引发命名冲突

9. Login.scala

10. Float, Double, Short, Boolean, ProcessBuilder...
