import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:flutter_boost/flutter_boost.dart';
import 'package:flutter_module/color/color.dart';
import 'package:flutter_module/model/trend_model.dart';

class TrendPageItem extends StatelessWidget {
  final TrendModel trendModel;

  const TrendPageItem(this.trendModel, {Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: EdgeInsets.fromLTRB(8, 8, 8, 4),
      child: InkWell(
        onTap: () {
          BoostNavigator.instance.push("reposDetail", arguments: {
            "web_url": trendModel.url,
            "owner": trendModel.name,
            "repo": trendModel.reposName
          });
        },
        child: Padding(
          padding: EdgeInsets.all(8),
          child: Row(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              ClipOval(
                child: CachedNetworkImage(
                    imageUrl: trendModel.contributorsUrl,
                    height: 40,
                    width: 40,
                    fit: BoxFit.cover),
              ),
              Expanded(
                  child: Padding(
                padding: EdgeInsets.only(left: 8),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: <Widget>[
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: <Widget>[
                        Expanded(
                            child: Text(
                          trendModel.reposName,
                          overflow: TextOverflow.ellipsis,
                          style: TextStyle(
                              color: DColor.themeColor,
                              fontSize: 16,
                              fontWeight: FontWeight.bold),
                        )),
                        Container(
                          margin: EdgeInsets.only(left: 10),
                          child: Text(
                            trendModel.language,
                            style: TextStyle(
                                color: DColor.desTextColor, fontSize: 12),
                          ),
                        )
                      ],
                    ),
                    Text(
                      trendModel.description,
                      style:
                          TextStyle(color: DColor.desTextColor, fontSize: 14),
                    ),
                    Padding(
                      padding: EdgeInsets.only(top: 4),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: <Widget>[
                          Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: <Widget>[
                              Icon(
                                Icons.star_border,
                                color: DColor.startTextColor,
                                size: 20,
                              ),
                              Text(
                                trendModel.starCount,
                                style: TextStyle(color: DColor.startTextColor),
                              )
                            ],
                          ),
                          Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: <Widget>[
                              Icon(
                                Icons.device_hub,
                                color: DColor.startTextColor,
                                size: 20,
                              ),
                              Text(
                                trendModel.forkCount,
                                style: TextStyle(color: DColor.startTextColor),
                              )
                            ],
                          ),
                          Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: <Widget>[
                              Icon(
                                Icons.remove_red_eye,
                                color: DColor.startTextColor,
                                size: 20,
                              ),
                              Text(
                                trendModel.meta,
                                style: TextStyle(color: DColor.startTextColor),
                              )
                            ],
                          )
                        ],
                      ),
                    )
                  ],
                ),
              ))
            ],
          ),
        ),
      ),
    );
  }
}
