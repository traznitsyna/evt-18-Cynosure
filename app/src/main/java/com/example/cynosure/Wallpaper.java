package com.example.cynosure;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import com.example.cynosure.SettableMappedIndexPreference.Mapper;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class Wallpaper extends WallpaperService {
	public static final String SHARED_PREFS_NAME = "cynosure_settings"; //исходные значения параметров настройки
	public static final int DEFAULT_FIGURE_COUNT = 200;
	public static final int DEFAULT_EFFECT_TYPE = 0;
	public static final int DEFAULT_MIN_SIZE= 49;
	public static final int DEFAULT_MAX_SIZE = 159;
	public static final int DEFAULT_MIN_OUTLINE_WIDTH = 3;
	public static final int DEFAULT_MAX_OUTLINE_WIDTH = 15;
	public static final int DEFAULT_MIN_TRANSPARENCY = 64;
	public static final int DEFAULT_MAX_TRANSPARENCY = 212;
	public static final int DEFAULT_MIN_SPEED = 0;
	public static final int DEFAULT_MAX_SPEED = 7;
	public static final int DEFAULT_BRIGHTNESS = 50;
	public static final int DEFAULT_FRAME_RATE = 29;

	public static final int FIGURE_COUNT_MAX_INDEX = 990;//предельные значения параметров
	public static final int EFFECT_TYPE_MAX_INDEX = 2;
	public static final int MIN_SIZE_MAX_INDEX = 59;
	public static final int MAX_SIZE_MAX_INDEX = 199;
	public static final int MIN_OUTLINE_WIDTH_MAX_INDEX = 90;
	public static final int MAX_OUTLINE_WIDTH_MAX_INDEX = 90;
	public static final int MIN_TRANSPARENCY_MAX_INDEX = 255;
	public static final int MAX_TRANSPARENCY_MAX_INDEX = 255;
	public static final int MIN_SPEED_MAX_INDEX = 70;
	public static final int MAX_SPEED_MAX_INDEX = 70;
	public static final int BRIGHTNESS_MAX_INDEX = 100;

	private ArrayList<Figure> figures = new ArrayList<Figure>();
	private final Handler mHandler = new Handler();
	private Random random = new Random(System.currentTimeMillis());

	@Override
	public void onCreate() {
		super.onCreate();
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	@Override
	public Engine onCreateEngine() {
		return new FigureEngine();
	}

	public static Mapper FIGURE_COUNT_MAPPER = new Mapper() {
		@Override
		public int calculateToInt(int index) {
			return index;
		}
		@Override
		public float calculateToFloat(int index) {
			throw new UnsupportedOperationException("В свойствах присутствует несоответствие между различными типами данных");
		}
		@Override
		public String calculateToDisplayableString(int index) {
			return Integer.toString(calculateToInt(index));
		}
	};

	public static Mapper EFFECT_TYPE_MAPPER = new Mapper() {
		@Override
		public int calculateToInt(int index) {
			return index;
		}
		@Override
		public float calculateToFloat(int index) {
			throw new UnsupportedOperationException("В свойствах присутствует несоответствие между различными типами данных");
		}
		@Override
		public String calculateToDisplayableString(int index) {
			int val = calculateToInt(index);
			if (val == 0) {
				return "Квадраты";
			}
			else if (val == 1) {
				return "Круги";
			}
			else if (val == 2) {
				return "Смешанный";
			}
			else {
				return "";
			}
		}
	};

	public static Mapper MIN_SIZE_MAPPER = new Mapper() {
		@Override
		public int calculateToInt(int index) {
			throw new UnsupportedOperationException("В свойствах присутствует несоответствие между различными типами данных");
		}
		@Override
		public float calculateToFloat(int index) {
			return index + 1.0f;
		}
		@Override
		public String calculateToDisplayableString(int index) {
			DecimalFormat df = new DecimalFormat("0");
			return df.format(calculateToFloat(index));
		}
	};

	public static Mapper MAX_SIZE_MAPPER = new Mapper() {
		@Override
		public int calculateToInt(int index) {
			throw new UnsupportedOperationException("В свойствах присутствует несоответствие между различными типами данных");
		}
		@Override
		public float calculateToFloat(int index) {
			return index + 1.0f;
		}
		@Override
		public String calculateToDisplayableString(int index) {
			DecimalFormat df = new DecimalFormat("0");
			return df.format(calculateToFloat(index));
		}
	};

	public static Mapper MIN_OUTLINE_WIDTH_MAPPER = new Mapper() {
		@Override
		public int calculateToInt(int index) {
			throw new UnsupportedOperationException("В свойствах присутствует несоответствие между различными типами данных");
		}
		@Override
		public float calculateToFloat(int index) {
			return index / 2.0f;
		}
		@Override
		public String calculateToDisplayableString(int index) {
			DecimalFormat df = new DecimalFormat("0.0");
			return df.format(calculateToFloat(index));
		}
	};

	public static Mapper MAX_OUTLINE_WIDTH_MAPPER = new Mapper() {
		@Override
		public int calculateToInt(int index) {
			throw new UnsupportedOperationException("В свойствах присутствует несоответствие между различными типами данных");
		}
		@Override
		public float calculateToFloat(int index) {
			return index / 2.0f;
		}
		@Override
		public String calculateToDisplayableString(int index) {
			DecimalFormat df = new DecimalFormat("0.0");
			return df.format(calculateToFloat(index));
		}
	};

	public static Mapper MIN_TRANSPARENCY_MAPPER = new Mapper() {
		@Override
		public int calculateToInt(int index) {
			return index;
		}
		@Override
		public float calculateToFloat(int index) {
			throw new UnsupportedOperationException("В свойствах присутствует несоответствие между различными типами данных");
		}
		@Override
		public String calculateToDisplayableString(int index) {
			return Integer.toString(calculateToInt(index));
		}
	};

	public static Mapper MAX_TRANSPARENCY_MAPPER = new Mapper() {
		@Override
		public int calculateToInt(int index) {
			return index;
		}
		@Override
		public float calculateToFloat(int index) {
			throw new UnsupportedOperationException("В свойствах присутствует несоответствие между различными типами данных");
		}
		@Override
		public String calculateToDisplayableString(int index) {
			return Integer.toString(calculateToInt(index));
		}
	};

	public static Mapper MIN_SPEED_MAPPER = new Mapper() {
		@Override
		public int calculateToInt(int index) {
			throw new UnsupportedOperationException("В свойствах присутствует несоответствие между различными типами данных");
		}
		@Override
		public float calculateToFloat(int index) {
			return index / 10.0f;
		}
		@Override
		public String calculateToDisplayableString(int index) {
			DecimalFormat df = new DecimalFormat("0.00");
			return df.format(calculateToFloat(index));
		}
	};

	public static Mapper MAX_SPEED_MAPPER = new Mapper() {
		@Override
		public int calculateToInt(int index) {
			throw new UnsupportedOperationException("В свойствах присутствует несоответствие между различными типами данных");
		}
		@Override
		public float calculateToFloat(int index) {
			return index / 10.0f;
		}
		@Override
		public String calculateToDisplayableString(int index) {
			DecimalFormat df = new DecimalFormat("0.00");
			return df.format(calculateToFloat(index));
		}
	};

	public static Mapper BRIGHTNESS_MAPPER = new Mapper() {
		@Override
		public int calculateToInt(int index) {
			throw new UnsupportedOperationException("В свойствах присутствует несоответствие между различными типами данных");
		}
		@Override
		public float calculateToFloat(int index) {
			return (float)index / 100;
		}
		@Override
		public String calculateToDisplayableString(int index) {
			DecimalFormat df = new DecimalFormat("0.00");
			return df.format(calculateToFloat(index));
		}
	};

	public static Mapper FRAME_RATE_MAPPER = new Mapper() {
		@Override
		public int calculateToInt(int index) {
			return index + 1;
		}
		@Override
		public float calculateToFloat(int index) {
			throw new UnsupportedOperationException("В свойствах присутствует несоответствие между различными типами данных");
		}
		@Override
		public String calculateToDisplayableString(int index) {
			return Integer.toString(calculateToInt(index));
		}
	};

	private static int getFrameInterval(int frameRate) {
		return (int)Math.round(1000.0f / (float)frameRate);
	}

	class FigureEngine extends Engine implements OnSharedPreferenceChangeListener {
		private int FIGURE_COUNT = FIGURE_COUNT_MAPPER.calculateToInt(DEFAULT_FIGURE_COUNT);
		private int EFFECT_TYPE = EFFECT_TYPE_MAPPER.calculateToInt(DEFAULT_EFFECT_TYPE);
		private float MIN_SIZE = MIN_SIZE_MAPPER.calculateToFloat(DEFAULT_MIN_SIZE);
		private float MAX_SIZE = MAX_SIZE_MAPPER.calculateToFloat(DEFAULT_MAX_SIZE);
		private float MIN_OUTLINE_WIDTH = MIN_OUTLINE_WIDTH_MAPPER.calculateToFloat(DEFAULT_MIN_OUTLINE_WIDTH);
		private float MAX_OUTLINE_WIDTH = MAX_OUTLINE_WIDTH_MAPPER.calculateToFloat(DEFAULT_MAX_OUTLINE_WIDTH);
		private int MIN_TRANSPARENCY = MIN_TRANSPARENCY_MAPPER.calculateToInt(DEFAULT_MIN_TRANSPARENCY);
		private int MAX_TRANSPARENCY = MAX_TRANSPARENCY_MAPPER.calculateToInt(DEFAULT_MAX_TRANSPARENCY);
		private float MIN_SPEED = MIN_SPEED_MAPPER.calculateToFloat(DEFAULT_MIN_SPEED);
		private float MAX_SPEED = MAX_SPEED_MAPPER.calculateToFloat(DEFAULT_MAX_SPEED);
		private float BRIGHTNESS = BRIGHTNESS_MAPPER.calculateToFloat(DEFAULT_BRIGHTNESS);
		private long FRAME_INTERVAL = getFrameInterval(FRAME_RATE_MAPPER.calculateToInt(DEFAULT_FRAME_RATE));
		private final Paint paint3 = new Paint();
		private final Paint paint2 = new Paint();
		private final Paint mPaint = new Paint();
		private float mTouchX = -5;
		private float mTouchY = -5;
		private float mWidth;
		private float mHeight;

		private int cycleCounter = 0;

		private final Runnable mPainter = new Runnable() {
			public void run() {
				drawFrame();
			mutate();
			}
		};
		private boolean mVisible;
		private SharedPreferences mPrefs;

		FigureEngine() {
			paint3.setAntiAlias(true);
			paint2.setAntiAlias(true);
			paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));

			final Paint paint = mPaint;
			paint.setColor(0xff000000);
			paint.setAntiAlias(true);
			paint.setStrokeWidth(2);
			paint.setStrokeCap(Paint.Cap.ROUND);
			paint.setStyle(Paint.Style.STROKE);

			mPrefs = Wallpaper.this.getSharedPreferences(SHARED_PREFS_NAME, 0);
			mPrefs.registerOnSharedPreferenceChangeListener(this);
			onSharedPreferenceChanged(mPrefs, null);
			fixFlippedMinMax();
		}

		public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
			FIGURE_COUNT = FIGURE_COUNT_MAPPER.calculateToInt(Integer.parseInt((prefs.getString(
					WallpaperSettings.FIGURE_COUNT_KEY, Integer.toString(DEFAULT_FIGURE_COUNT)))));
			EFFECT_TYPE = EFFECT_TYPE_MAPPER.calculateToInt(Integer.parseInt((prefs.getString(
					WallpaperSettings.EFFECT_TYPE_KEY, Integer.toString(DEFAULT_EFFECT_TYPE)))));
			MIN_SIZE = MIN_SIZE_MAPPER.calculateToFloat(Integer.parseInt(prefs.getString(
					WallpaperSettings.MIN_SIZE_KEY, Integer.toString(DEFAULT_MIN_SIZE))));
			MAX_SIZE = MAX_SIZE_MAPPER.calculateToFloat(Integer.parseInt(prefs.getString(
					WallpaperSettings.MAX_SIZE_KEY, Integer.toString(DEFAULT_MAX_SIZE))));
			MIN_OUTLINE_WIDTH = MIN_OUTLINE_WIDTH_MAPPER.calculateToFloat(Integer.parseInt(prefs.getString(
					WallpaperSettings.MIN_OUTLINE_WIDTH_KEY, Integer.toString(DEFAULT_MIN_OUTLINE_WIDTH))));
			MAX_OUTLINE_WIDTH = MAX_OUTLINE_WIDTH_MAPPER.calculateToFloat(Integer.parseInt(prefs.getString(
					WallpaperSettings.MAX_OUTLINE_WIDTH_KEY, Integer.toString(DEFAULT_MAX_OUTLINE_WIDTH))));
			MIN_TRANSPARENCY = MIN_TRANSPARENCY_MAPPER.calculateToInt(Integer.parseInt(prefs.getString(
					WallpaperSettings.MIN_TRANSPARENCY_KEY, Integer.toString(DEFAULT_MIN_TRANSPARENCY))));
			MAX_TRANSPARENCY = MAX_TRANSPARENCY_MAPPER.calculateToInt(Integer.parseInt(prefs.getString(
					WallpaperSettings.MAX_TRANSPARENCY_KEY, Integer.toString(DEFAULT_MAX_TRANSPARENCY))));
			MIN_SPEED = MIN_SPEED_MAPPER.calculateToFloat(Integer.parseInt(prefs.getString(
					WallpaperSettings.MIN_SPEED_KEY, Integer.toString(DEFAULT_MIN_SPEED))));
			MAX_SPEED = MAX_SPEED_MAPPER.calculateToFloat(Integer.parseInt(prefs.getString(
					WallpaperSettings.MAX_SPEED_KEY, Integer.toString(DEFAULT_MAX_SPEED))));
			BRIGHTNESS = BRIGHTNESS_MAPPER.calculateToFloat(Integer.parseInt(prefs.getString(
					WallpaperSettings.BRIGHTNESS_KEY, Integer.toString(DEFAULT_BRIGHTNESS))));
			fixFlippedMinMax();
		    createShapes();
		}

		private void fixFlippedMinMax() { //Если минимальные значения параметров больше максимальных, то они приравниваются к друг другу
			if (MIN_SIZE > MAX_SIZE) {
				MAX_SIZE = MIN_SIZE;
			}
			if (MIN_OUTLINE_WIDTH > MAX_OUTLINE_WIDTH) {
				MAX_OUTLINE_WIDTH = MIN_OUTLINE_WIDTH;
			}
			if (MIN_TRANSPARENCY > MAX_TRANSPARENCY) {
				MAX_TRANSPARENCY = MIN_TRANSPARENCY;
			}
			if (MIN_SPEED > MAX_SPEED) {
				MAX_SPEED = MIN_SPEED;
			}
		}

		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);
			setTouchEventsEnabled(true);
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
			mHandler.removeCallbacks(mPainter);
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			mVisible = visible;
			if (visible) {
				drawFrame();
				mutate();
			} else {
				mHandler.removeCallbacks(mPainter);
			}
		}

		private void mutate() {
			cycleCounter++;
			if (cycleCounter >= 64) {
				cycleCounter = 0;
			}
			int count = figures.size();
			Figure figure;
			for (int i=0; i<count; i++) {
				figure = figures.get(i);

				figure.x = figure.x + figure.xdelta;
				if (
						(figure.xdelta > 0.0f && figure.x > mWidth)
								||
								(figure.xdelta < 0.0f && figure.x < 0.0f))
				{
					figure.xdelta = -figure.xdelta;
					figure.x = figure.x + figure.xdelta;
					figure.x = figure.x + figure.xdelta;
				}

				figure.y = figure.y + figure.ydelta;
				if (
						(figure.ydelta > 0.0f && figure.y > mHeight)
								||
								(figure.ydelta < 0.0f && figure.y < 0.0f))
				{
					figure.ydelta = -figure.ydelta;
					figure.y = figure.y + figure.ydelta;
					figure.y = figure.y + figure.ydelta;
				}
				figure.y = figure.y + figure.ydelta;
				if (
						(figure.ydelta > 0.0f && figure.y > mHeight)
								||
								(figure.ydelta < 0.0f && figure.y < 0.0f))
				{
					figure.ydelta = -figure.ydelta;
					figure.y = figure.y + figure.ydelta;
					figure.y = figure.y + figure.ydelta;
				}

				figure.radius = figure.radius + figure.radiusdelta;
				if (
						(figure.radiusdelta > 0.0f && figure.radius > MAX_SIZE)
								||
								(figure.radiusdelta < 0.0f && figure.radius < MIN_SIZE))
				{
					figure.radiusdelta = -figure.radiusdelta;
					figure.radius = figure.radius + figure.radiusdelta;
					figure.radius = figure.radius + figure.radiusdelta;
				}

				figure.alpha = figure.alpha + figure.alphadelta;
				if (
						(figure.alphadelta > 0 && figure.alpha > MAX_TRANSPARENCY)
								||
								(figure.alphadelta < 0 && figure.alpha < MIN_TRANSPARENCY))
				{
					figure.alphadelta = -figure.alphadelta;
					figure.alpha = figure.alpha + figure.alphadelta;
					figure.alpha = figure.alpha + figure.alphadelta;
				}

				populateFigure(figure);
			}

		}
		private Runnable mIteration = new Runnable() {
			public void run() {
				drawFrame();
			}
		};


		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			super.onSurfaceChanged(holder, format, width, height);
			mWidth = width;
			mHeight = height;
			createShapes();
			drawFrame();
			mutate();
		}

		//создание заданного количество фигур FIGURE_COUNT через настройки
		private void createShapes() {
			Figure figure;
			figures.clear();
			for (int i=0; i<FIGURE_COUNT; i++) {
				figure = createShape(10.0f + mWidth * random.nextFloat(), 0.0f + mHeight * random.nextFloat());
				figures.add(figure);
			}

		}
		//Настройка переменных для создания фигур
		private Figure createShape(float x, float y) {
			Figure figure = new Figure();
			figure.radius = MIN_SIZE + (MAX_SIZE - MIN_SIZE) * random.nextFloat();
			figure.strokeWidth = MIN_OUTLINE_WIDTH + (MAX_OUTLINE_WIDTH - MIN_OUTLINE_WIDTH) * random.nextFloat();
			figure.x = x;
			figure.y = y;
			figure.alpha = MIN_TRANSPARENCY + random.nextInt(MAX_TRANSPARENCY - MIN_TRANSPARENCY + 1);
			figure.xdelta = (MAX_SPEED - MIN_SPEED) * (2.0f * random.nextFloat() - 1.0f);
			figure.ydelta = (MAX_SPEED - MIN_SPEED) * (2.0f * random.nextFloat() - 1.0f);
			figure.xdelta= figure.xdelta + Math.signum(figure.xdelta) * MIN_SPEED;
			figure.ydelta = figure.ydelta + Math.signum(figure.ydelta) * MIN_SPEED;
			figure.alphadelta = 1 + random.nextInt(2);
			figure.radiusdelta = -0.7f + 1.4f * random.nextFloat();
			figure.paintStroke1 = new Paint();
			figure.paintStroke1.setStyle(Paint.Style.STROKE);
			figure.paintStroke1.setAntiAlias(true);
			figure.paintFill = new Paint();
			figure.paintFill.setStyle(Paint.Style.FILL);
			figure.paintFill.setAntiAlias(true);
			populateFigure(figure);
			return figure;
		}

		//заполняемость фигур цветом. В разной части экрана, разные цветовые оттенки
		private void populateFigure(Figure figure) {
			float radiusAndStroke = figure.radius + figure.strokeWidth;
			float boundedWidth = mWidth - radiusAndStroke * 3.0f;
			float boundedHeight = mHeight - radiusAndStroke * 3.0f;
			float boundedHalfWidth = boundedWidth / 2.0f;
			float boundedHalfHeight = boundedHeight / 2.0f;
			float max = boundedHalfWidth * boundedHalfHeight;
			float xy = (figure.x - radiusAndStroke - boundedHalfWidth) *
					(figure.y - radiusAndStroke - boundedHalfHeight) + max;
			max = max * 1.8f;

			if (EFFECT_TYPE == 0 || EFFECT_TYPE == 1|| EFFECT_TYPE == 2) {
				figure.strokeColor = Color.HSVToColor(figure.alpha, new float[]{360.0f * xy / max, 1.9f, BRIGHTNESS});//заливка фигуры
				figure.paintFill.setColor(figure.strokeColor);
				figure.paintFill.setStrokeWidth(figure.strokeWidth);
				figure.strokeColor2 = Color.parseColor("#FF000000");//для контура
				figure.paintStroke1.setColor(figure.strokeColor2);
				figure.paintStroke1.setStrokeWidth(figure.strokeWidth);
		}
		}

		@Override
		public void onSurfaceCreated(SurfaceHolder holder) {
			super.onSurfaceCreated(holder);
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			mVisible = false;
			mHandler.removeCallbacks(mPainter);
		}

		@Override
		public void onOffsetsChanged(float xOffset, float yOffset, float xStep, float yStep, int xPixels, int yPixels) {
			drawFrame();
		}

		//Сохранение положения события касания, чтобы использовать его позже для рисования
		@Override
		public void onTouchEvent(MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				mTouchX = event.getX();
				mTouchY = event.getY();
				if (figures.size() > 0) {
					figures.remove(0);
					Figure bokeh = createShape(mTouchX, mTouchY);
					figures.add(bokeh);
				}
			}
			else {
				mTouchX = -5;
				mTouchY = -5;
			}
			super.onTouchEvent(event);
		}

		/* Этот метод вызывается повторно путем публикации с задержкой   */
		void drawFrame() {
			final SurfaceHolder holder = getSurfaceHolder();

			Canvas c = null;
			try {
				c = holder.lockCanvas();
				if (c != null) {
					drawFigure(c);
				}
			} finally {
				if (c != null) holder.unlockCanvasAndPost(c);
			}
			mHandler.removeCallbacks(mPainter);
			if (mVisible) {
				mHandler.postDelayed(mPainter, FRAME_INTERVAL);
			}
		}

		//рисование фигур
		void drawFigure(Canvas c) {
			c.save();
			c.drawColor(0xF5605813);
			int count = figures.size();
			Figure figure;
			for (int i=0; i<count; i++) {
				figure = figures.get(i);
				if (EFFECT_TYPE == 0) {
					c.drawRect(figure.radius+figure.x, figure.radius+figure.y, figure.x, figure.y, figure.paintFill);
					c.drawRect(figure.radius+figure.x, figure.radius+figure.y,  figure.x, figure.y,figure.paintStroke1);
				}
				else if (EFFECT_TYPE == 1) {
					c.drawCircle(figure.x, figure.y, figure.radius + figure.strokeWidth / 2.0f, figure.paintFill);
					c.drawCircle(figure.x, figure.y, figure.radius+ figure.strokeWidth / 2.0f, figure.paintStroke1);
				}
				else if (EFFECT_TYPE == 2) {
					c.drawRect(figure.radius+figure.x, figure.radius+figure.y, figure.x, figure.y, figure.paintFill);
					c.drawRect(figure.radius+figure.x, figure.radius+figure.y,  figure.x, figure.y,figure.paintStroke1);
					c.drawCircle(figure.x+100, figure.y+300, figure.radius, figure.paintFill);
					c.drawCircle(figure.x+100, figure.y+300, figure.radius, figure.paintStroke1);
				}
			}
			c.restore();
		}
	}


//переменные
	public static class Figure {
		public int strokeColor;
		public int strokeColor2;
		public int alpha;
		public float strokeWidth;
		public float x;
		public float y;
		public float radius;
		public Paint paintStroke1;
		public Paint paintFill;
		public float xdelta;
		public float ydelta;
		public int   alphadelta;
		public float radiusdelta;

	}
}
