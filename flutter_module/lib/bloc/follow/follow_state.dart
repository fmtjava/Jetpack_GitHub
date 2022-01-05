import 'package:equatable/equatable.dart';
import 'package:flutter_module/common/common_page_status.dart';
import 'package:flutter_module/model/follow_model.dart';

class FollowersState extends Equatable {
  final PageStatus pageStatus;
  final List<FollowModel> followList;
  final String errorMsg;

  const FollowersState(
      {this.pageStatus = PageStatus.LOADING,
      this.followList = const [],
      this.errorMsg = ''});

  FollowersState copyWith(
      {PageStatus pageStatus, List<FollowModel> followList, String errorMsg}) {
    return FollowersState(
        pageStatus: pageStatus ?? this.pageStatus,
        followList: followList ?? this.followList,
        errorMsg: errorMsg ?? this.errorMsg);
  }

  @override
  List<Object> get props => [pageStatus, followList, errorMsg];
}
