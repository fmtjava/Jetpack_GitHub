import 'package:dio/dio.dart';
import 'package:flutter_module/http/http_manager.dart';
import 'package:flutter_module/model/follow_model.dart';

class FollowRepository {
  static Future<List<FollowModel>> getFollowList(String userName,String type,int page) async {
    var url = "https://api.github.com/users/$userName/$type";
    Map<String, dynamic> queryParameters = {"page": page, "per_page": 10};
    Options options = Options(headers: {"Authorization":"Token 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"});
    List<FollowModel> followList = [];
    dynamic data =
        await HttpManager.request(url, queryParameters: queryParameters,options: options);
    List<dynamic> list = data;
    list.forEach((element) {
      followList.add(FollowModel.fromJson(element));
    });
    return followList;
  }
}
