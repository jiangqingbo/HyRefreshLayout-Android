# HyRefreshLayout-Android
继承自SwipeRefreshLayout,从而实现RecyclerView、ListView滑动到底部时上拉加载更多的功能

#### 引用包

    gradle： compile 'com.huyunit:hy-refreshlayout:1.0.4'

----
#### 更新日期：2016.12.26

    1、升级gradle插件版本2.3-3.3
    2、升级sdk版本25.0.2_25.2.0
    3、修改上拉更多初始化状态
    4、发布新版本1.0.4_4

#### 更新日期：2016.12.26 

    新增Adapter基础类BaseRecyclerViewAdapter， 并将原来RecyclerViewAdapter重命名为：BaseRefreshRecyclerViewAdapter
    BaseRecyclerViewAdapter是不带上拉刷新功能的，而BaseRefreshRecyclerViewAdapter是带上拉刷新更多FooterView视图。

#### 更新日期：2016.11.03

    添加自定义上滑动隐藏（SlideHideScrollView控件）ToolBar、FooterView的功能
