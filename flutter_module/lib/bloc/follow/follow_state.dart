import 'package:equatable/equatable.dart';
import 'package:flutter_module/common/common_page_status.dart';
import 'package:flutter_module/model/follow_model.dart';

class FollowersState extends Equatable {
  final PageStatus pageStatus;
  final List<FollowModel> followList;
  final Object error;
  final String errorMsg;
  final bool hasMore;

  const FollowersState(
      {this.pageStatus = PageStatus.LOADING,
      this.followList = const [],
      this.error,
      this.errorMsg = '',
      this.hasMore = true});

  FollowersState copyWith(
      {PageStatus pageStatus,
      List<FollowModel> followList,
      Object error,
      String errorMsg,
      bool hasMore}) {
    return FollowersState(
        pageStatus: pageStatus ?? this.pageStatus,
        followList: followList ?? this.followList,
        error: error ?? this.error,
        errorMsg: errorMsg ?? this.errorMsg,
        hasMore: hasMore ?? this.hasMore);
  }

  @override
  List<Object> get props => [pageStatus, followList, error, errorMsg, hasMore];
}
