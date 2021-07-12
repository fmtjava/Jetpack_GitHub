import 'package:equatable/equatable.dart';
import 'package:flutter_module/model/trend_model.dart';

abstract class TrendState extends Equatable {

  const TrendState();

  @override
  List<Object> get props => [];
}

class LoadingState extends TrendState {}

class SuccessState extends TrendState {
  final List<TrendModel> trendList;

  SuccessState(this.trendList);

  @override
  List<Object> get props => [trendList];
}

class FailState extends TrendState {
  final String message;

  FailState(this.message);

  @override
  List<Object> get props => [message];
}
