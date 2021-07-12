
class TrendModel {

  String fullName;
  String url;
  String description;
  String language;
  String meta;
  List<String> contributors;
  String contributorsUrl;
  String starCount;
  String forkCount;
  String name;
  String reposName;

	TrendModel.fromJsonMap(Map<String, dynamic> map):
		fullName = map["fullName"],
		url = map["url"],
		description = map["description"],
		language = map["language"],
		meta = map["meta"],
		contributors = List<String>.from(map["contributors"]),
		contributorsUrl = map["contributorsUrl"],
		starCount = map["starCount"],
		forkCount = map["forkCount"],
		name = map["name"],
		reposName = map["reposName"];

	Map<String, dynamic> toJson() {
		final Map<String, dynamic> data = new Map<String, dynamic>();
		data['fullName'] = fullName;
		data['url'] = url;
		data['description'] = description;
		data['language'] = language;
		data['meta'] = meta;
		data['contributors'] = contributors;
		data['contributorsUrl'] = contributorsUrl;
		data['starCount'] = starCount;
		data['forkCount'] = forkCount;
		data['name'] = name;
		data['reposName'] = reposName;
		return data;
	}
}
