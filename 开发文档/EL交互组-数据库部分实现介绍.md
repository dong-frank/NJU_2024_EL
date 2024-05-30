**一、DB\_MainActivity**

（一）功能简介：实现呈现数据库数据的主界面；

（二）核心任务：

1. 使用DB\_MyDatabaseHelper 类进行数据库操作，读取数据并将其显示在列表中；
1. 点击添加按钮会启动另一个活动 DB\_AddActivity，允许用户输入新信息并将其添加到数据库中。
1. 使用 DB\_CustomAdapter 类自定义适配器来管理 RecyclerView 中的列表项，并与数据库中的数据进行交互。

**二、DB\_MyDatabaseHelper**

（一）功能简介：实现与数据库交互的各个功能；

（二）功能及其实现：

1. 功能1：在数据新建表格 以及数据的初始化

1. 功能2：添加和更新数据

1. 功能3：查询数据 和 管理游览进度

**三、DB\_UpdateActivity & DB\_AddActivity**

（一）功能简介：配合MyDatabaseHelper实现更新和添加的功能，并管理更新和添加的界面

（二）代码：

1. AddAcitvity

1. 添加操作的具体实现

1. UpdateActivity 

1. 更新功能的实现：

5．删除功能的实现


（三）布局效果：



**四、DB\_CustomAdapter**

（一）功能简介：将站点信息显示在RecyclerView中，并处理点击事件，调用UpdateActivity

（二）核心功能及其实现：将内容绑定在MyViewHolder上，并处理点击事件

（三）布局及效果：



**五、SQLite数据库的相关信息：**

（一）表格介绍：数据库中存放两张表格

（1）表格1：my\_library 用来管理 景点相关信息

（2）表格2：tour\_status 用来管理 当前旅游进度


（二）数据库交互的几个核心技术：

（1）建表格

（2）添加数据

（3）更新数据

（4）删除数据

（5）查询数据



