import 'package:dio/dio.dart';

typedef SuccessCallback = void Function(dynamic data);
typedef ErrorCallback = void Function(String error);

class HttpManager {
  HttpManager._();

  static BaseOptions _baseOptions =
      BaseOptions(headers: {"api-token": "4d65e2a5626103f92a71867d7b49fea0"});
  static Dio _dio = Dio(_baseOptions);

  static get(String url, SuccessCallback success, ErrorCallback error,
      {Map<String, dynamic> queryParameters}) async {
    try {
      Response response = await _dio.get(url, queryParameters: queryParameters);
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

  static Future request(String url,
      {Map<String, dynamic> queryParameters, Options options}) async {
    try {
      Response response = await _dio.get(url,
          queryParameters: queryParameters, options: options);
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
