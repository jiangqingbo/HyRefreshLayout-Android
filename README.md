# HyRefreshLayout-Android
继承自SwipeRefreshLayout,从而实现RecyclerView、ListView滑动到底部时上拉加载更多的功能

#### 引用包

    gradle： compile 'com.huyunit:hy-refreshlayout:1.0.3'

----
#### 2016.12.26 

    新增Adapter基础类BaseRecyclerViewAdapter， 并将原来RecyclerViewAdapter重命名为：BaseRefreshRecyclerViewAdapter
    BaseRecyclerViewAdapter是不带上拉刷新功能的，而BaseRefreshRecyclerViewAdapter是带上拉刷新更多FooterView视图。

#### 2016.11.03

    添加自定义上滑动隐藏（SlideHideScrollView控件）ToolBar、FooterView的功能
