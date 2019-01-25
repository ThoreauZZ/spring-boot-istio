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
https://github.com/ThoreauZZ/configuration/tree/master/vagrant/k8s-istio . 
根据机器性能修改Vagrantfile . 
安装k8s最大的麻烦就是墙内无法访问Google镜像的问题，大部分被我换成了国内镜像，如果没有代理 . 
注释掉install-k8s-common.sh脚本中的HTTP_PROXY="http://192.168.99.1:1087" . 

然后执行vagrant自动安装kubernetes集群： 
```
vagrant up
```

## 安装istio

```
# 登陆机器
vagrant ssh k8s-master
sudo su -
cd /vagrant/deploy
# 安装istio
# 脚本install_istio.sh

# 安装dashboard
sh install_dashboard.sh dashboard
# https://192.168.99.110:31538/#!/login
# 获取token
token_secrets=$(kubectl describe sa admin-user -n kube-system |grep Tokens:|awk '{print $2}')
kubectl -n kube-system get secret ${token_secrets} -o jsonpath={.data.token}|base64 -d
```

## 安装istio-service
```
git clone https://github.com/ThoreauZZ/spring-boot-istio.git

# deploy
cd spring-boot-istio/kubernetes
kubectl apply -f <(istioctl kube-inject -f kubectl delete -f user-deployment.yaml)
kubectl get pods
```

master机器上： ui->service，多次地址不同
```
curl -s  $(kubectl get svc |grep user-ui|awk '{print $3}')/user/greet?name=thoreau| jq .
{
  "id": 4,
  "content": "Hello, thoreau",
  "ip": "10.244.0.40",
  "address": "user-service-deployment-76845bbfd-s6vmr",
  "time": "2019-01-25 03:18:05"
}
```
外网访问
```
cd istio-rules
kubectl apply -f my-gateway.yaml
export INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="http2")].nodePort}')
192.168.99.110:$INGRESS_PORT//user/greet?name=thoreau| jq .
```

## 网络控制
1. 版本
```
 kubectl apply -f <(istioctl kube-inject -f kubectl delete -f user-service-deployment-v2.yaml)
 kubectl get pod 
 # 访问v1 v2都有

 # 50% vs 50 %
 kubectl apply -f rute-userservice.yaml
```

2. 错误注入
```
kubectl apply -f rute-userservice-503.yaml
```
3. 熔断

## 可视化
```bash
# 通过命令把ClusterIP->NodePort。通过nodeport访问: 如下依赖关系，链路追踪，流量监控
kubectl get svc servicegraph -n istio-system -o yaml| sed "s/ClusterIP/NodePort/g"|kubectl apply -f -
kubectl get svc servicegraph -n istio-system
kubectl get svc jaeger-query -n istio-system -o yaml| sed "s/ClusterIP/NodePort/g"|kubectl apply -f -
kubectl get svc prometheus -n istio-system -o yaml| sed "s/ClusterIP/NodePort/g"|kubectl apply -f -
kubectl get svc grafana -n istio-system -o yaml| sed "s/ClusterIP/NodePort/g"|kubectl apply -f -
```


