# Jetpack_GitHub
[![Platform](https://img.shields.io/badge/platform-Android-green.svg)](https://github.com/fmtjava/OpenGitHub)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Release Version](https://img.shields.io/badge/version-2.3-red.svg)](https://fir.im/8jw7)
[![](https://img.shields.io/badge/Author-fmtjava-blue.svg)](https://github.com/fmtjava)
[![](https://img.shields.io/badge/QQ-2694746499-orange.svg)](https://github.com/fmtjava)<br />
基于Kotlin + Jetpack全家桶 + Coroutines(协程) 等架构实现的一款精简版Github客户端项目。<br />

**开源不易，如果喜欢的话希望给个 `Star` 或 `Fork` ^_^ ，谢谢**

# 项目截图
<div style="float:right">
  <img src="https://github.com/fmtjava/OpenGitHub/blob/master/image/1871575531757_.pic.jpg" width="270"/>&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/fmtjava/OpenGitHub/blob/master/image/1851575526269_.pic.jpg" width="270"/>&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/fmtjava/OpenGitHub/blob/master/image/1301564044994_.pic.jpg" width="270"/>
</div>

<br/>

<div style="float:right">
  <img src="https://github.com/fmtjava/OpenGitHub/blob/master/image/1371564048402_.pic.jpg" width="270"/>&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/fmtjava/OpenGitHub/blob/master/image/1351564044999_.pic.jpg" width="270"/>&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/fmtjava/OpenGitHub/blob/master/image/2281581130992_.pic.jpg" width="270"/>
</div>
<br/>

<div style="float:right">
  <img src="https://github.com/fmtjava/OpenGitHub/blob/master/image/1771574405820_.pic.jpg" width="270"/>&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/fmtjava/OpenGitHub/blob/master/image/1781574405821_.pic.jpg" width="270"/>&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/fmtjava/OpenGitHub/blob/master/image/1291564044993_.pic.jpg" width="270"/>
</div>

<div style="float:right">
  <img src="https://github.com/fmtjava/OpenGitHub/blob/master/image/1321564044996_.pic.jpg" width="270"/>&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/fmtjava/OpenGitHub/blob/master/image/1331564044997_.pic.jpg" width="270"/>&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/fmtjava/OpenGitHub/blob/master/image/1311564044995_.pic.jpg" width="270"/>
</div>

# 下载体验 
 - 点击[![](https://img.shields.io/badge/Download-apk-green.svg)](https://www.pgyer.com/3oGy) 下载(密码:123456)
 - 下方二维码下载(每日上限100次，如达到上限，还是 clone 源码吧！✧(≖ ◡ ≖✿))）<br/>
   <img src="https://www.pgyer.com/app/qrcode/3oGy"/>

# 核心技术栈
## Kotlin语言
  听说Android开发与Kotlin更配哦！Kotlin语言100%兼容Java,并且具有数据类、空安全、延迟加载、属性代理、拓展方法、函数表达式、高级函数等高级特性，极大
  的简化代码量，不需要在findViewById、不需要为空指针烦恼、不需要编写复杂的JavaBean,让你能够更专注与业务，kotlin是一门小清新的语言，只要你Java足够
  扎实，上手kotlin会很方便，kotlin会让你开发Android APP如沐春风，快点学习Kotlin吧!
    
## AAC架构
   还在为MVP内存泄露而烦恼吗？还在苦恼App架构选型吗？Googel官方AAC架构来袭，让你的App架构更加清晰，不在为内存泄露而烦恼。架构图如下
   <img src="https://camo.githubusercontent.com/2b3ff9b3a5f99c5480b612aa8f4f678dc696987a/68747470733a2f2f757365722d676f6c642d63646e2e786974752e696f2f323031392f342f31352f313661323130313664663963373663353f773d39363026683d37323026663d7765627026733d3135333832"/>
&#8195;
Model-View-ViewModel，View 指绿色的 Activity/Fragment，主要负责界面显示，不负责任何业务逻辑和数据处理。Model 指的是 Repository 包含的部分，主要负责数据获取，来组本地数据库或者远程服务器。ViewModel 指的是图中蓝色部分，主要负责业务逻辑和数据处理，本身不持有 View 层引用，通过 LiveData 向 View 层发送数据。Repository 统一了数据入口，不管来自数据库，还是服务器，统一打包给 ViewModel。
### 核心组件
   - Lifecycles：它持有关于组件（如 Activity 或 Fragment）生命周期状态的信息，并且允许其他对象观察此状态
   - ViewModel：以注重生命周期的方式管理界面相关的数据,为Activity 、Fragment存储数据，直到完全销毁；
   - LiveData：不用手动控制生命周期，不用担心内存泄露，数据变化时会收到通知，与ViewModel的组合使用可以说是双剑合璧，而Lifecycles贯穿其中；
   - DataBinding：将布局组件与源数据绑定，使源数据变化的同时布局组件及时同步更新，与ViewModel、LiveData、Lifecycles搭配使用，能够碰撞出极致的MVVM火花
   - Room：流畅地访问 SQLite 数据库；
   
## koin
   koin 是一个用于kotlin的实用型轻量级依赖注入框架，采用纯kotlin编写而成，仅使用功能解析，无代理、无代码生成、无反射。koin 是一个DSL,一个轻便的容易和一个使用的API。从此告别Dagger2的困境，让依赖注入更加简单。
   
## Retrofit + Okhttp + Coroutines(协程)打造强大的网络请求
   - Retrofit：Square出品的网络请求库，极大的减少了http请求的代码和步骤
   - Okhttp： 同样Square出品，不多介绍，做Android都应该知道
   - Coroutines(协程)：kotlin1.3版本发布，Coroutines稳定版也正式发布，Coroutines真是非常神奇，让你可以使用同步的方式写异步请求代码，增强代码的可读性、不在为回调而烦恼、优雅的进行线程切换操作，Coroutines的“黑魔法“特性会让你眼前一亮。

## Coroutines(协程)
   还在为线程切换而烦恼吗? 还在深陷回调的噩梦吗? 还在为Thread初始化带来的性能损耗而烦恼? kotlin1.3协程稳定版来袭，专治上述的疑难杂症，Android开发使用协程具备以下优点：
   - 协程依赖于线程，但是协程挂起时不需要阻塞线程，几乎是无代价的，协程是由开发者控制的。
   - 异步编程时无需编写大量的回调接口，Coroutines的“黑魔法“特性让你用同步的方式编写代码，增强代码可读性
   - 协程调度器代替Handler、AsyncTask、Rxjava更优雅的处理线程切换

## LiveEventBus
   LiveEventBus是一款Android消息总线，基于LiveData，具有生命周期感知能力，支持Sticky，支持AndroidX，支持跨进程，支持跨APP
   
## Assent
   轻便且灵活的Android动态权限申请框架，支持Kotlin和AndroidX
   
## Glide   
   Glide相比起Fresco要轻量很多，api调用起来也很简洁，对图片加载要求不是很高的话建议使用Glide。
   
# 更新日志
### v2.3
  * 新增OAuth2授权登录方式，旧版登录方式官方不再推荐(官方2020/11后废弃旧版登录方式)
  * 调整项目结构，优化代码
### v2.2
  * 接入Navigation改写动态页面
  * 调整项目结构，优化代码
### v2.1
  * 接入WorkManager实现版本更新功能
  * 新增SmallestWidth限定符屏幕适配方案
### v2.0
  * 接入Paging改写动态分页列表页面,并封装Paging版分页模版(BasePagingVMFragment、BaseLPagingModel)
  * 提供Paging版分页列表模版以及普通版分页列表模版，方便对比学习
  * 下个版本计划加入Navigation
### v1.9
  * 添加动态页面
  * 调整项目结构，优化代码
### v1.8
  * 添加App启动优化代码，提升App的启动速度
  * 调整项目结构，优化代码
 ### v1.7
  * 使用协程进一步简化异步代码，增强代码的可读性
  * BaseActivity、BaseMVActivity等基类再次进行封装
 ### v1.6
  * 封装通用列表以及统一分页模版代码
  * 使用FragmentTransaction#setMaxLifecycle(Fragment, Lifecycle.State)替换setUserVisibleHint，实现新的Fragment懒加载方案
  * BaseFragment以及BaseActivity等基类再次进行封装
  * 调整项目结构，优化代码
 ### v1.5
  * 添加动态权限申请功能，优化欢迎页以及登陆页的样式以及逻辑
  * 调整项目结构，优化代码
 ### v1.4
  * 完善搜索功能，新增用户以及仓库排序搜索
  * 调整项目结构，优化代码
 ### v1.3
  * 加入koin依赖注入框架完善AAC架构
  * 调整项目结构，优化代码
 ### v1.2
  * 修复Activity/Fragment自定义异常处理没有调用问题
  * 调整项目结构，优化代码
 ### v1.1
  * 启动页添加svg动画
  * 加入DataBinding完善AAC架构
  * LastAdapter替换BaseRecyclerViewAdapterHelper,结合DataBinding更方便
  * 调整项目结构，优化代码
 ### v1.0
   * 初始化项目，完成Github App核心功能
# Thanks
  - [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines)
  - [koin](https://github.com/InsertKoinIO/koin)
  - [Assent](https://github.com/afollestad/assent)
  - [LastAdapter](https://github.com/nitrico/LastAdapter)
  - [AgentWeb](https://github.com/Justson/AgentWeb)
  - [Glide](https://github.com/bumptech/glide)
  - [Toasty](https://github.com/GrenderG/Toasty)
  - [LikeButton](https://github.com/jd-alexander/LikeButton)
  - [CircleImageView](https://github.com/hdodenhof/CircleImageView)
  - [SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout)
  - [AnimatedSvgView](https://github.com/jaredrummler/AnimatedSvgView)
  - [MultiStateView](https://github.com/Kennyc1012/MultiStateView)
  - [XPopup](https://github.com/li-xiaojun/XPopup)
  - [LiveEventBus](https://github.com/JeremyLiao/LiveEventBus)
  - [simple-view-behavior](https://github.com/zoonooz/simple-view-behavior)
  - [Okhttp](https://github.com/square/okhttp)
  - [Retrofit](https://github.com/square/retrofit)
 
  
 # 关于我
  - WX：fmtjava
  - QQ：2694746499
  - Email：2694746499@qq.com
  - Github：https://github.com/fmtjava
  
 # License 
 

Copyright (c) 2019 fmtjava

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
  
  
  
  
  
  
   
   
   
   
   
   
