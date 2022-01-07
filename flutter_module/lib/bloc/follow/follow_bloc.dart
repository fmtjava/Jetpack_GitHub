import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_module/bloc/follow/follow_event.dart';
import 'package:flutter_module/bloc/follow/follow_state.dart';
import 'package:flutter_module/common/common_page_status.dart';
import 'package:flutter_module/model/follow_model.dart';
import 'package:flutter_module/repository/follow_repository.dart';
import 'package:flutter_module/util/toast_util.dart';

class FollowBloc extends Bloc<FollowersEvent, FollowersState> {
  int page;

  FollowBloc() : super(FollowersState());

  @override
  Stream<FollowersState> mapEventToState(FollowersEvent event) async* {
    if (event is GetFollowersEvent) {
      //保存加载更多时页码，保证在加载更多失败时页码计算正确
      if (!event.isRefresh) {
        page = event.page;
      }
      if (event.page == 1 && !event.isRefresh) {
        yield state.copyWith(pageStatus: PageStatus.LOADING);
      }
      try {
        List<FollowModel> followList = await FollowRepository.getFollowList(
            event.userName, event.type, event.authorization, event.page);
        //判断是否加载完数据
        bool hasMore = followList.isNotEmpty;
        //处理加载更多时的数据
        if (event.page > 1) {
          followList.insertAll(0, state.followList);
        }
        yield state.copyWith(
            pageStatus: PageStatus.SUCCESS,
            followList: followList,
            hasMore: hasMore);
        //下拉刷新成功后，在保存当前页码
        if (event.isRefresh) {
          page = 1;
        }
      } catch (e) {
        ToastUtil.showError(e.toString());
        if (event.page == 1 && !event.isRefresh) {
          yield state.copyWith(
              pageStatus: PageStatus.FAIL, errorMsg: e.toString());
        } else {
          //加载更多失败时，page-1,保证再次加载更多时数据正确
          if (event.page > 1) {
            page -= 1;
          }
          yield state.copyWith(
              pageStatus: event.isRefresh
                  ? PageStatus.REFRESH_DATA_FAIL
                  : PageStatus.LOAD_MORE_FAIL,
              error: e);
        }
      }
    }
  }
}
