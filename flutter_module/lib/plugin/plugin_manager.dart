import 'package:flutter/services.dart';

/// Dart与Android通信管理类
class PluginManager {
  PluginManager._();

  //MethodChannel：全双工，一次性通信
  static const MethodChannel _methodChannel =
      MethodChannel('MethodChannelPlugin');

  static void go2ReposDetail(String webUrl, String repo, String owner) {
    _methodChannel.invokeMapMethod(
        'go2ReposDetail', {"web_url": webUrl, "repo": repo, "owner": owner});
  }
}
