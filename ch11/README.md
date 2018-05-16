# 11 操作符 [L1]
## 11.1 标识符
## 11.2 中置操作符
## 11.3 一元操作符
## 11.4 赋值操作符
## 11.5 优先级
## 11.6 结合性
## 11.7 apply和update方法
## 11.8 提取器 [L2]
## 11.9 带单个参数或无参数的提取器 [L2]
## 11.10 unapplySeq方法
## 11.11 Dynamic Invocation
## 练习
2. The BigInt class has a pow method, not an operator. Why didn’t the Scala library  
   designers choose ** (as in Fortran) or ^ (as in Pascal) for a power operator?  
优先级问题，指数运算有最高的优先级，若用**，则优先级与乘除运算相同  
若用^，虽然具有最高优先级，但会覆盖原有的异或运算。

