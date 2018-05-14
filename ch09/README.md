# 9 文件和正则表达式
## 9.1 读取行
## 9.2 读取字符
## 9.3 读取词法单元和数字
```
print("How old are you? ")
val age = readInt()
```
## 9.4 从URL或其他源读取
## 9.5 读取二进制文件
## 9.6 写入文本文件
## 9.7 访问目录
## 9.8 序列化
## 9.9 进程控制
```
import sys.process._
("ls -al ..").!
(val result = "ls -al ..").!!
("ls -al .." #| "grep sec").!
("ls -al .." #> new File("output.txt")).!
("ls -al .." #>> new File("output.txt")).!
("grep sec" #< new File("output.txt")).!
("grep Scala" #< new URL("http://horstmann.com/index.html")).!

("ls -al .." #&& "ls -al .").!
("ls -al .." #|| "ls -al .").!
```

```
import sys.process._;
import java.io.File;
val cmd = "ls -al ..";
val dirName = "E:\\IdeaProjects\\scala-for-the-impatient\\ch09\\src\\test\\resources\\b";
val p = Process(cmd, new File(dirName), ("LANG", "en_US"));
("echo 42" #| p).!
```
```
val dirName = "/Users/zzy/Docs/scala-for-the-impatient/ch09/src/test/resources/b"
```
shell scripts, start script files like this:
```
#!/bin/sh
exec scala "$0" "$@"
!#
Scala commands
```
run Scala scripts from Java programs:
```
ScriptEngine engine =
new ScriptEngineManager().getScriptEngineByName("scala")
```
## 9.10 正则表达式
## 9.11 正则表达式组
## 练习
10. `Person.scala`