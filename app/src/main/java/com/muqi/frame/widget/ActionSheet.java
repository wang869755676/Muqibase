package com.muqi.frame.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.hyphenate.chatuidemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * 仿ios底部弹出框
 * 
 * @author chenqiang
 * 
 */
public class ActionSheet extends Fragment implements OnClickListener {

	private static final String ARG_CANCEL_SHEET_TITLE = "cancel_button_title";
	private static final String ARG_SHEET_TITLES = "other_button_titles";
	private static final int TRANSLATE_DURATION = 200;
	public static final int ALPHA_DURATION = 300;
	/* 是否已经显示 */
	private boolean isMissed = true;
	private ActionSheetListener mListener;
	private View mView;
	private LinearLayout mPanel;
	private ViewGroup mGroup;
	private View mBg;

	private FragmentActivity activity;
	private Resources resources;
	private int bottom_dialog_height;

	public void show(FragmentManager manager, String tag) {
		if (!isMissed) {
			return;
		}
		isMissed = false;
		FragmentTransaction ft = manager.beginTransaction();
		ft.add(this, tag);
		ft.addToBackStack(null);
		ft.commit();
	}

	public void dismiss() {
		if (isMissed) {
			return;
		}
		isMissed = true;
		FragmentManager manager = getFragmentManager();
		if (manager != null) {
			manager.popBackStack();
			FragmentTransaction ft = manager.beginTransaction();
			ft.remove(this);
			ft.commit();
		}
	}

	public boolean isMissed() {
		return isMissed;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		activity = getActivity();
		resources = getResources();
		bottom_dialog_height = (int) (resources
				.getDimension(R.dimen.bottom_dialog_height) + 0.5);

		// 如果键盘是弹出来的，关闭软键盘
		InputMethodManager imm = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			View focusView = activity.getCurrentFocus();
			if (focusView != null) {
				imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
			}
		}

		mView = createView();
		createItems();
		mGroup = (ViewGroup) activity.getWindow().getDecorView();
		mGroup.addView(mView);
		mBg.startAnimation(createAlphaInAnimation());
		mPanel.startAnimation(createTranslationInAnimation());
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	private Animation createTranslationInAnimation() {
		int type = TranslateAnimation.RELATIVE_TO_SELF;
		TranslateAnimation an = new TranslateAnimation(type, 0, type, 0, type,
				1, type, 0);
		an.setDuration(TRANSLATE_DURATION);
		return an;
	}

	private Animation createAlphaInAnimation() {
		AlphaAnimation an = new AlphaAnimation(0, 1);
		an.setDuration(ALPHA_DURATION);
		return an;
	}

	private Animation createTranslationOutAnimation() {
		int type = TranslateAnimation.RELATIVE_TO_SELF;
		TranslateAnimation an = new TranslateAnimation(type, 0, type, 0, type,
				0, type, 1);
		an.setDuration(TRANSLATE_DURATION);
		an.setFillAfter(true);
		return an;
	}

	private Animation createAlphaOutAnimation() {
		AlphaAnimation an = new AlphaAnimation(1, 0);
		an.setDuration(ALPHA_DURATION);
		an.setFillAfter(true);
		return an;
	}

	private View createView() {
		FrameLayout parent = new FrameLayout(activity);
		parent.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		mBg = new View(activity);
		mBg.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		mBg.setBackgroundColor(Color.argb(136, 0, 0, 0));
		mBg.setTag(R.string.cancel);
		mBg.setOnClickListener(this);

		mPanel = new LinearLayout(activity);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.BOTTOM;
		mPanel.setLayoutParams(params);
		mPanel.setOrientation(LinearLayout.VERTICAL);

		parent.addView(mBg);
		parent.addView(mPanel);
		return parent;
	}

	private void createItems() {
		int textColor = resources.getColor(R.color.teal_500);
		ArrayList<Integer> titles = getSheetTitles();
		if (titles != null) {
			int length = titles.size();
			for (int i = 0; i < length; i++) {
				TextView tv = new TextView(activity);
				tv.setText(titles.get(i));
				tv.setTextColor(textColor);
				tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
				tv.setGravity(Gravity.CENTER);
				tv.setBackgroundResource(getSheetBgId(titles.size(), i));
				tv.setOnClickListener(this);
				tv.setTag(titles.get(i));
				mPanel.addView(tv, createButtonLayoutParams());
			}
		}
		TextView tv_cancel = new TextView(activity);
		// bt.getPaint().setFakeBoldText(true);
		tv_cancel.setText(getCancelSheetTitle());
		tv_cancel.setTextColor(textColor);
		tv_cancel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
		tv_cancel.setGravity(Gravity.CENTER);
		tv_cancel
				.setBackgroundResource(R.drawable.drawable_bottom_dialog_whole);
		tv_cancel.setOnClickListener(this);
		tv_cancel.setTag(getCancelSheetTitle());
		LinearLayout.LayoutParams params = createButtonLayoutParams();
		params.topMargin = (int) (resources
				.getDimension(R.dimen.bottom_dialog_padding) + 0.5);
		mPanel.addView(tv_cancel, params);

		int bottom_dialog_margin = (int) (resources
				.getDimension(R.dimen.bottom_dialog_margin) + 0.5);
		mPanel.setPadding(bottom_dialog_margin, 0, bottom_dialog_margin,
				bottom_dialog_margin);
	}

	private LinearLayout.LayoutParams createButtonLayoutParams() {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, bottom_dialog_height);
		return params;
	}

	private int getSheetBgId(int size, int i) {
		if (size == 1) {
			return R.drawable.drawable_bottom_dialog_whole;
		} else {
			if (i == 0) {
				return R.drawable.drawable_bottom_dialog_top;
			} else if (i == (size - 1)) {
				return R.drawable.drawable_bottom_dialog_bottom;
			} else {
				return R.drawable.drawable_bottom_dialog_middle;
			}
		}
	}

	@Override
	public void onDestroyView() {
		mPanel.startAnimation(createTranslationOutAnimation());
		mBg.startAnimation(createAlphaOutAnimation());
		mView.postDelayed(new Runnable() {
			@Override
			public void run() {
				mGroup.removeView(mView);
			}
		}, ALPHA_DURATION);
		super.onDestroyView();
	}

	private int getCancelSheetTitle() {
		return getArguments().getInt(ARG_CANCEL_SHEET_TITLE);
	}

	private ArrayList<Integer> getSheetTitles() {
		return getArguments().getIntegerArrayList(ARG_SHEET_TITLES);
	}

	public void setActionSheetListener(ActionSheetListener listener) {
		mListener = listener;
	}

	@Override
	public void onClick(View v) {

		dismiss();

		if (mListener != null) {
			mListener.onSheetClick(this, (Integer) v.getTag());
		}

	}

	public static Builder createBuilder(Context context,
			FragmentManager fragmentManager) {
		return new Builder(context, fragmentManager);
	}

	public static class Builder {
		private Context mContext;
		private FragmentManager mFragmentManager;
		private int mCancelSheetTitleId = R.string.cancel;
		private ArrayList<Integer> mSheetTitleIds;
		private String mTag = "actionSheet";
		private ActionSheetListener mListener;

		private static Map<String, ActionSheet> sheets = new HashMap<String, ActionSheet>();

		public Builder(Context context, FragmentManager fragmentManager) {
			mContext = context;
			mFragmentManager = fragmentManager;
		}

		public Builder setCancelSheetTitle(int titleId) {
			mCancelSheetTitleId = titleId;
			return this;
		}

		public Builder setSheetTitles(ArrayList<Integer> titleIds) {
			mSheetTitleIds = titleIds;
			return this;
		}

		public Builder setTag(String tag) {
			mTag = tag;
			return this;
		}

		public Builder setOnSheetClickListener(ActionSheetListener listener) {
			this.mListener = listener;
			return this;
		}

		public Bundle prepareArguments() {
			Bundle bundle = new Bundle();
			bundle.putInt(ARG_CANCEL_SHEET_TITLE, mCancelSheetTitleId);
			bundle.putIntegerArrayList(ARG_SHEET_TITLES, mSheetTitleIds);
			return bundle;
		}

		public ActionSheet show() {
			ActionSheet as = sheets.get(mContext.hashCode() + "");
			if (as == null || as.isMissed()) {

			} else {
				as.dismiss();
				return null;
			}
			ActionSheet actionSheet = (ActionSheet) Fragment.instantiate(
					mContext, ActionSheet.class.getName(), prepareArguments());
			actionSheet.setActionSheetListener(mListener);
			actionSheet.show(mFragmentManager, mTag);
			sheets.put(mContext.hashCode() + "", actionSheet);
			return actionSheet;
		}

	}

	public static interface ActionSheetListener {
		void onSheetClick(ActionSheet actionSheet, int stringId);
	}

}