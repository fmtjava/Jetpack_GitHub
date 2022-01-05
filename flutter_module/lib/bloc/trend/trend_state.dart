import 'package:equatable/equatable.dart';
import 'package:flutter_module/common/common_page_status.dart';
import 'package:flutter_module/model/trend_model.dart';

class TrendState extends Equatable {
  final PageStatus pageStatus;
  final List<TrendModel> trendList;
  final String errorMsg;

  const TrendState(
      {this.pageStatus = PageStatus.LOADING,
      this.trendList = const [],
      this.errorMsg = ''});

  TrendState copyWith(
      {PageStatus pageStatus, List<TrendModel> trendList, String errorMsg}) {
    return TrendState(
        pageStatus: pageStatus ?? this.pageStatus,
        trendList: trendList ?? this.trendList,
        errorMsg: errorMsg ?? this.errorMsg);
  }

  @override
  List<Object> get props => [pageStatus, trendList, errorMsg];
}
