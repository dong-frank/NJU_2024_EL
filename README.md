# 项目介绍

## 小组成员
1. 组长 饶东申
2. 组员 张珂铫
3. 组员 许立恒
4. 组员 陆亭羽

## 项目名称 鼠鼠去那儿app

## 简介
1. **鼠鼠去那儿**,是一款趣味地理解密类型的安卓游戏.
2. 在**鼠鼠去那儿**中,一只正在周游世界的小仓鼠,在他旅游的过程中拍下了每一个他去过的地方的**全景图片**,并附带上了这个地方的**风土人情**,发给了他的人类朋友们.
3. 作为他的人类好朋友,我们通过这个app,可以足不出户地通过全景图片,**身临其境**般地感受鼠鼠去过的地方,和那边的风土人情.
4. 虽然鼠鼠已经去过了很多地方,但他相信地球上总有地方他还没去过,所以他邀请人类朋友们为鼠鼠**提供目的地的建议**,如果能写上一段**走心的介绍**,鼠鼠就更开心啦!

## 主要功能和实现方式
1. 全景地图的呈现
*百度地图Android全景SDK*
2. 地点的记录和用户增加删减地点
*SQLite数据库*
3. 沉浸式全景体验
*陀螺仪传感器结合全景图片*
4. 地点联想搜索
*百度地图Sug检索*
5. 用户输入地点转换坐标
*百度地图地理编码*

## 游玩方式
1. 进入app务必点击**同意隐私**,否则无法正常进行.
2. 点击**开始游戏**,进入主体游戏界面,可以手指上下左右滑动屏幕,移动全景图片进行观察.
3. 点击右下角**全屏按钮**,进入沉浸式全景体验,可以左右移动手机进行观察.**再次点击**即可退出
4. 点击**输入框**进行输入答案,答案支持模糊匹配,*例:南大,南大鼓楼校区,南京大学,南京大学(鼓楼校区)*
5. 也可通过点击**联想列表**中的选项进行输入
6. 若**答案错误**,鼠鼠会给出**提示**,不用担心一直卡着,**多尝试几次**,最后善良的鼠鼠会给出答案.
7. **答案正确**,则弹出**恭喜你回答正确**对话框,并显示地点的介绍.
8. 这时你可以选择**继续游戏**,进入下一个地点.
9. 也可以选择**随便走走**,这时游戏切换到**地图模式**,用户可以选择**临近的箭头**走进景点,也可以自行决定前往**哪个城市**的**哪个地点**  
注意:由于百度地图全景覆盖并不完全,可能用户输入的地点并没有相应的全景图片,这时建议输入他相邻的地点或换一个名称搜索.
11. 在**地图模式**下,点击右上角的**收藏**,可以向鼠鼠推荐这个地点.这时会弹出一个对话框,请用户输入这个地点的介绍,越详细鼠鼠越可能去哟.
12. 用户可以随时点击右上角的**地图**进行**游戏模式**和**地图模式**的切换.
13. 游戏过程中用户可以随时点击**底部导航栏**,打开**鼠鼠去过哪**界面,在这个界面中记录了用户**已经发现**的鼠鼠的足迹,和未知的用户**还未发现**的地方,和用户**向鼠鼠推荐**鼠鼠准备去的地方.
14. **鼠鼠去过哪**也可通过在**主页**点击相关按钮打开.


