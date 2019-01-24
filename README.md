## 概述
本例用于学习istio，官网已经给出了很好的多语言示例。此处用spring-boot + kotlin 写两个简单的服务。

```
                            +----------------+
                            |                |
                  +--------->  user-service  |
                  |         |                |
                  |         +----------------+
+-----------+     |
|           |     |
|  user-ui  +-----+
|           +-----+
+-----------+     |           +---------------+
                  |           |               |
                  |           | user-service  |
                  +---------> |               |
                              +---------------+
```
## 构建

安装docker环境和mvn后执行如下命令构建docker镜像
```
mvn clean package -Pdocker
```
两个镜像已经push到我的aliyun仓库，可以直接用
```
registry.cn-hangzhou.aliyuncs.com/my-istio-release/user-service:latest
registry.cn-hangzhou.aliyuncs.com/my-istio-release/user-ui:latest
```

## 安装kubernetes
环境：vitrulbox + vagrant
下载我的自动换部署脚本
https://github.com/ThoreauZZ/configuration/tree/master/vagrant/k8s-istio
根据机器性能修改Vagrantfile
安装k8s最大的麻烦就是墙内无法访问Google镜像的问题，大部分被我换成了国内镜像，如果没有代理
注释掉install-k8s-common.sh脚本中的HTTP_PROXY="http://192.168.99.1:1087"

然后执行vagrant自动安装kubernetes集群：
```
vagrant up
```
