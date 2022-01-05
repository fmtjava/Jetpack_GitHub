import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_module/bloc/trend/trend_event.dart';
import 'package:flutter_module/bloc/trend/trend_state.dart';
import 'package:flutter_module/common/common_page_status.dart';
import 'package:flutter_module/model/trend_model.dart';
import 'package:flutter_module/repository/trend_repository.dart';

class TrendBloc extends Bloc<TrendEvent, TrendState> {
  String since = "daily";

  TrendBloc() : super(TrendState());

  @override
  Stream<TrendState> mapEventToState(TrendEvent event) async* {
    if (event is GetTrendEvent) {
      since = event.since;
      yield state.copyWith(pageStatus: PageStatus.LOADING);
      try {
        List<TrendModel> trendList =
            await TrendRepository.getTrendList(since: since);
        yield state.copyWith(
            pageStatus: PageStatus.SUCCESS, trendList: trendList);
      } catch (e) {
        yield state.copyWith(
            pageStatus: PageStatus.FAIL, errorMsg: e.toString());
      }
    }
  }
}
