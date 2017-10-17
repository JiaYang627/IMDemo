# IMDemo

***
> 写在前面：

> 一、项目中获取联系人的时候发现了一个问题：用户A 和 B ，安装好程序，登录A切换至联系人界面，然后退出登录，此时在登录账户B ，也切换至联系人界面。退出B 再次登录A 此时联系人界面是账户B的好友列表，一开始以为是自己的问题，断点调试，发现环信给的数据就是展示的数据。不知道是不是环信后台的问题。环信对注册 并登录后 的用户在手机建有对应用户的数据库，不知道是不是因为缓存的问题。

> 二、在搜索好友的时候，搜索的是第三方比目的后台，而且搜索的条件是以搜索内容开头的，结果比目将后台所有用户返回回来。此问题个人觉得应数据第三方比目的问题。

* 项目框架(依旧使用的MVP模式开发)：


* 联网 (一开始想着使用自己封装的MVP框架开发，就依旧封装了联网的东西。这个项目中所有联网都是 环信、比目自己的和自己的完全联网没有一点关系)
* 消息通知：EventBus
* UI绑定：ButterKnife
* 第三方：
* 后台管理：比目(Bmob)
* 聊天：环信

## AndroidUI

> 聊天UI图(另外一部手机就不录屏了)
![IMDemoUI-ONE](http://a2.qpic.cn/psb?/V14YlNrL2eQEkW/QNHrnSQgDvhgiHzR2qJD05Cwsm4DrKmZPAPxOkzgFVk!/c/dDQAAAAAAAAA&bo=GgKzAwAAAAACB4s!&rf=viewer_4)

***

> 账号多端登录
![IMDemo-TWO](http://a1.qpic.cn/psb?/V14YlNrL2eQEkW/TmmLhbGP2q8jsmie.rFDWKAMnQ7uRJgSAaRntaWX*UU!/b/dJYAAAAAAAAA&bo=HAK3AwAAAAACTsA!&rf=viewer_4)

***

> 联系人 右侧字母自定义控件
![IMDemo-THREE](http://a1.qpic.cn/psb?/V14YlNrL2eQEkW/CHzbM2izRGzp7b.nPcLwj2t*0ywVa*t8UVNDEio6vjg!/b/dLEAAAAAAAAA&bo=GQK3AwAAAAACcPs!&rf=viewer_4)

***
* 写在最后：此项目只为再次练习MVP框架。聊天主要依附的是第三方环信。