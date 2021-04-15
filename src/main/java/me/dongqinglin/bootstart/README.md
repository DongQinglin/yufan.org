# 概览
```
config 配置
controller 控制器层
entity 实体类
filter 过滤器层，切面编程
repository 仓库层
service 业务层
state 状态转化层
```

> 状态层，state 之间的转化依赖于action，一个action对象描述了行为，一个state和一个action作为reducer的入参，将转化为另外一个state。reducer需要遵循纯函数规范。
