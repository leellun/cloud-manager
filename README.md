# cloud-manager
#### 介绍

采用springcloud最新的框架，组合：

- springcloud2022 
- springcloudalibaba2022 
- springboot3.0.2 
- jdk17  
- spring authorization server1.0.0 

#### 软件架构

前后端分离方案：

spring authorization server自定义password和jwt两种方案。

**jwt**

jwt适合管理系统，通过登录得到loginuser信息，然后userid放入jwt中，loginuser登录信息放入redis中，当通过gateway访问接口时，会先解析Authorization 得到jwt信息，然后获取userid，最后通过userid获取redis中的loginuser信息

方案好处：

当用户信息更改时，可以通过操作redis动态修改用户的权限，以及手动清楚用户的登录信息控制用户登录情况

**password**

对于网站的访问如果不存在手动清理用户信息的操作，可以直接通过password，token比较短

####实战演练

auth-center：认证中心

cloud-gateway：网关

cloud-system：系统管理



