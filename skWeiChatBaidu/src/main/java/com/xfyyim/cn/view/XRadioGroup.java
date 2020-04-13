package com.xfyyim.cn.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.IdRes;

public class XRadioGroup extends LinearLayout {
    private int mCheckedId = -1;
    private CompoundButton.OnCheckedChangeListener mChildOnCheckedChangeListener;
    private boolean mProtectFromCheckedChange = false;
    private XRadioGroup.OnCheckedChangeListener mOnCheckedChangeListener;
    private XRadioGroup.PassThroughHierarchyChangeListener mPassThroughListener;

    public XRadioGroup(Context context) {
        super(context);
        this.init();
    }

    public XRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public XRadioGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    @TargetApi(21)
    public XRadioGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init();
    }

    private void init() {
        this.mChildOnCheckedChangeListener = new XRadioGroup.CheckedStateTracker();
        this.mPassThroughListener = new XRadioGroup.PassThroughHierarchyChangeListener();
        super.setOnHierarchyChangeListener(this.mPassThroughListener);
    }

    public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
        this.mPassThroughListener.mOnHierarchyChangeListener = listener;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (this.mCheckedId != -1) {
            this.mProtectFromCheckedChange = true;
            this.setCheckedStateForView(this.mCheckedId, true);
            this.mProtectFromCheckedChange = false;
            this.setCheckedId(this.mCheckedId);
        }

    }

    private void setViewState(View child) {
        if (child instanceof RadioButton) {
            RadioButton button = (RadioButton)child;
            if (button.isChecked()) {
                this.mProtectFromCheckedChange = true;
                if (this.mCheckedId != -1) {
                    this.setCheckedStateForView(this.mCheckedId, false);
                }

                this.mProtectFromCheckedChange = false;
                this.setCheckedId(button.getId());
            }
        } else if (child instanceof ViewGroup) {
            ViewGroup view = (ViewGroup)child;

            for(int i = 0; i < view.getChildCount(); ++i) {
                this.setViewState(view.getChildAt(i));
            }
        }

    }

    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        this.setViewState(child);
        super.addView(child, index, params);
    }

    public void check(@IdRes int id) {
        if (id == -1 || id != this.mCheckedId) {
            if (this.mCheckedId != -1) {
                this.setCheckedStateForView(this.mCheckedId, false);
            }

            if (id != -1) {
                this.setCheckedStateForView(id, true);
            }

            this.setCheckedId(id);
        }
    }

    private void setCheckedId(@IdRes int id) {
        this.mCheckedId = id;
        if (this.mOnCheckedChangeListener != null) {
            this.mOnCheckedChangeListener.onCheckedChanged(this, this.mCheckedId);
        }

    }

    private void setCheckedStateForView(int viewId, boolean checked) {
        View checkedView = this.findViewById(viewId);
        if (checkedView != null && checkedView instanceof RadioButton) {
            ((RadioButton)checkedView).setChecked(checked);
        }

    }

    @IdRes
    public int getCheckedRadioButtonId() {
        return this.mCheckedId;
    }

    public void clearCheck() {
        this.check(-1);
    }

    public void setOnCheckedChangeListener(XRadioGroup.OnCheckedChangeListener listener) {
        this.mOnCheckedChangeListener = listener;
    }

    public XRadioGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new XRadioGroup.LayoutParams(this.getContext(), attrs);
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof XRadioGroup.LayoutParams;
    }

    protected LinearLayout.LayoutParams generateDefaultLayoutParams() {
        return new XRadioGroup.LayoutParams(-2, -2);
    }

    public CharSequence getAccessibilityClassName() {
        return XRadioGroup.class.getName();
    }

    private void setListener(View child) {
        if (child instanceof RadioButton) {
            int id = child.getId();
            if (id == -1) {
                id = child.hashCode();
                child.setId(id);
            }

            ((RadioButton)child).setOnCheckedChangeListener(this.mChildOnCheckedChangeListener);
        } else if (child instanceof ViewGroup) {
            ViewGroup view = (ViewGroup)child;

            for(int i = 0; i < view.getChildCount(); ++i) {
                this.setListener(view.getChildAt(i));
            }
        }

    }

    private void removeListener(View child) {
        if (child instanceof RadioButton) {
            ((RadioButton)child).setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)null);
        } else if (child instanceof ViewGroup) {
            ViewGroup view = (ViewGroup)child;

            for(int i = 0; i < view.getChildCount(); ++i) {
                this.removeListener(view.getChildAt(i));
            }
        }

    }

    private class PassThroughHierarchyChangeListener implements OnHierarchyChangeListener {
        private OnHierarchyChangeListener mOnHierarchyChangeListener;

        private PassThroughHierarchyChangeListener() {
        }

        public void onChildViewAdded(View parent, View child) {
            XRadioGroup.this.setListener(child);
            if (this.mOnHierarchyChangeListener != null) {
                this.mOnHierarchyChangeListener.onChildViewAdded(parent, child);
            }

        }

        public void onChildViewRemoved(View parent, View child) {
            XRadioGroup.this.removeListener(child);
            if (this.mOnHierarchyChangeListener != null) {
                this.mOnHierarchyChangeListener.onChildViewRemoved(parent, child);
            }

        }
    }

    private class CheckedStateTracker implements CompoundButton.OnCheckedChangeListener {
        private CheckedStateTracker() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (!XRadioGroup.this.mProtectFromCheckedChange) {
                XRadioGroup.this.mProtectFromCheckedChange = true;
                if (XRadioGroup.this.mCheckedId != -1) {
                    XRadioGroup.this.setCheckedStateForView(XRadioGroup.this.mCheckedId, false);
                }

                XRadioGroup.this.mProtectFromCheckedChange = false;
                int id = buttonView.getId();
                XRadioGroup.this.setCheckedId(id);
            }
        }
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(XRadioGroup var1, @IdRes int var2);
    }

    public static class LayoutParams extends LinearLayout.LayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }

        public LayoutParams(int w, int h, float initWeight) {
            super(w, h, initWeight);
        }

        public LayoutParams(ViewGroup.LayoutParams p) {
            super(p);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        protected void setBaseAttributes(TypedArray a, int widthAttr, int heightAttr) {
            if (a.hasValue(widthAttr)) {
                this.width = a.getLayoutDimension(widthAttr, "layout_width");
            } else {
                this.width = -2;
            }

            if (a.hasValue(heightAttr)) {
                this.height = a.getLayoutDimension(heightAttr, "layout_height");
            } else {
                this.height = -2;
            }

        }
    }
}
