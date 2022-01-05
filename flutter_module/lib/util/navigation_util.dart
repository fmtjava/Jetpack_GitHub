import 'package:flutter_boost/flutter_boost.dart';

//混合开发路由导航器使用了flutter_boost https://github.com/alibaba/flutter_boost/blob/master/README_CN.md
class NavigationUtil {
  static push(String name,
      {Map<String, dynamic> arguments,
      bool withContainer = false,
      bool opaque = true}) {
    BoostNavigator.instance.push(name,
        arguments: arguments, withContainer: withContainer, opaque: opaque);
  }

  static Future<bool> pop<T extends Object>([T result]) {
    return BoostNavigator.instance.pop(result);
  }
}
