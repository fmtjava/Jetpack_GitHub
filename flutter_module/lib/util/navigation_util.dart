import 'package:flutter_boost/flutter_boost.dart';

class NavigationUtil {
  static push(String name,
      {Map<String, dynamic> arguments,
      bool withContainer = false,
      bool opaque = true}) {
    BoostNavigator.instance.push(name,
        arguments: arguments, withContainer: withContainer, opaque: opaque);
  }
}
