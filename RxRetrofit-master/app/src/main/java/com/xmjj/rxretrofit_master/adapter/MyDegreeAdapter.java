package com.xmjj.rxretrofit_master.adapter;

import android.graphics.Point;


/**
 * @author genius
 *         这里的坐标系是X轴向右，Y轴向上的坐标系
 */
public class MyDegreeAdapter {

	private static final int ANGLE_0 = 0;
	private static final int ANGLE_90 = 90;
	private static final int ANGLE_180 = 180;
	private static final int ANGLE_360 = 360;

	private static final int QUADRANT_NONE = 0;//坐标轴上
	private static final int QUADRANT_ONE = 1;//第一象限
	private static final int QUADRANT_TWO = 2;//第二象限
	private static final int QUADRANT_THREE = 3;//第三象限
	private static final int QUADRANT_FOUR = 4;//第四象限



	/**
	 * @param point
	 * @return 获得Point点所在象限
	 */
	public static int GetQuadrant(Point point) {
		if (point.x == 0 || point.y == 0) {
			return QUADRANT_NONE;
		}

		if (point.x > 0) {
			if (point.y > 0) {
				return QUADRANT_ONE;
			} else {
				return QUADRANT_FOUR;
			}

		} else {
			if (point.y < 0) {
				return QUADRANT_THREE;
			} else {
				return QUADRANT_TWO;
			}
		}
	}


	/*以逆时针转为例子 x正半轴对应的是0度角 逆时针旋转为负角度*/
	public static int getAngle(Point point) {

		double Sin = Math.abs(point.y / Math.sqrt(point.x * point.x + point.y * point.y));

		double dAngle = Math.asin(Sin);

		int degree = (int) Math.toDegrees(dAngle);

		switch (GetQuadrant(point)) {
			case QUADRANT_NONE:
				if (point.x == 0 && point.y == 0) {
					degree = 0;
				}

				if (point.x == 0) {
					if (point.y > 0) {
						degree = -ANGLE_90;
					} else {
						degree = ANGLE_90;
					}
				}

				if (point.y == 0) {
					if (point.x > 0) {
						degree = ANGLE_0;
					} else {
						degree = ANGLE_180;
					}
				}

				break;
			case QUADRANT_ONE:
				degree = -degree;
				break;

			case QUADRANT_TWO:
				degree = -(ANGLE_180 - degree);

				break;
			case QUADRANT_THREE:
				degree = -(degree + ANGLE_180);

				break;
			case QUADRANT_FOUR:


				break;
		}

		return degree;
	}
}
