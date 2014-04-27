package com.example.control_four_comics;

import android.graphics.Bitmap;

public class FourComicsData {
	private String numberData_;
	private Bitmap imageData_;
	private String titleData_;
	private Bitmap buttonData_;
	private boolean flagFilter_;

	public void setNumberData(String number) {
		numberData_ = number;
	}

	public String getNumberData() {
		return numberData_;
	}

	public void setImageData(Bitmap image) {
		imageData_ = image;
	}

	public Bitmap getImageData() {
		return imageData_;
	}

	public void setTitleData(String title) {
		titleData_ = title;
	}

	public String getTitleData() {
		return titleData_;
	}

	public void setButtonData(Bitmap button) {
		buttonData_ = button;
	}

	public Bitmap getButtonData() {
		return buttonData_;
	}

	public void setFlagFilter(boolean flagFilter) {
		flagFilter_ = flagFilter;
	}

	public boolean isFlagFilter() {
		return flagFilter_;
	}
}
