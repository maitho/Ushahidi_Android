/**
 ** Copyright (c) 2010 Ushahidi Inc
 ** All rights reserved
 ** Contact: team@ushahidi.com
 ** Website: http://www.ushahidi.com
 **
 ** GNU Lesser General Public License Usage
 ** This file may be used under the terms of the GNU Lesser
 ** General Public License version 3 as published by the Free Software
 ** Foundation and appearing in the file LICENSE.LGPL included in the
 ** packaging of this file. Please review the following information to
 ** ensure the GNU Lesser General Public License version 3 requirements
 ** will be met: http://www.gnu.org/licenses/lgpl.html.
 **
 **
 ** If you have questions regarding the use of this file, please contact
 ** Ushahidi developers at team@ushahidi.com.
 **
 **/
package com.ushahidi.android.app.adapters;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ushahidi.android.app.ImageManager;
import com.ushahidi.android.app.R;
import com.ushahidi.android.app.entities.Photo;
import com.ushahidi.android.app.models.ListReportPhotoModel;

/**
 * This is the adapter for photos to be uploaded to the server.
 * 
 * @author eyedol
 * 
 */
public class UploadPhotoAdapter extends BaseListAdapter<Photo> {

	class Widgets extends com.ushahidi.android.app.views.View {

		public Widgets(View view) {
			super(view);
			this.photo = (ImageView) view.findViewById(R.id.upload_photo);

		}

		ImageView photo;
	}

	private ListReportPhotoModel mListPhotoModel;

	private List<Photo> items;

	private boolean pending = false;

	/**
	 * @param context
	 */
	public UploadPhotoAdapter(Context context) {
		super(context);
	}

	/**
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 *      android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = inflater.inflate(R.layout.upload_photo, parent, false);
		Widgets widgets = (Widgets) row.getTag();

		if (widgets == null) {
			widgets = new Widgets(row);
			row.setTag(widgets);
		}

		if (pending) {
			widgets.photo.setImageDrawable(getPendingPhoto(getItem(position)
					.getPhoto()));
		} else {
			widgets.photo.setImageDrawable(getPhoto(getItem(position)
					.getPhoto()));
		}
		// widgets.photo.setBackgroundResource(imageBackgroundColor());
		return row;

	}

	/**
	 * @see com.ushahidi.android.app.adapters.BaseListAdapter#refresh()
	 */
	@Override
	public void refresh() {
		pending = false;
		mListPhotoModel = new ListReportPhotoModel();
		items = mListPhotoModel.getPendingPhotos(context);
		this.setItems(items);

	}

	public void refresh(int reportId) {
		pending = true;
		mListPhotoModel = new ListReportPhotoModel();
		items = mListPhotoModel.getPendingPhotosByReportId(reportId);
		this.setItems(items);

	}

	public Drawable getPhoto(String fileName) {

		return ImageManager.getPendingDrawables(context, fileName);

	}

	public Drawable getPendingPhoto(String fileName) {

		return ImageManager.getDrawables(context, fileName);

	}

}
