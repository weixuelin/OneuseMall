package com.wt.weiutils.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.wt.weiutils.R;
import com.wt.weiutils.glide.BlurTransformation;
import com.wt.weiutils.glide.CropCircleTransformation;
import com.wt.weiutils.glide.RoundedCornersTransformation;

import java.security.MessageDigest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.bumptech.glide.load.resource.bitmap.VideoDecoder.FRAME_OPTION;


/**
 * 图片加载相关操作
 */

public class ImageUtil {

    private static ImageUtil imageUtil;
    private static final int TIME = 100;

    public static synchronized ImageUtil getInstance() {
        if (imageUtil == null) {
            imageUtil = new ImageUtil();
        }
        return imageUtil;

    }


    public interface OnGetBitmap {
        void getBitmap(Bitmap bitmap);

        void getDrawable(Drawable dd);
    }

    /**
     * 转换成bitmap对象
     *
     * @param context 上下文
     * @param call    回调
     */
    public void loadByBitmap(Context context, String filePath, int w, int h, final OnGetBitmap call) {

        RequestOptions options = RequestOptions.priorityOf(Priority.HIGH).centerCrop().override(w, h);

        Glide.with(context).asBitmap().load(filePath).apply(options).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                Log.i("result", "www---" + resource.getWidth() + "---hhh---" + resource.getHeight() + "-----bbbb----" + resource.getRowBytes());

                call.getBitmap(resource);

            }
        });

    }


    public void loadByBitmap(Context context, String filePath, final OnGetBitmap call) {

        RequestOptions options = RequestOptions.priorityOf(Priority.HIGH).centerCrop();

        Glide.with(context).asBitmap().load(filePath).apply(options).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                call.getBitmap(resource);

            }
        });

    }


    public void loadByDrawable(Context context, String filePath, final OnGetBitmap call) {

        RequestOptions options = RequestOptions.priorityOf(Priority.HIGH).centerCrop();

        Glide.with(context).asDrawable().load(filePath).apply(options).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                call.getDrawable(resource);
            }
        });

    }


    public void loadCircleCropImage(Context context, ImageView imageView, String url, int resId) {
        RequestOptions options = RequestOptions
                .circleCropTransform()
                .priority(Priority.HIGH)
                .error(resId)
                .placeholder(resId)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(context).load(url).apply(options).into(imageView);

    }

    /**
     * 加载网络图片，使用glide框架,自定义图片样式
     *
     * @param context   上下文对象 context
     * @param imageView ImageView
     * @param url       图片的地址
     */
    public void loadImageByTransformation(Context context, ImageView imageView, String url, int resId, Transformation<Bitmap> transformation) {
        if (context != null) {
            RequestOptions options = RequestOptions
                    .bitmapTransform(transformation)
                    .priority(Priority.HIGH)
                    .error(resId)
                    .placeholder(resId)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

            Glide.with(context).load(url).apply(options).into(imageView);
        }
    }

    public void loadImageByTransformation(Activity activity, final ImageView imageView, String url, int resId, Transformation<Bitmap> transformation) {
        if (!activity.isFinishing()) {
            RequestOptions options = RequestOptions
                    .bitmapTransform(transformation)
                    .priority(Priority.HIGH)
                    .error(resId)
                    .placeholder(resId)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

            Glide.with(activity).load(url).apply(options).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    imageView.setImageDrawable(resource);
                }
            });
        }
    }


    public void loadImageByTransformation(Fragment fragment, final ImageView imageView, String url, int resId, Transformation<Bitmap> transformation) {
        if (fragment != null) {
            RequestOptions options = RequestOptions
                    .bitmapTransform(transformation)
                    .priority(Priority.HIGH)
                    .error(resId)
                    .placeholder(resId)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

            Glide.with(fragment).load(url).apply(options).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    imageView.setImageDrawable(resource);
                }
            });
        }
    }


    public void loadImageNoTransformationByOverride(Context context, final ImageView imageView, String url, int resId, int w, int h) {
        if (context != null) {
            RequestOptions options = RequestOptions
                    .placeholderOf(resId)
                    .centerCrop()
                    .error(resId)
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

            RequestBuilder<Drawable> builder = Glide.with(context).load(url).transition(new DrawableTransitionOptions().crossFade(TIME)).apply(options);
            builder.preload(w, h);
            builder.into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    imageView.setImageDrawable(resource);
                }
            });

        }
    }

    public void loadImageNoTransformationByOverride(Activity activity, final ImageView imageView, String url, int resId, int w, int h) {
        if (!activity.isFinishing()) {
            RequestOptions options = RequestOptions
                    .placeholderOf(resId)
                    .centerCrop()
                    .error(resId)
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

            RequestBuilder<Drawable> builder = Glide.with(activity).load(url).transition(new DrawableTransitionOptions().crossFade(TIME)).apply(options);
            builder.preload(w, h);
            builder.into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    imageView.setImageDrawable(resource);
                }
            });

        }
    }

    public void loadImageNoTransformationByOverride(Fragment fragment, final ImageView imageView, String uri, int resId, int w, int h) {
        if (fragment != null) {

            RequestOptions options = RequestOptions
                    .placeholderOf(resId)
                    .centerCrop()
                    .error(resId)
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

            RequestBuilder<Drawable> builder = Glide.with(fragment).load(uri).transition(new DrawableTransitionOptions().crossFade(TIME)).apply(options);
            builder.preload(w, h);
            builder.into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    imageView.setImageDrawable(resource);
                }
            });

        }
    }


    /**
     * 获取视频第一帧
     */
    public void loadVideoScreenshot(final Context context, String uri, ImageView imageView, long frameTimeMicros, final OnGetBitmap onGetBitmap) {
        RequestOptions requestOptions = RequestOptions.frameOf(frameTimeMicros);
        requestOptions.set(FRAME_OPTION, MediaMetadataRetriever.OPTION_CLOSEST);
        requestOptions.transform(new BitmapTransformation() {
            @Override
            protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
                return toTransform;
            }

            @Override
            public void updateDiskCacheKey(MessageDigest messageDigest) {
                try {
                    messageDigest.update((context.getPackageName() + "RotateTransform").getBytes("utf-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Glide.with(context).load(uri).apply(requestOptions).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (onGetBitmap != null) {
                    onGetBitmap.getDrawable(resource);
                }

            }
        });

    }


    /**
     * 加载圆形图片
     */
    public void loadCircleImage(Context context, final ImageView imageView, String url, int resId) {

        Log.i("result", "图片地址----" + url);

        RequestOptions options = RequestOptions
                .bitmapTransform(new CropCircleTransformation(context))
                .placeholder(resId)
                .error(resId)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context).load(url).apply(options).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                imageView.setImageDrawable(resource);
            }
        });
    }


    /**
     * 加载圆角图片
     */
    public void loadRoundCircleImage(Context context, ImageView imageView, String url, int resId, int r) {
        RequestOptions options = RequestOptions
                .bitmapTransform(new RoundedCornersTransformation(context, r, 0, RoundedCornersTransformation.CornerType.ALL))
                .error(resId)
                .priority(Priority.HIGH)
                .skipMemoryCache(true)//跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL);//设置为磁盘缓存

        Glide.with(context).load(url).apply(options).into(imageView);

    }


    /**
     * 加载圆角图片-指定任意部分圆角（图片上、下、左、右四个角度任意定义）
     */
    public void loadCustRoundCircleImage(Context context, ImageView imageView, String url, int resId, RoundedCornersTransformation.CornerType type) {
        int r = (int) context.getResources().getDimension(R.dimen.qb_px_40);

        RequestOptions options = RequestOptions
                .bitmapTransform(new RoundedCornersTransformation(context, r, 1, type))
                .placeholder(resId)
                .error(resId)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context).load(url).apply(options).into(imageView);
    }


    /**
     * 加载模糊图片（自定义透明度）
     */
    public void loadBlurImage(Context context, final ImageView imageView, String url, int resId, int blur) {
        RequestOptions options = new RequestOptions()
                .placeholder(resId)
                .error(resId)
                .priority(Priority.HIGH)
                .bitmapTransform(new BlurTransformation(context, blur))
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                imageView.setImageDrawable(resource);
            }
        });
    }


    public void loadImageNoTransformation(Context context, final ImageView imageView, int resId, String url) {
        Log.i("result", "url--------" + url);

        if (context != null && !((Activity) context).isFinishing()) {

            RequestOptions options = RequestOptions
                    .placeholderOf(resId)
                    .errorOf(resId)          //加载失败显示图片
                    .priorityOf(Priority.HIGH)   //优先级
                    .diskCacheStrategyOf(DiskCacheStrategy.NONE); //缓存策略

            Glide.with(context).load(url).apply(options).transition(new DrawableTransitionOptions().crossFade(TIME))
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            imageView.setImageDrawable(resource);
                        }
                    });

        }
    }

    public void loadImageNoTransformation(Activity activity, final ImageView imageView, int resId, String url) {

        if (!activity.isFinishing()) {
            RequestOptions options = RequestOptions
                    .placeholderOf(resId)
                    .errorOf(resId)          //加载失败显示图片
                    .priorityOf(Priority.HIGH)   //优先级
                    .diskCacheStrategyOf(DiskCacheStrategy.NONE); //缓存策略

            Log.i("result", "url======" + url);

            Glide.with(activity).load(url).apply(options)
                    .transition(new DrawableTransitionOptions()
                            .crossFade(TIME)).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    imageView.setImageDrawable(resource);
                }
            });

        }
    }

    public void loadImageNoTransformation(Fragment fragment, final ImageView imageView, int resId, String url) {
        if (fragment != null) {
            RequestOptions options = RequestOptions
                    .placeholderOf(resId)
                    .error(resId)          //加载失败显示图片
                    .centerCrop()
                    .priority(Priority.HIGH)   //优先级
                    .diskCacheStrategy(DiskCacheStrategy.NONE); //缓存策略

            Glide.with(fragment).load(url).apply(options).transition(new DrawableTransitionOptions().crossFade(TIME)).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    imageView.setImageDrawable(resource);
                }
            });
        }
    }


    /**
     * 加载图片 实现宽度满屏，高度自适应
     *
     * @param context   上下文对象
     * @param imageView ImageView
     * @param resId     加载失败显示图片
     * @param url       加载图片地址
     */
    public void loadImageNoTransformationWithW(Context context, final ImageView imageView, int resId, String url) {

        Log.i("result", "===url======" + url);

        if (context != null) {

            RequestOptions options = RequestOptions
                    .placeholderOf(resId)
                    .error(resId)          //加载失败显示图片
                    .centerCrop()
                    .priority(Priority.HIGH)   //优先级
                    .diskCacheStrategy(DiskCacheStrategy.NONE); //缓存策略

            Glide.with(context).load(url)
                    .transition(new DrawableTransitionOptions().crossFade(TIME))
                    .apply(options)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if (imageView == null) {
                                return false;
                            }
                            if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            }

                            ViewGroup.LayoutParams params = imageView.getLayoutParams();
                            int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                            float scale = (float) vw / (float) resource.getIntrinsicWidth();
                            int vh = Math.round(resource.getIntrinsicHeight() * scale);
                            params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                            imageView.setLayoutParams(params);

                            return false;

                        }
                    }).into(imageView);
        }
    }


    /**
     * 剪裁bitmap 图片
     *
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
    public Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        if (w <= 0 || h <= 0) {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidht = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidht, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return newbmp;
    }


    /**
     * Drawable into Bitmap
     */
    public Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;

    }

    /**
     * Get round Bitmap
     *
     * @param bitmap
     * @param roundPx
     * @return
     */
    public Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


    /**
     * Get reflection Bitmap
     */

    public Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2, width, height / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0, bitmapWithReflection.getHeight()
                + reflectionGap, 0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);

        return bitmapWithReflection;
    }


    public Drawable createAdaptiveDrawableByDrawableId(Activity activity, int drawableId) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        Bitmap bitmap = ((BitmapDrawable) activity.getResources().getDrawable(drawableId)).getBitmap();
        bitmap = zoomBitmap(bitmap, width, height);
        Drawable drawable = new BitmapDrawable(bitmap);
        return drawable;
    }

    // Create adaptive drawable by bitmap
    public Drawable createAdaptiveDrawableByBitmap(Activity activity, Bitmap bitmap) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        bitmap = zoomBitmap(bitmap, width, height);
        Drawable drawable = new BitmapDrawable(bitmap);
        return drawable;
    }


    /**
     * Returns the bitmap position inside an imageView.
     *
     * @param imageView source ImageView
     * @return 0: left, 1: top, 2: width, 3: height
     */
    public int[] getBitmapPositionInsideImageView(ImageView imageView) {
        int[] ret = new int[4];

        if (imageView == null || imageView.getDrawable() == null)
            return ret;

        // Get image dimensions
        // Get image matrix values and place them in an array
        float[] f = new float[9];
        imageView.getImageMatrix().getValues(f);

        // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
        final float scaleX = f[Matrix.MSCALE_X];
        final float scaleY = f[Matrix.MSCALE_Y];

        // Get the drawable (could also get the bitmap behind the drawable and getWidth/getHeight)
        final Drawable d = imageView.getDrawable();
        final int origW = d.getIntrinsicWidth();
        final int origH = d.getIntrinsicHeight();

        // Calculate the actual dimensions
        final int actW = Math.round(origW * scaleX);
        final int actH = Math.round(origH * scaleY);

        ret[2] = actW;
        ret[3] = actH;

        // Get image position
        // We assume that the image is centered into ImageView
        int imgViewW = imageView.getWidth();
        int imgViewH = imageView.getHeight();

        int top = (imgViewH - actH) / 2;
        int left = (imgViewW - actW) / 2;

        ret[0] = left;
        ret[1] = top;

        return ret;
    }
}
