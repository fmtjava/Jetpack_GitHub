import 'package:equatable/equatable.dart';

abstract class FollowersEvent extends Equatable {
  @override
  List<Object> get props => [];
}

class GetFollowersEvent extends FollowersEvent {
  final bool isRefresh;
  final String userName;
  final String type;
  final int page;

  GetFollowersEvent(this.userName, this.type, this.page,
      {this.isRefresh = false});

  @override
  List<Object> get props => [page];
}
