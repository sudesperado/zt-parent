# java并发编程的艺术

## 1.java并发机制的底层实现原理

### 1.1 volatile

作用：在多处理器开发中保证了共享变量的可见性

原理：将当前处理器缓存行的数据写回到系统内存 ==> 写会内存的操作会使其他处理器里缓存了该内存地址的数据无效

示例1：
```
public class VolatileTest {
    private static volatile Integer num = 0;

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (num<100){
                    System.out.println(Thread.currentThread().getName()+"----"+num++);
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (num<100){
                    System.out.println(Thread.currentThread().getName()+"----"+num++);
                }
            }
        }).start();
    }
}
```
打印结果：
```$xslt
Thread-0----0
Thread-0----1
Thread-0----2
Thread-0----3
Thread-0----4
Thread-0----5
Thread-0----6
Thread-1----7
Thread-1----8
Thread-1----9
Thread-1----10
Thread-1----11
Thread-1----12
Thread-1----13
Thread-1----14
Thread-1----15
Thread-1----16
Thread-1----17
Thread-1----18
Thread-1----19
Thread-1----20
Thread-1----21
Thread-1----22
Thread-1----23
Thread-1----24
Thread-1----25
Thread-1----26
Thread-1----27
Thread-1----28
Thread-1----29
Thread-1----30
Thread-1----31
Thread-1----32
Thread-1----33
Thread-1----34
Thread-1----35
Thread-1----36
Thread-1----37
Thread-1----38
Thread-1----39
Thread-1----40
Thread-1----41
Thread-1----42
Thread-1----43
Thread-1----44
Thread-1----45
Thread-1----46
Thread-1----47
Thread-1----48
Thread-1----49
Thread-1----50
Thread-1----51
Thread-1----52
Thread-1----53
Thread-1----54
Thread-1----55
Thread-1----56
Thread-1----57
Thread-1----58
Thread-1----59
Thread-1----60
Thread-1----61
Thread-1----62
Thread-1----63
Thread-1----64
Thread-1----65
Thread-1----66
Thread-1----67
Thread-1----68
Thread-1----69
Thread-1----70
Thread-1----71
Thread-1----72
Thread-1----73
Thread-1----74
Thread-1----75
Thread-1----76
Thread-1----77
Thread-1----78
Thread-1----79
Thread-1----80
Thread-1----81
Thread-1----82
Thread-1----83
Thread-1----84
Thread-1----85
Thread-1----86
Thread-1----87
Thread-1----88
Thread-1----89
Thread-1----91
Thread-1----92
Thread-1----93
Thread-1----94
Thread-1----95
Thread-1----96
Thread-1----97
Thread-1----98
Thread-1----99
Thread-0----90
```
### 1.2 synchronized

