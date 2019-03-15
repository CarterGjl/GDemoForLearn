package aop.demo.jetpack.android.atpeople;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

public class SpannableData implements DataBingdingSpan<String> {

    public SpannableData(String spanned) {
        this.spanned = spanned;
    }

    private String spanned;

    public void setSpanned(String spanned) {
        this.spanned = spanned;
    }

    public String getSpanned() {


        return spanned == null ? "" : spanned;
    }

    @Override
    public CharSequence spanedText() {

        SpannableString spannableString = new SpannableString(spanned);
        spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, spannableString.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @Override
    public String bindingData() {
        return spanned;
    }
}
