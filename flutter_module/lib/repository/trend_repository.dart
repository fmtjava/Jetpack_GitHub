import 'package:flutter_module/http/http_manager.dart';
import 'package:flutter_module/model/trend_model.dart';

class TrendRepository {
  static Future<List<TrendModel>> getTrendList({String since = "daily"}) async {
    var url = "https://guoshuyu.cn/github/trend/list?since=$since";
    List<TrendModel> trendList = [];
    dynamic data = await HttpManager.request(url);
    List<dynamic> list = data;
    list.forEach((element) {
      trendList.add(TrendModel.fromJsonMap(element));
    });
    return trendList;
  }
}
