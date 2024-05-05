#ifndef CHINA_H_
#define CHINA_H_

#include <fstream>
#include <iostream>
#include <math.h>
#include <map>
#include <vector>
#include "define.h"
using namespace std;

void init();

double cal_city_distance(string dis, string guess);

vector<string> city_name_hint(string name);
#endif
