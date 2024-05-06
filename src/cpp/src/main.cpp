#include <iostream>
#include <fstream>
#include <string>
#include "../inc/China.h"
using namespace std;

int main() {
    init();

    string city1;
    string city2;
    while(cin >> city1 >> city2)
    {
        cout << cal_city_distance(city1, city2) << endl;
    }
    return 0;
}