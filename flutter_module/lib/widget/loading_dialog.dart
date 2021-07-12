import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';

/// 自定义加载框
class LoadingDialog extends Dialog {
  @override
  Widget build(BuildContext context) {
    return Material(
      type: MaterialType.transparency,
      child: Center(
        child: Container(
          width: 100,
          height: 100,
          decoration:
              BoxDecoration(color: Colors.black38,borderRadius: BorderRadius.all(Radius.circular(5))),
          child: SpinKitCircle(color: Colors.white), //指示器
        ),
      ),
    );
  }
}
