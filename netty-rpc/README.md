# SpringBoot Netty RPC框架

整体思路：

编写Netty服务端

1. 在Handle里处理接收到客户端发送的数据
2. 获取被Spring容器加载的BeanService
3. 通过ApplicationContext获取Bean
4. 通过反射调用具体方法，并获取返回值
5. 写入channel发送给客户端

