package io.vilo.viloapp.utils;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Patterns;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.request.animation.GlideAnimation;
//import com.bumptech.glide.request.target.BitmapImageViewTarget;

public class Utilities {

    public static boolean isValidPhone(CharSequence target) {
        return !TextUtils.isEmpty(target)
                && Patterns.PHONE.matcher(target).matches()
                && target.length() >= 10
                && target.length() <= 11;
    }

    public static int convertDpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int convertPxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static String toLowercase(String string) {
        return String.valueOf(string.charAt(0)).toUpperCase() + string.substring(1, string.length());
    }

//    public static void loadImage(final Context context, final String url, int placeholder,
//                                 final ImageView imageView) {
//        // This is a hack to remove the image resizing issue
//        // https://github.com/bumptech/glide/issues/613#issuecomment-138920515
//        Glide.with(context).load(url)
//                .asBitmap().centerCrop()
//                .placeholder(placeholder)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(new BitmapImageViewTarget(imageView) {
//                    @Override
//                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
//                        super.onResourceReady(bitmap, anim);
//                        Glide.with(context)
//                                .load(url)
//                                .centerCrop().into(imageView);
//                    }
//                });
//    }
}
