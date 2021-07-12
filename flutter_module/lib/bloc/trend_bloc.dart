import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_module/bloc/trend_event.dart';
import 'package:flutter_module/bloc/trend_state.dart';
import 'package:flutter_module/model/trend_model.dart';
import 'package:flutter_module/repository/trend_repository.dart';

class TrendBloc extends Bloc<TrendEvent, TrendState> {
  String since = "daily";

  TrendBloc(TrendState initialState) : super(initialState);

  @override
  Stream<TrendState> mapEventToState(TrendEvent event) async* {
    if (event is GetTrendEvent) {
      since = event.since;
      yield LoadingState();
      try {
        List<TrendModel> trendList =
            await TrendRepository.getTrendList(since: since);
        yield SuccessState(trendList);
      } catch (e) {
        yield FailState(e.toString());
      }
    }
  }
}
