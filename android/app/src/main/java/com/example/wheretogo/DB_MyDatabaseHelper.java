package com.example.wheretogo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import java.util.ArrayList;


import androidx.annotation.Nullable;

import androidx.annotation.Nullable;

/**
 * 数据库操作类
 */
public class DB_MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "BoolLibrary.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME ="my_library";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "site_name";
    private static final String COLUMN_CITY = "site_city";
    private static final String COLUMN_ADDRESS = "site_address";

    private static final String COLUMN_PID = "site_pid";
    private static final String COLUMN_INTRO = "site_intro";

    private static final String TABLE_NAME_STATUS = "tour_status";
    private static final String COLUMN_ID_STATUS = "_id";
    private static final String COLUMN_TOUR_STATUS = "current_at_site_count";
    private static final String COLUMN_TOTAL_SITES_NUMBER = "total_sites_number";


    DB_MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context; // losing this line cost me a great deal of time to fix it;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // much attention needed to take care of the format
        String query =  "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_CITY + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_PID + " TEXT, " +
                COLUMN_INTRO + " TEXT);";
        db.execSQL(query);


        // step1 : create table;
        String query_tour_status =  "CREATE TABLE " + TABLE_NAME_STATUS +
                " (" + COLUMN_ID_STATUS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TOUR_STATUS +" INTEGER, " +
                COLUMN_TOTAL_SITES_NUMBER + " INTEGER);";
        db.execSQL(query_tour_status);

        {   //default tour status
            // WARNING! WARNING ! Do remember to change total_sites_number if default values are changed...
            int total_sites_number = 34; //TODO: WARNING! WARNING!
            int current_at = 0;
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_TOUR_STATUS, current_at);
            cv.put(COLUMN_TOTAL_SITES_NUMBER, total_sites_number);
            db.insert(TABLE_NAME_STATUS, null, cv);
        }

        // WARNING! WARNING! IF YOU WANT TO CHANGE default values of sites, do remember to change the corresponding total_sites_number(see warning above)
        {
            // default_1
            String name_defalut = "南京大学(鼓楼校区);南大;南大汉口路校门;南京大学";
            String city_default = "南京";
            String address_default = "南京大学(鼓楼校区)-汉口路南门";
            String PID_default = "09002500121709071035569301I";
            String intro_default = "来南京旅游,不能错过的百年历史大学;坐落于南京市中心,俨然有一种大隐隐于市的神秘感;校区内古朴的建筑端庄厚重,和爬山虎、树木、相映成趣;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }
        {
            // Default 2:
            String name_defalut = "梧桐大道;陵园路-道路;梧桐大道九号";
            String city_default = "南京";
            String address_default = "陵园路";
            String PID_default = "09002500121709060812110887H";
            String intro_default = "树是梧桐树,城是南京城;“总要去一趟南京吧，看看这满城的梧桐\";那些道路两侧遍植梧桐,都被我们称作____;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }
        {
            // Default 3:
            String name_defalut = "夫子庙;夫子庙-秦淮风光带;南京夫子庙步行街";
            String city_default = "南京";
            String address_default = "夫子庙";
            String PID_default = "0900250012221013094721969AX";
            String intro_default = "南京,这座拥有着六朝古都历史的城市,每一个角落都蕴含着浓厚的历史文化底蕴.而位于南京城南的___，更是这座城市的文化灵魂;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }
        {
            // Default 4:
            String name_defalut = "玄武湖;玄武湖公园;玄武湖公园-玄武门";
            String city_default = "南京";
            String address_default = "玄武湖";
            String PID_default = "09002500122002220828517715K";
            String intro_default = "___是江南三大名湖之一;历史上的___周边是当时南京最适合人类居住的空间;___是六朝时期的皇家园林湖泊和操练水军的演兵场;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }
        {
            // Default 5:
            String name_defalut = "南京绿地中心紫峰大厦;紫峰大厦";
            String city_default = "南京";
            String address_default = "紫峰大厦";
            String PID_default = "0900250012221008122348381AD";
            String intro_default = "____是位于中国江苏省南京市的一座摩天大楼;它曾是中国第六高楼以及江苏第一高楼,建成时也是世界上排名靠前的超高建筑之一;____集办公、商业、文化娱乐于一体,是南京城市的地标性建筑,并成为游客了解现代南京城市发展的窗口;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }

        {
            // Default 6:
            String name_defalut = "龙门石窟;龙门石窟-西北服务区;龙门石窟景区-票务中心";
            String city_default = "洛阳";
            String address_default = "洛阳市洛龙区伊河两岸";
            String PID_default = "0902780001170525124037000IN";
            String intro_default = "作为东汉时期丝绸之路的起点,洛阳居天下之中,地处洛水之阳,曾为十三王朝古都。说起洛阳,必不可不提____;____是世界上造像最多、规模最大的石刻艺术宝库,“中国石刻艺术的最高峰”;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }

        {
            // Default 7:
            String name_defalut = "秦始皇陵;秦始皇兵马俑博物馆;秦始皇陵地宫;秦始皇";
            String city_default = "西安";
            String address_default = "西安市临潼区骊山北麓";
            String PID_default = "0902150001160228133137000IN";
            String intro_default = "六朝古都,丝绸之路的最初起点,提起西安,涌入脑海的便是千古一帝——___,步入西安,感受古代中国的辉煌与伟大;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }

        {
            // Default 8:
            String name_defalut = "莫高窟;敦煌莫高窟;莫高窟景区-游客中心";
            String city_default = "敦煌";
            String address_default = "甘肃省敦煌市东南鸣沙山东麓崖壁上";
            String PID_default = "2500670012230818170642268OI";
            String intro_default = "敦煌___是丝绸之路上一颗璀璨的明珠;这里不仅汇聚了千年的佛教艺术,更承载着丰富的历史、文化和宗教价值;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }

        {
            // Default 9:
            String name_defalut = "塔尔寺;塔尔寺-售票处;塔尔寺广场";
            String city_default = "西宁";
            String address_default = "青海省西宁市湟中区金塔路";
            String PID_default = "09014100121806211635079596P";
            String intro_default = "西宁,在历史上称湟中,是丝绸之路的重要一环,也是史上唐蕃古道必经之地,链接中原与西部边陲的重要枢纽;___是中国藏传佛教格鲁派（黄教）六大寺院之一,是西宁历史浓厚的一笔;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }

        {
            // Default 10:
            String name_defalut = "交河故城;交河;交河古城峡谷;交河古城游客服务中心";
            String city_default = "吐鲁番";
            String address_default = "吐鲁番市雅尔乡将格勒买斯村";
            String PID_default = "02015700001408291217577502C";//TODO:
            String intro_default = "____是公元前2世纪-公元14世纪丝绸之路东天山南麓吐鲁番盆地的重要的中心城镇,是古代丝绸之路的生动表现与历史印象;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);

        }

        {
            // Default 11:
            String name_defalut = "中西区中环;中环;中环街市;香港中环";
            String city_default = "香港";
            String address_default = "香港特别行政区中西区";
            String PID_default = "09024200121708161056015799T";
            String intro_default = "____,一个充满活力的国际金融中心,也是香港特别行政区的心脏地带;这里不仅是商业和金融活动的集中地,更是东西方文化交融的典范;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }

        {
            // Default 12:
            String name_defalut = "东门步行街;东门步行街-道路;东门老街;东门老街-道路;东门";
            String city_default = "深圳";
            String address_default = "深圳东门步行街";
            String PID_default = "09005700121709101642089199B";
            String intro_default = "深圳_____是深圳市的著名步行街;是深圳形成时间最早、最成熟和最具规模的商业旺区;漫步在_____，享受现代繁华的车水马龙;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }

        {
            // Default 13:
            String name_defalut = "珠江新城;珠江新城-地铁站;";
            String city_default = "广州";
            String address_default = "广州市天河区";
            String PID_default = "09004800011705131121533588W";//TODO:
            String intro_default = "____是广州天河CBD的主要组成部分;是广州市最富现代气息的地区,彰显了广州市的飞速发展与进步;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }

        {
            // Default 14:
            String name_defalut = "陆家嘴;陆家嘴-地铁站;上海陆家嘴中心;陆家嘴世纪金融广场";
            String city_default = "上海";
            String address_default = "上海市浦东新区";
            String PID_default = "09000300121905181704561368P";
            String intro_default = "___是上海国际金融中心的核心功能区,是中国近些年来飞速发展的表现之处;___的光景，给人以深深的震撼与感慨;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }

        {
            // Default 15:
            String name_defalut = "王府井;王府井商业街;王府井-地铁站;王府井百货;王府井大街";
            String city_default = "北京";
            String address_default = "北京市东城区";
            String PID_default = "0100220000130824130211630J5";
            String intro_default = "___是一条具有数百年悠久历史的商业街,在北京享有“金街”美誉;___以其独特的魅力吸引了来自世界各地的游客,在这里,你可以找到各种传统和现代的商品,从丝绸、茶叶到电子产品,应有尽有;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);

        }

        {
            //Default 16:
            String name_defalut = "亚龙湾;亚龙湾国家旅游度假区;亚龙湾站;亚龙湾中心广场";
            String city_default = "三亚";
            String address_default = "三亚亚龙湾国家旅游度假区";
            String PID_default = "0102710000131212230847233S5";
            String intro_default = "___以其美丽的海滩和清澈的海水而闻名;这个地区是热带天堂的缩影,拥有洁白的沙滩、郁郁葱葱的热带植被和丰富的海洋生物;游客可以在这里享受宁静的自然环境，体验当地的文化和历史;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);

        }

        {
            //Default 17:
            String name_defalut = "鼓浪屿;厦门鼓浪屿码头;鼓浪屿轮渡码头";
            String city_default = "厦门";
            String address_default = "福建省厦门市思明区鼓浪屿";
            String PID_default = "0900190001160302142343720IN";
            String intro_default = "游玩被誉为海上花园的厦门,自然少不了___;___,岛西南有一海蚀岩洞受浪潮冲击,声如擂鼓,其上亦有钢琴博物馆;置身其中,聆听海浪与钢琴交奏的曲乐,亦是一大乐事;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }

        {
            //Default 18:
            String name_defalut = "情侣路;珠海情侣路海滨浴场;情侣中路-道路;情侣南路-道路;情侣北路-道路";
            String city_default = "珠海";
            String address_default = "广东省珠海市香洲区";
            String PID_default = "2400720012221220181449187OI";
            String intro_default = "___是一条风景优美的海滨步行路线;沿途植被葱郁,风景如画,可以欣赏到珠海独特的海滨风光,远眺大海和对岸的澳门景色,感受海风拂面的清爽;古代留下的传闻更是为其增添了一份浪漫的氛围,是情侣约会和散步的绝佳场所;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }

        {
            //Default 19:
            String name_defalut = "北戴河海水浴场;北戴河南海水浴场;北戴河东海水浴场;北戴河海滩;北戴河景区";
            String city_default = "秦皇岛";
            String address_default = "秦皇岛中海滩路";
            String PID_default = "09029600121801091516451779S";
            String intro_default = "_______沿海景观秀丽，浪漫迷人,白色的海滩绵延数公里,洁净细腻,是游泳、日光浴和沙滩活动的最佳场所;海水清澈透明,无污染,宛如明镜落入凡尘;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }

        {
            //Default 20:
            String name_defalut = "金沙滩;金沙滩景区";
            String city_default = "青岛";
            String address_default = "山东省青岛市经济技术开发区";
            String PID_default = "0901680012171116162102000IN";
            String intro_default = "___以其绵延不断的金色细沙和清澈碧蓝的海水而闻名,被誉为\"东方夏威夷\";无论是在夏日沐浴阳光,还是漫步观海赏景,都能让人感受到大自然的魅力和海滨度假的乐趣,是一处值得一游的海滨胜地;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }

        {
            //Default 21:
            String name_defalut = "玉龙雪山;玉龙雪山国家风景名胜区;玉龙雪山国家风景名胜区-售票处";
            String city_default = "丽江";
            String address_default = "云南省丽江市北面";
            String PID_default = "09025900122104101434285135L";
            String intro_default = "____冰雪覆盖,终年白雪皑皑,山上高山冰川和奇峰怪石构成了一幅壮美的画卷;____景区内气候凉爽宜人,是夏季避暑的理想胜地;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }

        {
            //Default 22:
            String name_defalut = "黄果树瀑布;黄果树瀑布景区;黄果树瀑布景区-票务中心;黄果树风景名胜区";
            String city_default = "安顺";
            String address_default = "贵州安顺市镇宁县";
            String PID_default = "0900830001170413035448578IN";
            String intro_default = "_____是一处宏大壮丽的多级瀑布群,气势磅礴,水势雄壮,堪称中国最大的瀑布之一;_____水流湍急,水雾飞扬,如银瀑般挂落山间,激起漫天水雾,给人一种令人惊叹的视觉体验;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }

        {
            //Default 23:
            String name_defalut = "天龙天坑;天龙天坑风景区;天龙天坑景观点";
            String city_default = "重庆";
            String address_default = "重庆市武隆区仙女镇";
            String PID_default = "0902920012180322124942000IN";
            String intro_default = "___是重庆最大的国家地质公园，以天然的天坑群、天生桥群、地缝奇观、溪泉飞瀑为主要特色，规模宏大且可进入性强，主要包括武隆天坑、天生三桥景区和武隆地缝景区。公园内岩溶地质、地貌特征尤为突出，拥有世界最大天生桥群和世界第二大天坑群，及集喀斯特地貌奇观为一体的地表裂缝。";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }

        {
            //Default 24:
            String name_defalut = "泰山;泰山风景名胜区;泰山广场";
            String city_default = "泰安";
            String address_default = "山东省泰安市";
            String PID_default = "0901710012171104154611000IN";
            String intro_default = "__风景秀丽,山势险峻,峰巅平坦开阔,龙脊蜿蜒,雄伟壮观的景观令人叹为观止;杜甫的诗句“造化钟神秀，阴阳割昏晓”更是对其的唯美形容;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }

        {
            //Default 25:
            String name_defalut = "敕勒川草原;敕勒川草原文化旅游区;敕勒川公园;敕勒川";
            String city_default = "呼和浩特";
            String address_default = "内蒙古自治区呼和浩特市新城区内蒙古少数民族群众文化体育运动中心附近";
            String PID_default = "0901310012210606122027335HB";
            String intro_default = "_____地势平坦,绿草茵茵,湖泊散布其间,景色宜人,曾经是古代匈奴和汉朝的战场,历史悠久,留下了许多古代遗迹和文化遗产;天苍苍,野茫茫,风吹草低见牛羊;___,阴山下,天似穹庐,笼盖四野";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }

        {
            //Default 26:
            String name_defalut = "九寨沟;九寨沟县;九寨沟风景名胜区;九寨沟国家公园";
            String city_default = "阿坝藏族羌族自治州";
            String address_default = "四川省西北部阿坝藏族羌族自治州九寨沟县中南部";
            String PID_default = "0902090000141021154617700IN";
            String intro_default = "___位于成都北部,因沟内有九个寨子而得名;景区内生物多样性丰富,物种珍稀性突出;___以高山湖泊群、瀑布、彩林、雪峰、蓝冰和藏族风情并称“九寨沟六绝”,被世人誉为“童话世界”,号称“水景之王”;___还是以地质遗迹钙化湖泊、滩流、瀑布景观、岩溶水系统和森林生态系统为主要保护对象的国家地质公园,具有极高的科研价值;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }

        {
            //Default 27:
            String name_defalut = "峨眉山;峨眉山市;峨眉山风景区;峨眉山-金顶";
            String city_default = "乐山";
            String address_default = "乐山市峨眉山市名山南路41号";
            String PID_default = "0901990000140924132234300IN";
            String intro_default = "___位于四川省乐山市境内,景区面积154平方公里,最高峰万佛顶海拔3099米;具有“雄、秀、奇、险、幽”的特色,以优美的自然风光、悠久的佛教文化、丰富的动植物资源、独特的地质地貌著称于世,被人们誉为“仙山佛国”、“植物王国”、“动物乐园”、“地质博物馆”;李白曾写下“___月半轮秋，影入平羌江水流。夜发清溪向三峡，思君不见下渝州。”;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }

        {
            //Default 28:
            String name_defalut = "成都大熊猫繁育研究基地;熊猫基地";
            String city_default = "成都";
            String address_default = "四川省成都市成华区熊猫大道1375号";
            String PID_default = "0901920012171124123329320IN";
            String intro_default = "是世界著名的大熊猫迁地保护基地、科研繁育基地、公众教育基地和教育旅游基地;作为“大熊猫迁地保护生态示范工程”，以保护和繁育大熊猫、小熊猫等中国特有濒危野生动物而闻名于世;大熊猫和花（花花）在各大社交平台爆火，她在2020年7月4日与双胞胎妹妹和叶出生于基地月亮产房;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }

        {
            //Default 29:
            String name_defalut = "三星堆;广汉三星堆博物馆;三星堆遗址";
            String city_default = "德阳";
            String address_default = "德阳市广汉市西安路133号";
            String PID_default = "1801980000140722162401796IN";
            String intro_default = "___距今已有3000至5000年历史,是迄今在西南地区发现的范围最大、延续时间最长、文化内涵最丰富的古城、古国、古蜀文化遗址;现有保存最完整的东、西、南城墙和月亮湾内城墙;___被称为20世纪人类最伟大的考古发现之一,昭示了长江流域与黄河流域一样,同属中华文明的母体，被誉为“长江文明之源”;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }

        {
            //Default 30:
            String name_defalut = "乐山大佛;乐山大佛景区";
            String city_default = "乐山";
            String address_default = "四川省乐山市市中区凌云路中段2435号";
            String PID_default = "0901990000140919161149400IN";
            String intro_default = "____又名凌云大佛,全称为“嘉州凌云寺大弥勒石像”,位于四川省乐山市南岷江东岸凌云寺侧,濒大渡河、青衣江和岷江三江汇流处;大佛为弥勒佛坐像,通高71米,是中国最大的一尊摩崖石刻造像;____开凿于唐代开元元年（713年）,完成于贞元十九年（803年）,历时约九十年;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);

        }

        {
            //Default 31:
            String name_defalut = "千户苗寨;西江千户苗寨;西江苗寨";
            String city_default = "黔东南苗族侗族自治州";
            String address_default = "贵州省黔东南苗族侗族自治州雷山县西江镇南贵村";
            String PID_default = "1800860000140606134322208IN";
            String intro_default = "____由十余个依山而建的自然村寨相连成片,四面环山,重连叠嶂,梯田依山顺势直连云天,白水河穿寨而过,将____一分为二;____在半山建造独具特色的木结构吊脚楼,千余户吊脚楼随着地形的起伏变化,层峦叠嶂;____是一座露天博物馆,展览着一部苗族发展史诗,成为观赏和研究苗族传统文化的大看台;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }

        {
            //Default 32:
            String name_defalut = "大理古城;大理古城步行街;";
            String city_default = "大理";
            String address_default = "云南省大理白族自治州大理市一塔路42号";
            String PID_default = "0902670012200928152445681HS";
            String intro_default = "____东临洱海,西枕苍山,城楼雄伟,风光优美;城中有一贯穿南北的大街,街边有各种专卖大理石制品、扎染、草编等名特产品的店铺和风味十足的白族饮食店;城内流淌着清澈的溪水,到处可见古朴雅的白族传统民居,这里居民不论贫富,都有在庭院内养花种草的习惯;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }

        {
            //Default 33:
            String name_defalut = "喀什古城;喀什古城夜市;喀什古城美食广场";
            String city_default = "喀什";
            String address_default = "新疆维吾尔自治区喀什地区喀什市解放北路";
            String PID_default = "02015200001408061235434906A";
            String intro_default = "____位于新疆,拥有几百年历史,是喀什乃至新疆有名的旅游景点之一;古城内的建筑大多充满了特色风情,街道内纵横交错,风格统一,游客漫步其中,仿佛走进了中亚的异域,感觉十分壮观;另外,喀什古城还是电影《追风筝的人》的重要取景地,来此可以寻找电影中熟悉的场景;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }

        {
            //Default 34:
            String name_defalut = "客家土楼;福建土楼永定景区;土楼";
            String city_default = "龙岩";
            String address_default = "福建省龙岩市永定区湖坑镇洪坑村";
            String PID_default = "09001500001412061243305036Q";
            String intro_default = "永定____是世界一绝,它以历史悠久、千姿百态、规模宏大、结构奇巧、文化内涵丰富著称于世,被誉为“世界上独一无二的神话般的山村民居建筑”,具有聚族而居、安全防卫、防火防震、防风防潮、冬暖夏凉等功能;电影《大鱼海棠》中就曾出现____的身影;";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addSite(String name, String city, String address, String PID, String intro){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME,name);
        // THIS IS INVERSE
        cv.put(COLUMN_ADDRESS,city);
        cv.put(COLUMN_CITY,address);
        cv.put(COLUMN_PID,PID);
        cv.put(COLUMN_INTRO,intro);

        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "新增失败", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "添加成功!", Toast.LENGTH_SHORT).show();
        }
    }

    // called in mainActivity;
    Cursor readAllData(){

        String query = "SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }



    void updateData(String row_id, String name, String city, String address, String PID, String intro){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_CITY,city);
        cv.put(COLUMN_ADDRESS,address);
        cv.put(COLUMN_PID,PID);
        cv.put(COLUMN_INTRO,intro);

        long result = db.update(TABLE_NAME,cv,"_id=?",new String[]{row_id});
        if(result == -1){
            Toast.makeText(context,"更新失败",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"更新成功!",Toast.LENGTH_SHORT).show();
        }
    }
    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getReadableDatabase();
        long result = db.delete(TABLE_NAME,"_id=?",new String[]{row_id});
        if(result == -1){
            Toast.makeText(context,"删除失败",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
        }
    }

    // BELOW are functions for SELECT;

    // cooperate with mainActivity storeSelectedDataInArray (Test usage)
    Cursor readSelectedData(String siteName){

        String query = "SELECT * FROM "+ TABLE_NAME +" WHERE site_name GLOB \'" + siteName + "\'";
        //String query = "SELECT * FROM "+ TABLE_NAME +" WHERE site_name GLOB \'*" + siteName +"*\'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);// this line is wrong!
        }
        return cursor;
    }

    Cursor getPotentialSiteName(String siteName){
        String query = "SELECT * FROM "+ TABLE_NAME +" WHERE site_name GLOB \'*" + siteName +"*\'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);// this line is wrong!
        }
        return cursor;
    }

    String getSelectedPid(String siteName){

        Cursor cursor = readSelectedData(siteName);
        String rtn = null;

        if(cursor.getCount()==0){
            Toast.makeText(context,"未查询到...",Toast.LENGTH_SHORT).show();
            return rtn;
        }else if(cursor.getCount() == 1){
            while(cursor.moveToNext()){
                rtn = cursor.getString(4);
            }
            return rtn;
        }else{
            Toast.makeText(context,"查询到多个...",Toast.LENGTH_SHORT).show();
            return rtn;
        }
    }

    String getSelectedCity(String siteName){
        Cursor cursor = readSelectedData(siteName);
        String rtn = null;

        if(cursor.getCount()==0){
            Toast.makeText(context,"未查询到...",Toast.LENGTH_SHORT).show();
            return rtn;
        }else if(cursor.getCount() == 1){
            while(cursor.moveToNext()){
                rtn = cursor.getString(2);
            }
            return rtn;
        }else{
            Toast.makeText(context,"查询到多个...",Toast.LENGTH_SHORT).show();
            return rtn;
        }
    }

    String getSelectedAddress(String siteName){
        Cursor cursor = readSelectedData(siteName);
        String rtn = null;

        if(cursor.getCount()==0){
            Toast.makeText(context,"未查询到...",Toast.LENGTH_SHORT).show();
            return rtn;
        }else if(cursor.getCount() == 1){
            while(cursor.moveToNext()){
                rtn = cursor.getString(3);
            }
            return rtn;
        }else{
            Toast.makeText(context,"查询到多个...",Toast.LENGTH_SHORT).show();
            return rtn;
        }
    }
    String getSelectedIntro(String siteName){
        Cursor cursor = readSelectedData(siteName);
        String rtn = null;
        if(cursor.getCount()==0){
            Toast.makeText(context,"未查询到...",Toast.LENGTH_SHORT).show();
            return rtn;
        }else if(cursor.getCount() == 1){
            while(cursor.moveToNext()){
                rtn = cursor.getString(5);
            }
            return rtn;
        }else{
            Toast.makeText(context,"查询到多个...",Toast.LENGTH_SHORT).show();
            return rtn;
        }
    }



    // plz ignore this one... this one is for text usage;
    void getSelectedDataTest(){
        {
            //Default test for getSelectedPid() and getSelectedIntro();
            String name_defalut = getNextSiteName();

            String city_default = "南京";
            String address_default = "紫峰大厦";

            String PID_default = getSelectedPid("紫峰大厦");
            String intro_default = getSelectedIntro("紫峰大厦");



            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(TABLE_NAME, null, cv);
            UpdateTourStatus("1", getCurrentAt(), getTotalSitesNumber()+1);
        }
    }

    // getNextSite part：



    // call this function to get the name of next site;
    String getNextSiteName(){
        //step1 : find current_at (which is a counter)
        int current_at = getCurrentAt();
        if(current_at >= getTotalSitesNumber()){
            current_at = 1;
        }else{
            current_at++;
        }

        //step1.2 change value of the table;
        UpdateTourStatus("1",current_at,getTotalSitesNumber());
        //step2 : find row_id of the current_at;
        int row_id = getRow_IdByCurrentAt(current_at);

        //step3: use row_id to find site_name;
        if(row_id == 0){
            Toast.makeText(context,"WENT WRONG!",Toast.LENGTH_SHORT).show();
        }
        return getNameByRowId(row_id);
    }

    // cooperate with the function below;
    String getNameByRowId(int row_id){

        String query = "SELECT * FROM "+ TABLE_NAME +" WHERE _id = " +row_id; // once again LETHAL mistake
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);// this line is wrong!
        }
        String siteName = null;
        while (cursor.moveToNext()) {
            siteName = cursor.getString(1);
        }

        return siteName;
    }
    int getCurrentAt(){
        String query = "SELECT * FROM "+ TABLE_NAME_STATUS +" WHERE _id = 1";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        int current_at = 1;
        if(cursor.getCount() == 1) {
            while (cursor.moveToNext()) {
                current_at = cursor.getInt(1);
            }
        }
        int limit = getTotalSitesNumber();
        if(current_at >= limit){
            current_at = limit;
        }

        return current_at;
    }
    int getTotalSitesNumber(){
        String query = "SELECT * FROM "+ TABLE_NAME_STATUS +" WHERE _id = 1";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        int total_sites_number = 1;
        if(cursor.getCount() == 1) {
            while (cursor.moveToNext()) {
                total_sites_number = cursor.getInt(2);
            }
        }
        return  total_sites_number;
    }
    int getRow_IdByCurrentAt(int current_at){
        SQLiteDatabase db = getReadableDatabase();
        String query2 = "SELECT * FROM "+ TABLE_NAME; // once again LETHAL mistake
        Cursor cursor_rowid = null;
        if(db != null){
            cursor_rowid = db.rawQuery(query2,null);// this line is wrong!
        }
        int counter = 1;
        int row_id = 0;
        while (cursor_rowid.moveToNext()) {
            if (counter == current_at) {
                row_id = cursor_rowid.getInt(0);
            }
            counter++;
        }
        return row_id;
    }

    void UpdateTourStatus(String row_id, int new_current_at, int new_total_sites){
        SQLiteDatabase db = this.getWritableDatabase();

        row_id = "1";

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TOUR_STATUS,new_current_at);
        cv.put(COLUMN_TOTAL_SITES_NUMBER,new_total_sites);

        db.update(TABLE_NAME_STATUS,cv,"_id=?",new String[]{row_id});

    }
    void ResetTourStatus(){
        UpdateTourStatus("1",0,getTotalSitesNumber());
    }
}
/*




 */
