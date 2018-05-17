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

## 第二版补充习题：
11. Improve the dynamic property selector in Section 11.11, “Dynamic Invocation,”  
on page 150 so that one doesn’t have to use underscores. For example,  
sysProps.java.home should select the property with key "java.home". Use a helper  
class, also extending Dynamic, that contains partially completed paths.  

12. Define a class XMLElement that models an XML element with a name, attributes,  
and child elements. Using dynamic selection and method calls, make it possible  
to select paths such as rootElement.html.body.ul(id="42").li, which should  
return all li elements inside ul with id attribute 42 inside body inside html.  

13. Provide an XMLBuilder class for dynamically building XML elements, as  
builder.ul(id="42", style="list-style: lower-alpha;"), where the method name  
becomes the element name and the named arguments become the attributes.  
Come up with a convenient way of building nested elements.  