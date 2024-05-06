#include "../inc/define.h"
#include "../inc/China.h"

static city China[4000];
string city_name[4000];
map<string, int> name_to_idx;

void init()
{
    ifstream fs;
    fs.open("inc/open1.txt", ios::out);
    if (!fs.is_open()) {
        cerr << "fail to open!" << endl;
    }
    else {
        //cout << "well_functioning!" << endl;
    }

    int cnt = 1;
    int t1;
    string name;
    double x;
    double y;
    while (fs >> t1)
    {
        fs >> name;
        city_name[cnt] = name;
        name_to_idx[name] = cnt;
        fs >> x >> y;
        China[cnt].idx = cnt;
        China[cnt].x = x;
        China[cnt].y = y;
        cnt++;
    }
}

double HaverSin(double theta)
{
    double v = sin(theta / 2);
    return v * v;
}

double ConvertDegreesToRadians(double jiaodu)
{
    return jiaodu * 3.14159 / 180.0;
}


double TwoPointDistance(double from_lon, double from_lat, double to_lon, double to_lat)
{
    const double EARTH_RADIUS = 6371.0;
    // 用 haversine 公式计算球面两点间的距离。
    // 经纬度转换成弧度
    from_lon = ConvertDegreesToRadians(from_lon);
    from_lat = ConvertDegreesToRadians(from_lat);
    to_lon = ConvertDegreesToRadians(to_lon);
    to_lat = ConvertDegreesToRadians(to_lat);
    // 差值
    double vLon = std::abs(from_lon - to_lon);
    double vLat = std::abs(from_lat - to_lat);
    // h is the great circle distance in radians, great circle 就是一个球体上的切面，它的圆心即是球心的一个周长最大的圆。
    double h = HaverSin(vLat) + cos(from_lat) * cos(to_lat) * HaverSin(vLon);
    auto sh = sqrt(h);
    double distance = 2 * (asin(sqrt(h))) * EARTH_RADIUS;

    return distance;
}


double cal_city_distance(string dis, string guess)
{
    int idx_dis = name_to_idx[dis];
    int idx_guess = name_to_idx[guess];
    double x1 = China[idx_dis].x;
    double y1 = China[idx_dis].y;
    double x2 = China[idx_guess].x;
    double y2 = China[idx_guess].y;

    return TwoPointDistance(x1,y1,x2,y2);
};

void execute_cal_city_distance()
{
    init();

    string city1;
    string city2;
    while (cin >> city1 >> city2)
    {
        cout << cal_city_distance(city1, city2) << endl;
    }
}
/*
vector<string> city_name_hint(string name)
{
    vector<string> rtn;
	for(string str: city_name)
	{
		if(str.find(name))
		{
            rtn.push_back(str);
		}
	}
    return rtn;
}

*/
