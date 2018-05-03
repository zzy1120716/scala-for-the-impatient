## 练习
1. 
```
!=   ==             doubleValue   isInstanceOf    min              toChar          unary_-
##   >              ensuring      isNaN           ne               toDegrees       unary_~
%    >=             eq            isNegInfinity   notify           toDouble        underlying
&    >>             equals        isPosInfinity   notifyAll        toFloat         until
*    >>>            floatValue    isValidByte     round            toHexString     wait
+    ^              floor         isValidChar     self             toInt           |
-    abs            formatted     isValidInt      shortValue       toLong          →
->   asInstanceOf   getClass      isValidLong     signum           toOctalString
/    byteValue      hashCode      isValidShort    synchronized     toRadians
<    ceil           intValue      isWhole         to               toShort
<<   compare        isInfinite    longValue       toBinaryString   toString
<=   compareTo      isInfinity    max             toByte           unary_+
```

2.
```
scala> import math._
import math._

scala> sqrt(3)
res1: Double = 1.7320508075688772

scala> res1 * res1
res2: Double = 2.9999999999999996
```

3. res变量是var
```
scala> res2 + 1
res3: Double = 3.9999999999999996
```

4. 在ScalaDoc中搜索StringOps类，查找“*”方法，将字符串重复拼接，共n个

5. max方法在Int类中，作用是返回两个数中的最大值

6. BigInt类的pow方法

7.
```
import util.Random
import BigInt.probablePrime
```

8. GetRandomFile，BigInt的toString方法可以指定基数，输出转换为指定进制的字符串。

9. take, takeRight, apply, reverse, head, last

10. 
```
def take(n: Int): String -- Selects first n elements.
def takeRight(n: Int): String -- Selects last n elements.
def drop(n: Int): String -- Selects all elements except first n ones.
def dropRight(n: Int): String -- Selects all elements except last n ones.
def substring(arg0: Int, arg1: Int): String -- arg0(beginIndex), arg1(endIndex)
```
优点：简洁易读
缺点：只能单方向，取中间的字符串需要多次调用，不如用substring