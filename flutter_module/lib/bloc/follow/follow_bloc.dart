import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_module/bloc/follow/follow_event.dart';
import 'package:flutter_module/bloc/follow/follow_state.dart';
import 'package:flutter_module/model/follow_model.dart';
import 'package:flutter_module/repository/follow_repository.dart';
import 'package:flutter_module/util/toast_util.dart';

class FollowBloc extends Bloc<FollowersEvent, FollowersState> {
  int page;
  List<FollowModel> mFollowList = [];

  FollowBloc(FollowersState initialState) : super(initialState);

  @override
  Stream<FollowersState> mapEventToState(FollowersEvent event) async* {
    if (event is GetFollowersEvent) {
      page = event.page;
      if (page == 1 && !event.isRefresh) {
        yield LoadingState();
      }
      try {
        List<FollowModel> followList =
        await FollowRepository.getFollowList(event.userName, event.type, page);
        if (followList == null || followList.length == 0) {
          yield SuccessState(followList);
        } else {
          if (page == 1) {
            mFollowList.clear();
          }
          mFollowList.addAll(followList);
          yield SuccessState(followList);
        }
      } catch (e) {
        ToastUtil.showError(e.toString());
        if (page == 1 && !event.isRefresh) {
          yield LoadFailState(e);
        } else {
          yield LoadDataFailState(e);
        }
      }
    }
  }
}
