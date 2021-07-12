import 'package:dio/dio.dart';

typedef SuccessCallback = void Function(dynamic data);
typedef ErrorCallback = void Function(String error);

class HttpManager {
  HttpManager._();

  static BaseOptions _baseOptions =
      BaseOptions(headers: {"api-token": "4d65e2a5626103f92a71867d7b49fea0"});
  static Dio _dio = Dio(_baseOptions);

  static get(String url, SuccessCallback success, ErrorCallback error) async {
    try {
      Response response = await _dio.get(url);
      if (response.statusCode != 200) {
        throw Exception(
            "statusCode=${response.statusCode},statusMessage=${response.statusMessage}");
      } else {
        success(response.data);
      }
    } catch (e) {
      error(e.toString());
    }
  }

  static Future request(String url) async {
    try {
      Response response = await _dio.get(url);
      if (response.statusCode != 200) {
        throw Exception(
            "statusCode=${response.statusCode},statusMessage=${response.statusMessage}");
      } else {
        return response.data;
      }
    } catch (e) {
      throw Exception(e.toString());
    }
  }
}
