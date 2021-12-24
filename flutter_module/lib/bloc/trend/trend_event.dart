import 'package:equatable/equatable.dart';

abstract class TrendEvent extends Equatable {
  @override
  List<Object> get props => [];
}

class GetTrendEvent extends TrendEvent {
  final String since;

  GetTrendEvent({this.since = "daily"});

  @override
  List<Object> get props => [since];
}
