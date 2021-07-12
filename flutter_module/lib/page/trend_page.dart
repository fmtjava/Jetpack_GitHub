import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_module/bloc/trend_bloc.dart';
import 'package:flutter_module/bloc/trend_event.dart';
import 'package:flutter_module/bloc/trend_state.dart';
import 'package:flutter_module/color/color.dart';
import 'package:flutter_module/string/string.dart';
import 'package:flutter_module/util/toast_util.dart';
import 'package:flutter_module/widget/loading_dialog.dart';
import 'package:flutter_module/widget/trend_page_item.dart';

//Bloc中文文档：https://bloclibrary.dev/#/zh-cn/architecture
class TrendPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BlocProvider<TrendBloc>(
      create: (c) => TrendBloc(LoadingState())..add(GetTrendEvent()),
      child: TrendListPage(),
    );
  }
}

class TrendListPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          leading: IconButton(
              icon: Icon(Icons.arrow_back),
              onPressed: () => SystemNavigator.pop()),
          backgroundColor: DColor.themeColor,
          title: Text(DString.TREND),
          actions: <Widget>[
            //弹出菜单
            PopupMenuButton(
                onSelected: (action) {
                  context.read<TrendBloc>().add(GetTrendEvent(since: action));
                },
                offset: Offset(0, 28),
                itemBuilder: (context) => <PopupMenuItem<String>>[
                      PopupMenuItem(
                          child: Text(DString.DAILY), value: DString.DAILY),
                      PopupMenuItem(
                          child: Text(DString.WEEKLY), value: DString.WEEKLY),
                      PopupMenuItem(
                          child: Text(DString.MONTHLY), value: DString.MONTHLY)
                    ])
          ],
        ),
        body: BlocBuilder<TrendBloc, TrendState>(builder: (context, state) {
          if (state is LoadingState) {
            return LoadingDialog();
          }
          if (state is SuccessState) {
            return ListView.builder(
              itemBuilder: (context, index) {
                return TrendPageItem(state.trendList[index]);
              },
              itemCount: state.trendList.length,
            );
          } else {
            FailState failState = state;
            ToastUtil.showError(failState.message);
            return Center(
              child: OutlinedButton(
                onPressed: () {
                  context.read<TrendBloc>().add(
                      GetTrendEvent(since: context.read<TrendBloc>().since));
                },
                child: Text(DString.LOAD_AGAINT),
              ),
            );
          }
        }));
  }
}
