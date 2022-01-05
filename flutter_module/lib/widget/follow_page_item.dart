import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:flutter_module/color/color.dart';
import 'package:flutter_module/congigure/page_configure.dart';
import 'package:flutter_module/model/follow_model.dart';
import 'package:flutter_module/util/navigation_util.dart';

class FollowPageItem extends StatelessWidget {
  final FollowModel followModel;

  const FollowPageItem(this.followModel, {Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: EdgeInsets.fromLTRB(8, 8, 8, 4),
      child: Material(
        child: InkWell(
          child: Padding(
            padding: EdgeInsets.all(15),
            child: Row(
              children: [
                ClipOval(
                  child: CachedNetworkImage(
                      imageUrl: followModel.avatarUrl,
                      height: 40,
                      width: 40,
                      fit: BoxFit.cover),
                ),
                Padding(
                  padding: EdgeInsets.only(left: 10),
                  child: Text(
                    followModel.login,
                    style: TextStyle(
                        color: DColor.themeColor,
                        fontSize: 18,
                        fontWeight: FontWeight.bold),
                  ),
                )
              ],
            ),
          ),
          onTap: () => NavigationUtil.push(USER_INFO_PAGE, arguments: {
            "user_name": followModel.login,
            "user_avatar": followModel.avatarUrl,
          }),
        ),
      ),
    );
  }
}
