/*
 *  This file is part of AndroidIDE.
 *
 *  AndroidIDE is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  AndroidIDE is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with AndroidIDE.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.itsaky.androidide.views.editor;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.SizeUtils;
import com.itsaky.androidide.R;

import io.github.rosemoe.sora.widget.base.EditorPopupWindow;

/**
 * An {@link EditorPopupWindow} implementation which shows only a single textview.
 * @author Akash Yadav
 */
public abstract class SimpleTextWindow extends EditorPopupWindow {
    
    protected final TextView text;
    
    /**
     * Create a popup window for editor
     *
     * @param editor The editor
     * @see #FEATURE_SCROLL_AS_CONTENT
     * @see #FEATURE_SHOW_OUTSIDE_VIEW_ALLOWED
     * @see #FEATURE_HIDE_WHEN_FAST_SCROLL
     */
    public SimpleTextWindow (@NonNull IDEEditor editor) {
        super (editor, getFeatureFlags ());
    
        final var context = editor.getContext ();
        final var dp4 = SizeUtils.dp2px (4);
        final var dp8 = dp4 * 2;
        
        this.text = new TextView (context);
        this.text.setBackground (createBackground ());
        this.text.setTextColor (ContextCompat.getColor (context, R.color.primaryTextColor));
        this.text.setTextSize (TypedValue.COMPLEX_UNIT_SP, 14);
        this.text.setClickable (false);
        this.text.setFocusable (false);
        this.text.setPaddingRelative (dp8, dp4, dp8, dp4);
        this.text.setLayoutParams (new ViewGroup.LayoutParams (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    
        setContentView (this.text);
    }
    
    public void displayWindow() {
        this.text.measure (View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        setSize (this.text.getMeasuredWidth (), this.text.getMeasuredHeight ());
        
        final var line = getEditor ().getCursor ().getLeftLine ();
        final var column = getEditor ().getCursor ().getLeftColumn ();
        int x = (int) ((getEditor ().getOffset (line, column) - (getWidth () / 2)));
        int y = (int) (getEditor ().getRowHeight () * line) - getEditor ().getOffsetY () - getHeight () - 5;
        setLocationAbsolutely(x, y);
        show();
    }
    
    protected Drawable createBackground () {
        GradientDrawable background = new GradientDrawable ();
        background.setShape (GradientDrawable.RECTANGLE);
        background.setColor (0xff212121);
        background.setStroke (1, 0xffffffff);
        background.setCornerRadius (8);
        return background;
    }
    
    private static int getFeatureFlags () {
        return FEATURE_SCROLL_AS_CONTENT | FEATURE_SHOW_OUTSIDE_VIEW_ALLOWED;
    }
}
