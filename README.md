# OpenGitHub
一款基于Material Design + Kotlin + MVVM + ViewModel + LiveData  + Room + Retrofit + Okhttp + 协程 + LiveDataBus + Glide等架构实现Github客户端项目(持续更新中),欢迎start or fork！

# 项目截图
<div style="float:right">
  <img src="https://github.com/fmtjava/OpenGitHub/blob/master/image/1281564044992_.pic.jpg" width="270"/>&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/fmtjava/OpenGitHub/blob/master/image/1301564044994_.pic.jpg" width="270"/>&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/fmtjava/OpenGitHub/blob/master/image/1371564048402_.pic.jpg" width="270"/>
</div>

<br/>

<div style="float:right">
  <img src="https://github.com/fmtjava/OpenGitHub/blob/master/image/1351564044999_.pic.jpg" width="270"/>&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/fmtjava/OpenGitHub/blob/master/image/1341564044998_.pic.jpg" width="270"/>&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/fmtjava/OpenGitHub/blob/master/image/1291564044993_.pic.jpg" width="270"/>
</div>
<br/>

<div style="float:right">
  <img src="https://github.com/fmtjava/OpenGitHub/blob/master/image/1321564044996_.pic.jpg" width="270"/>&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/fmtjava/OpenGitHub/blob/master/image/1331564044997_.pic.jpg" width="270"/>&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/fmtjava/OpenGitHub/blob/master/image/1311564044995_.pic.jpg" width="270"/>
</div>

# 下载体验
   (下方二维码，每日上限100次，如达到上限，还是 clone 源码吧！✧(≖ ◡ ≖✿)）<br/>
   <img src="https://github.com/fmtjava/OpenGitHub/blob/master/image/1564135578.png"/>

# 核心技术栈
## Kotlin语言
  听说Android开发与Kotlin更配哦！Kotlin语言100%兼容Java,并且具有数据类、空安全、延迟加载、属性代理、拓展方法、函数表达式、高级函数等高级特性，极大
  的简化代码量，不需要在findViewById、不需要为空指针烦恼、不需要编写复杂的JavaBean,让你能够更专注与业务，kotlin是一门小清新的语言，只有你Java足够
  扎实，上手kotlin会很方便，kotlin会让你开发Android APP如沐春风，快点学习Kotlin吧!
    
## AAC架构
   还在为MVP内存泄露而烦恼吗？还在苦恼App架构选型吗？Googel官方AAC架构来袭，让你的App架构更加清晰，不在为内存泄露而烦恼。架构图如下
   <img src="https://camo.githubusercontent.com/2b3ff9b3a5f99c5480b612aa8f4f678dc696987a/68747470733a2f2f757365722d676f6c642d63646e2e786974752e696f2f323031392f342f31352f313661323130313664663963373663353f773d39363026683d37323026663d7765627026733d3135333832"/>
&#8195;
Model-View-ViewModel，View 指绿色的 Activity/Fragment，主要负责界面显示，不负责任何业务逻辑和数据处理。Model 指的是 Repository 包含的部分，主要负责数据获取，来组本地数据库或者远程服务器。ViewModel 指的是图中蓝色部分，主要负责业务逻辑和数据处理，本身不持有 View 层引用，通过 LiveData 向 View 层发送数据。Repository 统一了数据入口，不管来自数据库，还是服务器，统一打包给 ViewModel。
### 核心组件
   - ViewModel：以注重生命周期的方式管理界面相关的数据；
   - LiveData：在底层数据库更改时通知视图；
   - Room：流畅地访问 SQLite 数据库；
   - Lifecycles：管理您的 Activity 和 Fragment 生命周期；
   
## Retrofit + Okhttp + Coroutines(协程)打造强大的网络请求
   - Retrofit：Square出品的网络请求库，极大的减少了http请求的代码和步骤
   - Okhttp： 同样Square出品，不多介绍，做Android都应该知道
   - Coroutines(协程)：kotlin1.3版本发布，Coroutines稳定版也正式发布，Coroutines真是非常神奇，让你可以使用同步的方式写异步请求代码，增强代码的
                      可读性、不在为回调而烦恼、优雅的进行线程切换操作，Coroutines的“黑魔法“特性会让你眼前一亮。
## LiveEventBus
   LiveEventBus是一款Android消息总线，基于LiveData，具有生命周期感知能力，支持Sticky，支持AndroidX，支持跨进程，支持跨APP
   
## Glide   
   Glide相比起Fresco要轻量很多，api调用起来也很简洁，对图片加载要求不是很高的话建议使用Glide。
   
# 更新日志
 ### v1.0
       -初始化项目，完成Github App核心功能
# Thanks
  - [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines)
  - [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
  - [AgentWeb](https://github.com/Justson/AgentWeb)
  - [Glide](https://github.com/bumptech/glide)
  - [Toasty](https://github.com/GrenderG/Toasty)
  - [LikeButton](https://github.com/jd-alexander/LikeButton)
  - [CircleImageView](https://github.com/hdodenhof/CircleImageView)
  - [LiveEventBus](https://github.com/JeremyLiao/LiveEventBus)
  - [simple-view-behavior](https://github.com/zoonooz/simple-view-behavior)
  - [Okhttp](https://github.com/square/okhttp)
  - [Retrofit](https://github.com/square/retrofit)
  
 # 关于我
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
  
  
  
  
  
  
   
   
   
   
   
   
