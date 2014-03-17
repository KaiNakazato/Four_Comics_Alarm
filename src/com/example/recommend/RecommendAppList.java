package com.example.recommend;

import java.util.ArrayList;


@SuppressWarnings("serial")
public class RecommendAppList extends ArrayList<RecommendAppData> {
	
	private  static RecommendAppList instance = new RecommendAppList();
	
	public static RecommendAppList getInstance() {
		return instance;
	}

}
