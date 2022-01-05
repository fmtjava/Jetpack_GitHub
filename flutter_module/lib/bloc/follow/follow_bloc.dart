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
      page = event.page;
      if (page == 1 && !event.isRefresh) {
        yield state.copyWith(pageStatus: PageStatus.LOADING);
      }
      try {
        List<FollowModel> followList = await FollowRepository.getFollowList(
            event.userName, event.type, page);
        if (page > 1) {
          followList.insertAll(0, state.followList);
        }
        yield state.copyWith(
            pageStatus: PageStatus.SUCCESS, followList: followList);
      } catch (e) {
        ToastUtil.showError(e.toString());
        if (page == 1 && !event.isRefresh) {
          yield state.copyWith(
              pageStatus: PageStatus.FAIL, errorMsg: e.toString());
        } else {
          page -= 1;
          yield state.copyWith(
              pageStatus: PageStatus.LOAD_MORE_FAIL, errorMsg: e.toString());
        }
      }
    }
  }
}
