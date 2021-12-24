import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_module/bloc/follow/follow_bloc.dart';
import 'package:flutter_module/bloc/follow/follow_event.dart';
import 'package:flutter_module/bloc/follow/follow_state.dart';
import 'package:flutter_module/color/color.dart';
import 'package:flutter_module/string/string.dart';
import 'package:flutter_module/util/toast_util.dart';
import 'package:flutter_module/widget/follow_page_item.dart';
import 'package:flutter_module/widget/loading_dialog.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';

class FollowPage extends StatelessWidget {
  final String userName;
  final String type;

  const FollowPage(this.userName, this.type, {Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return BlocProvider<FollowBloc>(
      create: (c) =>
          FollowBloc(LoadingState())..add(GetFollowersEvent(userName, type, 1)),
      child: FollowListPage(userName, type),
    );
  }
}

class FollowListPage extends StatelessWidget {
  final RefreshController _refreshController =
      RefreshController(initialRefresh: false);

  final String userName;
  final String type;

  FollowListPage(this.userName, this.type, {Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          leading: IconButton(
              icon: Icon(Icons.arrow_back),
              onPressed: () => SystemNavigator.pop()),
          backgroundColor: DColor.themeColor,
          title: Text(type == DString.FOLLOWERS_TYPE
              ? DString.FOLLOWER
              : DString.FOLLOWING),
        ),
        body:
            BlocBuilder<FollowBloc, FollowersState>(builder: (context, state) {
          if (state is LoadingState) {
            return LoadingDialog();
          }
          _loadComplete();
          if (state is SuccessState) {
            if (state.followList.isEmpty) {
              _refreshController.loadNoData();
            } else {
              _refreshController.footerMode.value = LoadStatus.canLoading;
            }
            return _contentWidget(context);
          } else if (state is LoadDataFailState) {
            if (context.read<FollowBloc>().page > 1) {
              _refreshController.loadFailed();
              context.read<FollowBloc>().page =
                  context.read<FollowBloc>().page - 1;
            }
            return _contentWidget(context);
          } else {
            return _errorWidget(context);
          }
        }));
  }

  Widget _contentWidget(BuildContext context) {
    return SmartRefresher(
      enablePullDown: true,
      enablePullUp: true,
      controller: _refreshController,
      onRefresh: () => context
          .read<FollowBloc>()
          .add(GetFollowersEvent(userName, type, 1, isRefresh: true)),
      onLoading: () => context.read<FollowBloc>().add(GetFollowersEvent(
          userName, type, context.read<FollowBloc>().page + 1)),
      child: ListView.builder(
        itemBuilder: (context, index) =>
            FollowPageItem(context.read<FollowBloc>().mFollowList[index]),
        itemCount: context.read<FollowBloc>().mFollowList.length,
      ),
    );
  }

  Widget _errorWidget(BuildContext context) {
    return Center(
      child: OutlinedButton(
        onPressed: () => context
            .read<FollowBloc>()
            .add(GetFollowersEvent(userName, type, 1)),
        child: Text(DString.LOAD_AGAINT),
      ),
    );
  }

  _loadComplete() {
    _refreshController.refreshCompleted();
    _refreshController.loadComplete();
  }
}
