import 'dart:io';
import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_boost/flutter_boost.dart';
import 'package:flutter_module/page/trend_page.dart';
import 'color/color.dart';

void main() {
  //这里的CustomFlutterBinding调用务必不可缺少，用于控制Boost状态的resume和pause
  CustomFlutterBinding();
  runApp(MyApp(window.defaultRouteName));
  if (Platform.isAndroid) {
    // 以下两行 设置android状态栏为透明的沉浸。写在组件渲染之后，是为了在渲染后进行set赋值，覆盖状态栏，写在渲染之前MaterialApp组件会覆盖掉这个值。
    SystemUiOverlayStyle systemUiOverlayStyle =
        SystemUiOverlayStyle(statusBarColor: Colors.transparent);
    SystemChrome.setSystemUIOverlayStyle(systemUiOverlayStyle);
  }
}

///4.创建一个自定义的Binding，继承和with的关系如下，里面什么都不用写
class CustomFlutterBinding extends WidgetsFlutterBinding
    with BoostFlutterBinding {}

class MyApp extends StatelessWidget {
  final String defaultRoute;

  const MyApp(this.defaultRoute, {Key key}) : super(key: key);

  //1.配置路由表
  static Map<String, FlutterBoostRouteFactory> routerMap = {
    'trendPage': (settings, uniqueId) {
      return PageRouteBuilder(
          settings: settings,
          pageBuilder: (_, __, ___) {
            return TrendPage();
          });
    }
  };

  //2.创建路由工厂
  Route<dynamic> routeFactory(RouteSettings settings, String uniqueId) {
    FlutterBoostRouteFactory func = routerMap[settings.name];
    if (func == null) {
      return null;
    }
    return func(settings, uniqueId);
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'GitHub',
        theme: ThemeData(
          primarySwatch: createMaterialColor(DColor.themeColor),
        ),
        //3.通过FlutterBoostApp启动Flutter
        home: FlutterBoostApp(routeFactory));
  }
}
//自定义主题颜色
MaterialColor createMaterialColor(Color color) {
  List strengths = <double>[.05];
  Map swatch = <int, Color>{};
  final int r = color.red, g = color.green, b = color.blue;

  for (int i = 1; i < 10; i++) {
    strengths.add(0.1 * i);
  }
  strengths.forEach((strength) {
    final double ds = 0.5 - strength;
    swatch[(strength * 1000).round()] = Color.fromRGBO(
      r + ((ds < 0 ? r : (255 - r)) * ds).round(),
      g + ((ds < 0 ? g : (255 - g)) * ds).round(),
      b + ((ds < 0 ? b : (255 - b)) * ds).round(),
      1,
    );
  });
  return MaterialColor(color.value, swatch);
}
