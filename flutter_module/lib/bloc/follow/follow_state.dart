import 'package:equatable/equatable.dart';
import 'package:flutter_module/model/follow_model.dart';

abstract class FollowersState extends Equatable {
  const FollowersState();

  @override
  List<Object> get props => [];
}

class LoadingState extends FollowersState {}

class SuccessState extends FollowersState {
  final List<FollowModel> followList;

  SuccessState(this.followList);

  @override
  List<Object> get props => [followList];
}

class LoadDataFailState extends FollowersState{
  final Object error;

  LoadDataFailState(this.error);

  @override
  List<Object> get props => [error];
}

class LoadFailState extends FollowersState {
  final String message;

  LoadFailState(this.message);

  @override
  List<Object> get props => [message];
}
