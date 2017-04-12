# Samantha

[![Build Status](https://travis-ci.com/jonashao/Samantha.svg?token=w8o6xkMwHcpfRFpW3KZW&branch=master)](https://travis-ci.com/jonashao/Samantha)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/jonashao/Samantha/blob/master/LICENSE)

## 关于

Samantha（沙曼萨）是一个智能助手，可以帮你从短信、邮件等信息源中提取出有用的信息，并自动创建提醒。

这是[郝俊楠](https://junnanhao.com)的本科毕业设计项目, 如果你对这个项目感兴趣，欢迎加入。

## 技能

| 信息类型 | 已实现子类    | 待实现                         |
| ---- | -------- | --------------------------- |
| 火车票  | 12306    | 勾选终点站, 站点时刻表，座位表，去哪儿等其他购票来源 |
| 快递   | 顺丰，申通，中通 | 更多快递公司特色定制                  |
| 会议邀请 |          | 招聘会、宣讲会、学术会议、技术沙龙           |
| 酒店   |          | 名字、位置、电话                    |
| 账单   |          | 蚂蚁、信用卡、白条                   |

## 采用技术和库

| name         | usage    |
| ------------ | -------- |
| Realm        | 数据       |
| ButterKnife  | View绑定   |
| Lombok       | 生成样板代码   |
| RxJava       | Reactive |
| RxPermission | 权限管理     |
| FoldingCell  | 折叠卡片UI   |

## 贡献

如果你希望贡献:

- Fork 这个项目
- 用Android Stuido import项目。请确保SDK Manager勾选了`ConstraintLayout for Android`，`NDK`，`CMake`，`LLDB`
- [Email给我](mailto://imkoche@gmail.com?subject=Samantha贡献),和我聊聊你想做的贡献，我会及时回复一些必要的文档
- 当你完成修改之后, 发起一个Pull请求，等待同意合并

Todo list 可参考[Teambition上的项目](https://www.teambition.com/project/586a58b190af5d9c6643b3f2/)

主要有：

- 正则表达式编写
- 概念描述（如：描述酒店的位置的信息抽取规则、展示形式等）
- Android 
  - ViewHolder
  - Android Layout 
  - Swipe Delete 支持
  - TextView Text Size 自适应支持
  - Color定义
  - Icon 设计/导入

